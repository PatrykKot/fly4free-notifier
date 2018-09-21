package com.kotlarz.fly4freenotifier.service;

import com.kotlarz.fly4freenotifier.domain.event.SiteEvent;
import com.kotlarz.fly4freenotifier.domain.notified.NotifiedUser;
import com.kotlarz.fly4freenotifier.domain.phrase.Phrase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
public class EmailNotifier {
    @Transactional
    public void notify(Phrase phrase, SiteEvent event) {
        NotifiedUser owner = phrase.getOwner();
        String email = owner.getEmail();

        log.info("Sending email to " + email);
    }
}
