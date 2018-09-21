package com.kotlarz.fly4freenotifier.domain.phrase;

import com.kotlarz.fly4freenotifier.domain.event.SiteEvent;
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

    @ManyToMany(mappedBy = "phrases", fetch = FetchType.LAZY)
    private List<SiteEvent> siteEvents;
}
