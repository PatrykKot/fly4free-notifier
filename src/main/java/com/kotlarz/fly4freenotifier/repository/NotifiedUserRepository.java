package com.kotlarz.fly4freenotifier.repository;

import com.kotlarz.fly4freenotifier.domain.notified.NotifiedUser;
import org.springframework.data.repository.CrudRepository;

public interface NotifiedUserRepository extends CrudRepository<NotifiedUser, Long> {
}
