package com.kotlarz.fly4freenotifier.service.crawler.fly4free;

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
public class Fly4FreeCrawler implements SiteCrawler {
    private static final String FLY4FREE_HOME_URL = "https://www.fly4free.pl";

    @Override
    @SneakyThrows
    public List<CrawlingResult> getLatestEventContents() {
        Document document = Jsoup.connect(FLY4FREE_HOME_URL).get();
        return document
                .getElementsByClass("item__content")
                .stream()
                .map(element -> {
                    Element titleElement = element.getElementsByClass("item__title").get(0);
                    String content = titleElement.text();
                    String link = titleElement.getElementsByTag("a").get(0).attr("href");
                    return CrawlingResult.builder()
                            .content(content)
                            .link(link)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public SiteType supports() {
        return SiteType.FLY4FREE;
    }
}
