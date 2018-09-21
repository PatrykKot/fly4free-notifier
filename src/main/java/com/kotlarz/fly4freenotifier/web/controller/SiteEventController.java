package com.kotlarz.fly4freenotifier.web.controller;

import com.kotlarz.fly4freenotifier.repository.SiteEventRepository;
import com.kotlarz.fly4freenotifier.service.SiteTypeTranslationResolver;
import com.kotlarz.fly4freenotifier.web.dto.site.SiteEventDto;
import com.kotlarz.fly4freenotifier.web.dto.site.SiteTypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("api/site/events")
public class SiteEventController {
    @Autowired
    private SiteEventRepository siteEventRepository;

    @Autowired
    private SiteTypeTranslationResolver translator;

    @GetMapping
    public List<SiteEventDto> getAll() {
        return StreamSupport.stream(siteEventRepository.findAll().spliterator(), false)
                .map(event -> SiteEventDto.builder()
                        .id(event.getId())
                        .content(event.getContent())
                        .date(event.getDate().getTime())
                        .siteType(SiteTypeDto.builder()
                                .type(event.getSiteType())
                                .text(translator.translate(event.getSiteType()))
                                .build())
                        .build())
                .sorted(Comparator.comparing(SiteEventDto::getDate))
                .collect(Collectors.toList());
    }
}
