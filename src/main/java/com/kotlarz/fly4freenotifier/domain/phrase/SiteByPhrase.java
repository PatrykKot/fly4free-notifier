package com.kotlarz.fly4freenotifier.domain.phrase;

import com.kotlarz.fly4freenotifier.domain.event.SiteEvent;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SiteByPhrase {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Boolean emailSent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phraseid")
    private Phrase phrase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "siteeventid")
    private SiteEvent siteEvent;
}
