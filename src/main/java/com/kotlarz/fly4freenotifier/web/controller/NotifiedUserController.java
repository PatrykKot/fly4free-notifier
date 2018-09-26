package com.kotlarz.fly4freenotifier.web.controller;

import com.kotlarz.fly4freenotifier.service.events.NotifiedUserService;
import com.kotlarz.fly4freenotifier.web.dto.notified.NotifiedUserDto;
import com.kotlarz.fly4freenotifier.web.dto.notified.PhraseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/notified/user")
public class NotifiedUserController {
    @Autowired
    private NotifiedUserService notifiedUserService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateAll(@RequestBody List<NotifiedUserDto> notifiedUserDto) {
        notifiedUserService.updateAll(notifiedUserDto);
    }

    @GetMapping
    public List<NotifiedUserDto> getAlL() {
        return notifiedUserService.getAll().stream()
                .map(notifiedUser -> NotifiedUserDto.builder()
                        .id(notifiedUser.getId())
                        .email(notifiedUser.getEmail())
                        .phrases(notifiedUser.getPhrases().stream()
                                .map(phrase -> PhraseDto.builder()
                                        .id(phrase.getId())
                                        .siteType(phrase.getSiteType())
                                        .text(phrase.getText())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }
}
