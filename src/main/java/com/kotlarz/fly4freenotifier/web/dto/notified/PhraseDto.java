package com.kotlarz.fly4freenotifier.web.dto.notified;

import com.kotlarz.fly4freenotifier.domain.notified.SiteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhraseDto {
    private Long id;

    private String text;

    private SiteType siteType;
}
