package com.kotlarz.fly4freenotifier.repository;

import com.kotlarz.fly4freenotifier.domain.event.SiteEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SiteEventRepository extends JpaRepository<SiteEvent, Long> {
    List<SiteEvent> findByHash(String hash);
}
