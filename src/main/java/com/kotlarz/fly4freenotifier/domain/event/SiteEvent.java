package com.kotlarz.fly4freenotifier.domain.event;

import com.kotlarz.fly4freenotifier.domain.notified.SiteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteEvent {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SiteType siteType;

    @Column(nullable = false)
    private String content;
}
