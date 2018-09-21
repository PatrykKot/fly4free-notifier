package com.kotlarz.fly4freenotifier.service;

import com.kotlarz.fly4freenotifier.domain.notified.SiteType;

import java.util.List;

public interface SiteCrawler {
    List<String> getLatestEventContents();

    SiteType supports();
}
