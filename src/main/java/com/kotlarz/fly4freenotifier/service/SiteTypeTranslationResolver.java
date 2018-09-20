package com.kotlarz.fly4freenotifier.service;

import com.kotlarz.fly4freenotifier.domain.notified.SiteType;
import org.springframework.stereotype.Service;

@Service
public class SiteTypeTranslationResolver {
    public String translate(SiteType type) {
        switch (type) {
            case FLY4FREE_FACEBOOK:
                return "FB Fly4Free";
            default:
                return type.name();
        }
    }
}
