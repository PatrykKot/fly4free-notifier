package com.kotlarz.fly4freenotifier.repository;

import com.kotlarz.fly4freenotifier.domain.event.SiteEvent;
import org.springframework.data.repository.CrudRepository;

public interface SiteEventRepository extends CrudRepository<SiteEvent, Long> {
}
