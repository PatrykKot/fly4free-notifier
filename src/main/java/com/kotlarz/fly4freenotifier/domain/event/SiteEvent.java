package com.kotlarz.fly4freenotifier.domain.event;

import com.kotlarz.fly4freenotifier.domain.notified.SiteType;
import com.kotlarz.fly4freenotifier.domain.phrase.SiteByPhrase;
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

    @Column(nullable = false, length = 4096)
    private String content;

    @Column(nullable = false, length = 4096)
    private String normalizedContent;

    @Column(length = 4096)
    private String link;

    @Column(length = 4096)
    private String innerTitle;

    @Column(nullable = false)
    private String hash;

    @Column(nullable = false)
    private Date date;

    @OneToMany(mappedBy = "siteEvent", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<SiteByPhrase> siteByPhrases;
}
