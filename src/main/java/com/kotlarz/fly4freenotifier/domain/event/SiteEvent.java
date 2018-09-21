package com.kotlarz.fly4freenotifier.domain.event;

import com.kotlarz.fly4freenotifier.domain.notified.SiteType;
import com.kotlarz.fly4freenotifier.domain.phrase.Phrase;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = @Index(columnList = "hash", name = "SITE_EVENT_HASH_INDEX"))
public class SiteEvent {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SiteType siteType;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String hash;

    @Column(nullable = false)
    private Date date;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            joinColumns = {@JoinColumn(name = "siteeventid")},
            inverseJoinColumns = {@JoinColumn(name = "phraseid")}
    )
    private List<Phrase> phrases;
}
