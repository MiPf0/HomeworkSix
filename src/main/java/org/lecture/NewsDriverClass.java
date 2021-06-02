package org.lecture;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Driver Class for SWEN-NEWS Service
 */
@Slf4j
public class NewsDriverClass {

    /**
     * Driver method for SWEN-NEWS Service
     */
    public static void main(String[] args) {

        NewsAgency newsAgency = new NewsAgency();
        Path path = Paths.get("src/main/resources/news.csv");

        String menuStart = """
            ________________________________________________________________________
            | (1) register a subscriber          (3) list current subscribers      |
            | (2) add category to subscriber                                       |
            | .....................................................................|
            | (4) update the news list           (6) get news for each subscriber  |
            | (5) send news to all subscribers                                     |
            | .....................................................................|
            | (7) exit the program                                                 |
            |______________________________________________________________________|""";


        System.out.println("""
                ----------------------------------------------------
                Welcome to SWEN-NEWS - know everything ahead!
                """);
        System.out.println("What would you like to do?");

        Scanner sc = new Scanner(System.in);

        do {
            System.out.println(menuStart);
            System.out.println("Your choice: ");
            String userInput = sc.next();
            switch (userInput) {
                case "1" -> {
                    Subscriber ss = getNewSubscriber();
                    newsAgency.addObserver(ss);
                    System.out.println("New subscriber registered.");
                    log.info("New subscriber successfully registered.");
                }
                case "2" -> {
                    addCategoryToSubscriber(newsAgency);
                    log.info("Category successfully added.");
                }
                case "3" -> listSubscribers(newsAgency);
                case "4" -> {
                    newsAgency.removeAllNews();
                    newsAgency.addAllNews(FileReader.readNewsCSVFile(path));
                    System.out.println("News list updated.");
                    log.info("News list successfully updated.");
                }
                case "5" -> {
                    sendNews(newsAgency,newsAgency.getNews());
                    System.out.println("News sent.");
                    log.info("News successfully sent.");
                }
                case "6" -> {
                    List<Subscriber> ssList = newsAgency.transformReadersToSubscribers();
                    ssList.forEach(Subscriber::readNews);
                    newsAgency.readers = new ArrayList<>(ssList);
                    log.info("News printed successfully for every reader.");
                }
                default -> System.exit(0);
            }
        } while(true);
    }

    /**
     * lists all current subscribers and prints them to the console
     * @param na news agency object
     */
    public static void listSubscribers(NewsAgency na) {
        for (Subscriber ss : na.transformReadersToSubscribers()) {
            System.out.println(ss.getName());
            for(Category c : ss.getObservableCategories()) {
                System.out.println("---" + c.getName());
            }
        }
    }

    /**
     * gets new subscriber from user input
     * @return Subscriber object (new subscriber)
     */
    public static Subscriber getNewSubscriber() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter name of new subscriber: ");
        return new Subscriber(sc.next());
    }

    /**
     * sends news from a source through the newsAgency object to all current subscribers thereof
     * @param na news agency
     * @param news list of news (e.g. input from a file read)
     */
    public static void sendNews(NewsAgency na,List<News> news) {
        List<Subscriber> ssList = na.transformReadersToSubscribers();
        for(News n : news) {
            for (int i=0;i<ssList.size();i++) {
                ssList.get(i).update(n);
            }
        }
        na.readers = new ArrayList<>(ssList);
    }

    /**
     * adds a category to a subscriber which belongs to the object news agency
     * @param na news agency
     */
    public static void addCategoryToSubscriber(NewsAgency na) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the name of the subscriber to which you want to add a category: ");
        String stringSubscriberToBeAddedACategory = sc.next();

        int idxToBeFound = -1;

        for(Channel chan : na.getReaders()) {
            Subscriber ss = (Subscriber) chan;
            if(stringSubscriberToBeAddedACategory.equals(ss.getName())) {
                idxToBeFound = na.getReaders().indexOf(chan);
            }
        }

        Subscriber SubscriberToBeAddedACategory = (Subscriber) na.getReaders().get(idxToBeFound);

        if (SubscriberToBeAddedACategory != null) {

            if (SubscriberToBeAddedACategory.getObservableCategories().size() > 3) {
                System.out.println("No more categories to add.");
            } else {
                System.out.println("Which category you would like to add?");
                if (!SubscriberToBeAddedACategory.getObservableCategories().contains(Category.TOP_STORIES)) {
                    System.out.println(Category.TOP_STORIES.getName());
                }
                if (!SubscriberToBeAddedACategory.getObservableCategories().contains(Category.SPORTS)) {
                    System.out.println(Category.SPORTS.getName());
                }
                if (!SubscriberToBeAddedACategory.getObservableCategories().contains(Category.POLITICS)) {
                    System.out.println(Category.POLITICS.getName());
                }
                if (!SubscriberToBeAddedACategory.getObservableCategories().contains(Category.BOULEVARD)) {
                    System.out.println(Category.BOULEVARD.getName());
                }

                System.out.println("Please type your choices separated by a comma (e.g. 'Top stories,Politics,Boulevard'):");
                String choicesString = sc.next();

                String[] choices = choicesString.split(",");

                for (String s : choices) {
                    if (s.equals(Category.TOP_STORIES.getName())) {
                        SubscriberToBeAddedACategory.getObservableCategories().add(Category.TOP_STORIES);
                    }
                    if (s.equals(Category.SPORTS.getName())) {
                        SubscriberToBeAddedACategory.getObservableCategories().add(Category.SPORTS);
                    }
                    if (s.equals(Category.POLITICS.getName())) {
                        SubscriberToBeAddedACategory.getObservableCategories().add(Category.POLITICS);
                    }
                    if (s.equals(Category.BOULEVARD.getName())) {
                        SubscriberToBeAddedACategory.getObservableCategories().add(Category.BOULEVARD);
                    }
                }

                na.getReaders().set(idxToBeFound,SubscriberToBeAddedACategory);

                System.out.println("New category/ies " + choicesString + " added.");
            }
        }
    }
}