package com.kotlarz.fly4freenotifier.web.dto.site;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class SiteEventDto {
    private Long id;

    private SiteTypeDto siteType;

    private String content;

    private String normalizedContent;

    private String link;

    private Long date;

    private List<SiteByPhraseDto> siteByPhrases;
}
