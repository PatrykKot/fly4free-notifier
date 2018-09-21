package com.kotlarz.fly4freenotifier.repository;

import com.kotlarz.fly4freenotifier.domain.phrase.Phrase;
import org.springframework.data.repository.CrudRepository;

public interface PhraseRepository extends CrudRepository<Phrase, Long> {
}
