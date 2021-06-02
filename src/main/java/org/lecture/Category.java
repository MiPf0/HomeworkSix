package org.lecture;

import lombok.Getter;

/**
 * category extended enum
 */
@Getter
public enum Category {
    TOP_STORIES("TopStories"),
    SPORTS("Sports"),
    POLITICS("Politics"),
    BOULEVARD("Boulevard");

    String name;

    Category(String name) {
        this.name = name;
    }
}