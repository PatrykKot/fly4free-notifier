package com.kotlarz.fly4freenotifier.service;

import com.kotlarz.fly4freenotifier.domain.event.SiteEvent;
import com.kotlarz.fly4freenotifier.domain.notified.SiteType;
import com.kotlarz.fly4freenotifier.domain.phrase.Phrase;
import com.kotlarz.fly4freenotifier.repository.PhraseRepository;
import com.kotlarz.fly4freenotifier.repository.SiteEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class SiteEventResolver {
    @Autowired
    private SiteEventRepository siteEventRepository;

    @Autowired
    private PhraseRepository phraseRepository;

    @Autowired
    private EmailNotifier emailNotifier;

    @Transactional
    public void resolveNewEvents(List<SiteEvent> events) {
        Map<SiteType, List<Phrase>> groupedPhrases = StreamSupport.stream(phraseRepository.findAll().spliterator(), false)
                .collect(Collectors.groupingBy(phrase -> phrase.getSiteType()));

        events.forEach(event -> {
            String content = event.getContent().toLowerCase();
            SiteType siteType = event.getSiteType();

            if (groupedPhrases.containsKey(siteType)) {
                List<Phrase> matchingPhrases = groupedPhrases.get(siteType).stream()
                        .filter(phrase -> content.contains(phrase.getText().toLowerCase().trim()))
                        .collect(Collectors.toList());
                log.info("{} phrases matching to event from site " + siteType);
                log.info("Saving event from site " + siteType);
                matchingPhrases.forEach(phrase -> phrase.getSiteEvents().add(event));
                event.setPhrases(matchingPhrases);
                siteEventRepository.save(event);

                log.info("Sending email to owners of {} phrases", matchingPhrases.size());
                matchingPhrases.forEach(phrase -> emailNotifier.notify(phrase, event));
            } else {
                log.info("There are no phrases for site " + siteType);
                log.info("Saving event from site " + siteType);
                siteEventRepository.save(event);
            }
        });
    }
}
