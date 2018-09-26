package com.kotlarz.fly4freenotifier.web.controller;

import com.kotlarz.fly4freenotifier.domain.notified.SiteType;
import com.kotlarz.fly4freenotifier.service.events.SiteTypeTranslationResolver;
import com.kotlarz.fly4freenotifier.web.dto.site.SiteTypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("api/site/type")
public class SiteTypeController {
    @Autowired
    private SiteTypeTranslationResolver translator;

    @GetMapping
    public List<SiteTypeDto> getAll() {
        return Stream.of(SiteType.values())
                .map(siteType -> SiteTypeDto.builder()
                        .text(translator.translate(siteType))
                        .type(siteType)
                        .build())
                .collect(Collectors.toList());
    }
}
