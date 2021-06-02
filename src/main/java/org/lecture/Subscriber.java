package org.lecture;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Subscriber class which implements the Channel interface
 */
@Slf4j
@Getter
@Setter
@EqualsAndHashCode
public class Subscriber implements Channel {

    private final String name;

    private List<News> news = new ArrayList<>();
    private final List<News> newsRead = new ArrayList<>();
    private List<Category> observableCategories = new ArrayList<>();
    private LocalDateTime latestUpdate = LocalDateTime.now();


    public Subscriber(String name) {
        this.name = name;
    }

    /**
     * update method which updates the news of this subscriber
     * @param news
     */
    @Override
    public void update(News news) {
        for (Category cat : observableCategories) {
            if (cat.equals(news.getCategory()) && !this.news.contains(news) && !this.newsRead.contains(news)) {
                this.news.add(news);
            }
        }
        log.info("Observers updated");
    }

    /**
     * prints news of this subscriber to the console
     */
    public void readNews() {
        LocalDateTime nextUpdate = latestUpdate.plusSeconds(15);
        if (LocalDateTime.now().isAfter(nextUpdate)) {
            System.out.println("All current news for " + name);
            if (news.isEmpty()) {
                System.out.println("No news.");
            } else {
                for (News n : news) {
                    System.out.println("---" + n);
                    this.newsRead.add(n);
                }
                this.news = new ArrayList<>();
            }
        } else {
            System.out.println("At the moment no new news.");
        }

        news.removeAll(newsRead);

        System.out.println();
        latestUpdate = LocalDateTime.now();
        log.info("News printed to the console and process finished.");
    }
}