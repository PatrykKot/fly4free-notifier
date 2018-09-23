package com.kotlarz.fly4freenotifier.repository;

import com.kotlarz.fly4freenotifier.domain.event.SiteEvent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SiteEventRepository extends JpaRepository<SiteEvent, Long> {
    List<SiteEvent> findByHash(String hash);

    Long countByContentContainingIgnoreCase(@Param("search") String search);

    List<SiteEvent> findByContentContainingIgnoreCase(String content, Pageable pageable);
}
