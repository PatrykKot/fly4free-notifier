package com.kotlarz.fly4freenotifier.repository;

import com.kotlarz.fly4freenotifier.domain.event.SiteEvent;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SiteEventRepository extends CrudRepository<SiteEvent, Long> {
    List<SiteEvent> findByHash(String hash);
}
