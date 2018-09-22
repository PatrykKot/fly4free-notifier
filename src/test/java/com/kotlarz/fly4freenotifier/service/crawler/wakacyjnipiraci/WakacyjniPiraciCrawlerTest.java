package com.kotlarz.fly4freenotifier.service.crawler.wakacyjnipiraci;

import com.kotlarz.fly4freenotifier.service.util.CrawlingResult;
import org.junit.Assert;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class WakacyjniPiraciCrawlerTest {
    @Test
    public void testWakacyjniPiraciCrawler() throws MalformedURLException {
        WakacyjniPiraciCrawler crawler = new WakacyjniPiraciCrawler();
        List<CrawlingResult> results = crawler.getLatestEventContents();
        Assert.assertFalse(results.isEmpty());

        for (CrawlingResult result : results) {
            Assert.assertTrue(result.getContent().length() > 0);
            Assert.assertTrue(result.getLink().length() > 0);
            new URL(result.getLink());
        }
    }
}