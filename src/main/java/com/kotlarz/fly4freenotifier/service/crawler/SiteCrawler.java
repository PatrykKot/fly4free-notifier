package com.kotlarz.fly4freenotifier.service.crawler;

import com.kotlarz.fly4freenotifier.domain.notified.SiteType;
import com.kotlarz.fly4freenotifier.service.util.CrawlingResult;

import java.util.List;

public interface SiteCrawler {
    List<CrawlingResult> getLatestEventContents();

    SiteType supports();
}
