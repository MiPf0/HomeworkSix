package org.lecture;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class NewsDriverTest {

    @Test
    public void testGetSubscriber() {

        String mike = "Mike";
        System.setIn(new ByteArrayInputStream(mike.getBytes()));
        Subscriber mikeSub = NewsDriverClass.getNewSubscriber();
        Assertions.assertEquals(mike,mikeSub.getName());

        String susi = "Susi";
        System.setIn(new ByteArrayInputStream(susi.getBytes()));
        Subscriber susiSub = NewsDriverClass.getNewSubscriber();
        Assertions.assertEquals(susi,susiSub.getName());
    }

    @Test
    public void testListSubscriber() {

        NewsAgency na = new NewsAgency();

        Subscriber mike = new Subscriber("Mike");
        List<Category> mikeList = new ArrayList<>();
        mikeList.add(Category.POLITICS);
        mikeList.add(Category.BOULEVARD);
        mike.setObservableCategories(mikeList);
        na.getReaders().add(mike);

        Subscriber susi = new Subscriber("Susi");
        List<Category>susiList = new ArrayList<>();
        susiList.add(Category.SPORTS);
        susi.setObservableCategories(susiList);
        na.getReaders().add(susi);

        String expected = """
                Mike
                ---Politics
                ---Boulevard
                Susi
                ---Sports
                """;

        //information to get console output to string from StackOverflow
        //https://stackoverflow.com/questions/8708342/redirect-console-output-to-string-in-java
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(output);
        PrintStream old = System.out;
        System.setOut(ps);
        NewsDriverClass.listSubscribers(na);
        System.out.flush();
        System.setOut(old);

        String tested = output.toString();

        //information to use '.trim().replace("\r","")' from StackOverflow
        //https://stackoverflow.com/questions/36324452/assertequalsstring-string-comparisonfailure-when-contents-are-identical
        Assertions.assertEquals(expected.trim().replace("\r",""),tested.trim().replace("\r",""));
    }

    @Test
    public void testSendNews() {

        NewsAgency na = new NewsAgency();
        List<News> NewsList = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        News pol = new News(Category.POLITICS,"Macron & Merkel held talks about Middle East Conflict", LocalDateTime.parse("02.06.2021 11:15",dtf));
        News boul = new News(Category.BOULEVARD,"Meghan & Harry go back to royal",LocalDateTime.parse("07.06.2021 18:45",dtf));
        NewsList.add(pol);
        NewsList.add(boul);
        na.getNews().addAll(NewsList);

        Subscriber mike = new Subscriber("Mike");
        List<Category> mikeList = new ArrayList<>();
        mikeList.add(Category.POLITICS);
        mikeList.add(Category.BOULEVARD);
        mike.setObservableCategories(mikeList);
        na.getReaders().add(mike);

        NewsDriverClass.sendNews(na,NewsList);

        Assertions.assertEquals(na.getNews(),mike.getNews());
    }

    @Test
    public void testAddCategoryToSubscriber() {

        NewsAgency na = new NewsAgency();

        String input = "Mike"
                + "\n"
                + "Politics,Boulevard";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Subscriber mike = new Subscriber("Mike");
        na.getReaders().add(mike);

        NewsDriverClass.addCategoryToSubscriber(na);

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(Category.POLITICS);
        categoryList.add(Category.BOULEVARD);
        mike.setObservableCategories(categoryList);

        Assertions.assertEquals(mike,na.getReaders().get(0));
    }

}
