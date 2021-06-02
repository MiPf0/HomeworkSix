package org.lecture;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * NewsAgency class
 */
@Getter
public class NewsAgency {

    private List<News> news = new ArrayList<>();
    public List<Channel> readers = new ArrayList<>();

    /**
     * adds new observer to the readers list
     * @param ss Subscriber
     */
    public void addObserver(Subscriber ss) {
        this.readers.add(ss);
    }

    /**
     * transforms reader (of type Channel [interface]) to subscriber in order to work with it more easily
     * @return
     */
    public List<Subscriber> transformReadersToSubscribers() {
        List<Subscriber> lp = new ArrayList<>();
        for(Channel cl : readers) {
            lp.add((Subscriber) cl);
        }
        return lp;
    }

    /**
     * adds news to the nes list
     * @param newsList
     */
    public void addAllNews(List<News> newsList) {
        this.news = newsList;
    }

    /**
     * removes all news from the news list
     */
    public void removeAllNews() {
        this.news = new ArrayList<>();
    }

}