package com.kotlarz.fly4freenotifier.service.events;

import com.kotlarz.fly4freenotifier.domain.notified.NotifiedUser;
import com.kotlarz.fly4freenotifier.domain.phrase.Phrase;
import com.kotlarz.fly4freenotifier.repository.NotifiedUserRepository;
import com.kotlarz.fly4freenotifier.web.dto.notified.NotifiedUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotifiedUserService {
    @Autowired
    private NotifiedUserRepository repository;

    @Transactional
    public List<NotifiedUser> getAll() {
        List<NotifiedUser> users = new LinkedList<>();
        repository.findAll().forEach(item -> users.add(item));
        return users;
    }

    @Transactional
    public void updateAll(List<NotifiedUserDto> notifiedUserDto) {
        repository.deleteAll();
        notifiedUserDto.forEach(this::create);
    }

    private void create(NotifiedUserDto notifiedUserDto) {
        NotifiedUser notifiedUser = NotifiedUser.builder()
                .email(notifiedUserDto.getEmail())
                .build();

        notifiedUser.setPhrases(notifiedUserDto.getPhrases().stream()
                .map(phraseDto -> Phrase.builder()
                        .owner(notifiedUser)
                        .siteType(phraseDto.getSiteType())
                        .text(phraseDto.getText())
                        .build())
                .collect(Collectors.toList()));

        repository.save(notifiedUser);
    }
}
