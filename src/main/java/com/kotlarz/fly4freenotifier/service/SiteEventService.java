package com.kotlarz.fly4freenotifier.service;

import com.kotlarz.fly4freenotifier.domain.event.SiteEvent;
import com.kotlarz.fly4freenotifier.repository.SiteEventRepository;
import com.kotlarz.fly4freenotifier.web.dto.site.SiteByPhraseDto;
import com.kotlarz.fly4freenotifier.web.dto.site.SiteEventDto;
import com.kotlarz.fly4freenotifier.web.dto.site.SiteTypeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SiteEventService {
    @Autowired
    private SiteTypeTranslationResolver translator;

    @Autowired
    private SiteEventRepository siteEventRepository;

    @Transactional
    public List<SiteEvent> getAll() {
        return toList(siteEventRepository.findAll());
    }

    @Transactional
    public List<SiteEventDto> getAllDto() {
        return getAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public SiteEventDto toDto(SiteEvent event) {
        return SiteEventDto.builder()
                .id(event.getId())
                .content(event.getContent())
                .normalizedContent(event.getNormalizedContent())
                .date(event.getDate().getTime())
                .siteType(SiteTypeDto.builder()
                        .type(event.getSiteType())
                        .text(translator.translate(event.getSiteType()))
                        .build())
                .link(event.getLink())
                .siteByPhrases(event.getSiteByPhrases().stream()
                        .map(siteByPhrase -> SiteByPhraseDto.builder()
                                .id(siteByPhrase.getId())
                                .email(siteByPhrase.getPhrase().getOwner().getEmail())
                                .phraseText(siteByPhrase.getPhrase().getText())
                                .emailSent(siteByPhrase.getEmailSent())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    @Transactional
    public void deleteAll() {
        log.info("Deleting all events");
        siteEventRepository.deleteAll();
    }

    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> result = new LinkedList<>();
        iterable.forEach(item -> result.add(item));
        return result;
    }
}
