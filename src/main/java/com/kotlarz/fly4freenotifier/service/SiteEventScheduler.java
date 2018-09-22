package com.kotlarz.fly4freenotifier.service;

import com.kotlarz.fly4freenotifier.domain.event.SiteEvent;
import com.kotlarz.fly4freenotifier.domain.notified.SiteType;
import com.kotlarz.fly4freenotifier.repository.SiteEventRepository;
import com.kotlarz.fly4freenotifier.service.crawler.SiteCrawler;
import com.kotlarz.fly4freenotifier.service.util.CrawlingResult;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class SiteEventScheduler {
    private static final Integer MAX_CONTENT_LENGTH = 150;

    @Autowired
    private List<SiteCrawler> crawlers;

    @Autowired
    private SiteEventRepository siteEventRepository;

    @Autowired
    private SiteEventResolver siteEventResolver;

    @Scheduled(fixedRate = 1000 * 60)
    private void crawlAll() {
        log.info("Running {} crawlers", crawlers.size());
        transactionalCrawlAll();

        log.info("Crawlers finished");
    }

    @Transactional
    protected void transactionalCrawlAll() {
        List<SiteEvent> newEvents = new LinkedList<>();

        crawlers.forEach(crawler -> {
            List<CrawlingResult> latestResults = crawler.getLatestEventContents();
            SiteType siteType = crawler.supports();

            latestResults.forEach(result -> {
                String content = result.getContent();
                String hash = calculateHash(content, siteType);

                if (siteEventRepository.findByHash(hash).isEmpty()) {
                    log.debug("Found new event at site " + siteType.name());
                    newEvents.add(SiteEvent.builder()
                            .hash(hash)
                            .date(new Date())
                            .siteType(siteType)
                            .content(content)
                            .normalizedContent(normalizeContent(content))
                            .link(result.getLink())
                            .build());
                }
            });
        });

        log.info("Found {} new events", newEvents.size());
        siteEventResolver.resolveNewEvents(newEvents);
    }

    private String calculateHash(String content, SiteType siteType) {
        return runSha512(content + siteType.name());
    }

    @SneakyThrows
    public String runSha512(String toHash) {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        byte[] bytes = digest.digest(toHash.getBytes(StandardCharsets.UTF_8));
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            builder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        return builder.toString();
    }

    private String normalizeContent(String content) {
        if (content.length() > MAX_CONTENT_LENGTH) {
            return content.substring(0, MAX_CONTENT_LENGTH) + "...";
        } else {
            return content;
        }
    }
}
