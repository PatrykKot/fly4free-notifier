package com.kotlarz.fly4freenotifier.service.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CrawlingResult {
    private String content;

    private String innerTitle;

    private String link;
}