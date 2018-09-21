package com.kotlarz.fly4freenotifier.web.dto.site;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SiteEventDto {
    private Long id;

    private SiteTypeDto siteType;

    private String content;

    private Long date;
}
