package com.kotlarz.fly4freenotifier.service.crawler.wakacyjnipiraci;

import com.kotlarz.fly4freenotifier.domain.notified.SiteType;
import com.kotlarz.fly4freenotifier.service.crawler.SiteCrawler;
import com.kotlarz.fly4freenotifier.service.util.CrawlingResult;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WakacyjniPiraciCrawler implements SiteCrawler {
    private static final String WAKACYJNI_PIRACI_HOME_URL = "https://www.wakacyjnipiraci.pl";

    @Override
    @SneakyThrows
    public List<CrawlingResult> getLatestEventContents() {
        Document document = Jsoup.connect(WAKACYJNI_PIRACI_HOME_URL).get();
        return document
                .getElementsByClass("post-preview")
                .stream()
                .map(element -> {
                    Element aElement = element.getElementsByTag("a").get(0);
                    aElement.getElementsByTag("span").get(0).text("");

                    String content = aElement.text();
                    String link = aElement.attr("href");
                    return CrawlingResult.builder()
                            .content(content)
                            .link(link)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public SiteType supports() {
        return SiteType.WAKACYJNI_PIRACI;
    }
}
