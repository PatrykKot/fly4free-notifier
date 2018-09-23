package com.kotlarz.fly4freenotifier.web.controller;

import com.kotlarz.fly4freenotifier.service.SiteEventService;
import com.kotlarz.fly4freenotifier.web.dto.CountedResponse;
import com.kotlarz.fly4freenotifier.web.dto.site.SiteEventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/site/events")
public class SiteEventController {
    @Autowired
    private SiteEventService siteEventService;

    @GetMapping
    public CountedResponse<SiteEventDto> get(@RequestParam(name = "sortBy", required = false) String sortBy,
                                             @RequestParam(name = "descending", required = false, defaultValue = "false") Boolean descending,
                                             @RequestParam("page") Long page,
                                             @RequestParam(value = "rowsPerPage", required = false) Long rowsPerPage,
                                             @RequestParam(value = "search", required = false) String search) {
        List<SiteEventDto> data = siteEventService.getDtos(sortBy, descending, page, rowsPerPage, search);
        Long count = siteEventService.countBySearch(search);
        return new CountedResponse<>(data, count);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    public void deleteAll() {
        siteEventService.deleteAll();
    }
}
