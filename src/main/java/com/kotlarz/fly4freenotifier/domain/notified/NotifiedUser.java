package com.kotlarz.fly4freenotifier.domain.notified;

import com.kotlarz.fly4freenotifier.domain.phrase.Phrase;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotifiedUser {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Phrase> phrases;
}
