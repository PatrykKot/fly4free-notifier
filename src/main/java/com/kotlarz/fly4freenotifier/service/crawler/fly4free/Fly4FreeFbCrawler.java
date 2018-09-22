package com.kotlarz.fly4freenotifier.service.crawler.fly4free;

import com.kotlarz.fly4freenotifier.domain.notified.SiteType;
import com.kotlarz.fly4freenotifier.service.crawler.SiteCrawler;
import com.kotlarz.fly4freenotifier.service.util.CrawlingResult;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class Fly4FreeFbCrawler implements SiteCrawler {
    private static final String FLY4FREE_FACEBOOK_URL = "https://www.facebook.com/fly4free/";

    @Override
    @SneakyThrows
    public List<CrawlingResult> getLatestEventContents() {
        return Jsoup.connect(FLY4FREE_FACEBOOK_URL)
                .get()
                .getElementsByClass("userContent")
                .stream()
                .map(element -> {
                    String link = resolveOutsideFbLink(element.getElementsByTag("a").get(0).attr("href"));
                    return CrawlingResult.builder()
                            .content(element.child(0).text())
                            .link(link)
                            .innerTitle(getInnerTitle(link))
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public SiteType supports() {
        return SiteType.FLY4FREE_FACEBOOK;
    }

    @SneakyThrows
    private String resolveOutsideFbLink(String url) {
        Map<String, String> queryMap = getQueryMap(url);
        String encodedLink = queryMap.get("u");
        return URLDecoder.decode(encodedLink, "UTF-8");
    }

    private Map<String, String> getQueryMap(String url) throws MalformedURLException {
        URL urlObj = new URL(url);

        String[] params = urlObj.getQuery().split("&");
        Map<String, String> map = new HashMap<>();
        for (String param : params) {
            String name = param.split("=")[0];
            String value = param.split("=")[1];
            map.put(name, value);
        }
        return map;
    }

    @SneakyThrows
    private String getInnerTitle(String url) {
        return Jsoup.connect(url)
                .get()
                .title();
    }
}
