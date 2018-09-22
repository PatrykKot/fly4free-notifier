package com.kotlarz.fly4freenotifier.web.controller;

import com.kotlarz.fly4freenotifier.service.SiteEventService;
import com.kotlarz.fly4freenotifier.web.dto.site.SiteEventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/site/events")
public class SiteEventController {
    @Autowired
    private SiteEventService siteEventService;

    @GetMapping
    public List<SiteEventDto> getAll() {
        return siteEventService.getAllDto().stream()
                .sorted(Comparator.comparing(SiteEventDto::getDate))
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    public void deleteAll() {
        siteEventService.deleteAll();
    }
}
