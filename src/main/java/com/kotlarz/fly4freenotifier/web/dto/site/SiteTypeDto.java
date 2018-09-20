package com.kotlarz.fly4freenotifier.web.dto.site;

import com.kotlarz.fly4freenotifier.domain.notified.SiteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SiteTypeDto {
    private SiteType type;

    private String text;
}
