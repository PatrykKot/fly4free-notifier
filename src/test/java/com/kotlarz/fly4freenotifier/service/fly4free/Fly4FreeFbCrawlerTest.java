package com.kotlarz.fly4freenotifier.service.fly4free;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class Fly4FreeFbCrawlerTest {
    @Test
    public void testFly4FreeFbCrawler() {
        Fly4FreeFbCrawler crawler = new Fly4FreeFbCrawler();
        List<String> contents = crawler.getLatestEventContents();
        Assert.assertFalse("Fly4free didn't found any posts", contents.isEmpty());
    }
}