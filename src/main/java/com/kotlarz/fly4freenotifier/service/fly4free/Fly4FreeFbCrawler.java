package com.kotlarz.fly4freenotifier.service.fly4free;

import com.kotlarz.fly4freenotifier.domain.notified.SiteType;
import com.kotlarz.fly4freenotifier.service.SiteCrawler;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Fly4FreeFbCrawler implements SiteCrawler {
    private static final String FLY4FREE_FACEBOOK_URL = "https://www.facebook.com/fly4free/";

    @Override
    @SneakyThrows
    public List<String> getLatestEventContents() {
        return Jsoup.connect(FLY4FREE_FACEBOOK_URL)
                .get()
                .getElementsByClass("userContent")
                .stream()
                .map(element -> element.child(0))
                .map(element -> element.text())
                .collect(Collectors.toList());
    }

    @Override
    public SiteType supports() {
        return SiteType.FLY4FREE_FACEBOOK;
    }
}
