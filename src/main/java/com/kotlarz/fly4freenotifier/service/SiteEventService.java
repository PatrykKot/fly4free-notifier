package com.kotlarz.fly4freenotifier.service;

import com.kotlarz.fly4freenotifier.domain.event.SiteEvent;
import com.kotlarz.fly4freenotifier.repository.SiteEventRepository;
import com.kotlarz.fly4freenotifier.web.dto.site.SiteByPhraseDto;
import com.kotlarz.fly4freenotifier.web.dto.site.SiteEventDto;
import com.kotlarz.fly4freenotifier.web.dto.site.SiteTypeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public List<SiteEventDto> getDtos(String sortBy, Boolean descending, Long page, Long rowsPerPage, String search) {
        Sort.Direction direction = descending ? Sort.Direction.DESC : Sort.Direction.ASC;
        sortBy = sortBy == null ? "date" : sortBy;
        rowsPerPage = rowsPerPage == null ? Integer.MAX_VALUE : rowsPerPage;
        PageRequest request = PageRequest.of(page.intValue() - 1, rowsPerPage.intValue(), direction, sortBy);

        return siteEventRepository.findByContentContainingIgnoreCase(search, request).stream()
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

    @Transactional
    public Long countBySearch(String search) {
        return siteEventRepository.countByContentContainingIgnoreCase(search);
    }
}
