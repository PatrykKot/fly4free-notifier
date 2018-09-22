package com.kotlarz.fly4freenotifier.service.crawler.fly4free;

import com.kotlarz.fly4freenotifier.service.util.CrawlingResult;
import org.junit.Assert;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Fly4FreeFbCrawlerTest {
    @Test
    public void testFly4FreeFbCrawler() throws MalformedURLException {
        Fly4FreeFbCrawler crawler = new Fly4FreeFbCrawler();
        List<CrawlingResult> results = crawler.getLatestEventContents();
        Assert.assertFalse(results.isEmpty());

        for (CrawlingResult result : results) {
            Assert.assertTrue(result.getContent().length() > 0);
            Assert.assertTrue(result.getLink().length() > 0);
            Assert.assertTrue(result.getInnerTitle().length() > 0);
            new URL(result.getLink());
        }
    }
}