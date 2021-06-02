package org.lecture;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * News class
 */
@Getter
@EqualsAndHashCode
public class News {
    Category category;
    String text;
    LocalDateTime ldt;
    Boolean read = false;

    public News(Category c, String s, LocalDateTime ldt) {
        this.category = c;
        this.text = s;
        this.ldt = ldt;
    }

    @Override
    public String toString() {
        return category + " --- " + text + " " + ldt;
    }
}