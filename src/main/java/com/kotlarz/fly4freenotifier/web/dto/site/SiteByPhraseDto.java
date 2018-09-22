package com.kotlarz.fly4freenotifier.web.dto.site;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SiteByPhraseDto {
    private Long id;

    private String phraseText;

    private String email;

    private Boolean emailSent;
}
