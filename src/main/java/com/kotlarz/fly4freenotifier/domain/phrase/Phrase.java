package com.kotlarz.fly4freenotifier.domain.phrase;

import com.kotlarz.fly4freenotifier.domain.notified.NotifiedUser;
import com.kotlarz.fly4freenotifier.domain.notified.SiteType;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Phrase {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SiteType siteType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ownerid")
    private NotifiedUser owner;

    @OneToMany(mappedBy = "phrase", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<SiteByPhrase> siteByPhrases;
}
