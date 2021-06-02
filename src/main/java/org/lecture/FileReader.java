package org.lecture;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * File Reader class
 */
public class FileReader {

    /**
     * reads News from a CSV File
     * @param path
     * @return List of Type News
     */
    public static List<News> readNewsCSVFile(Path path) {

        List<String> lines = null;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        lines.remove(0);

        List<News> news = new ArrayList<>();
        for (String l : lines) {
            String[] part = l.split(",");
            String category = part[0];
            Category c = getCategory(category);
            LocalDateTime ldt = null;
            if(c != null) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                ldt = LocalDateTime.parse(part[2],dtf);
                News singleNews = new News(c,part[1],ldt);
                news.add(singleNews);
            }
        }
        return news;
    }

    /**
     * gets Category of news
     * @param str
     * @return Category
     */
    public static Category getCategory(String str) {
        switch(str){
            case "TOP_STORIES" -> {
                return Category.TOP_STORIES;
            }
            case "SPORTS" -> {
                return Category.SPORTS;
            }
            case "POLITICS" -> {
                return Category.POLITICS;
            }
            case "BOULEVARD" -> {
                return Category.BOULEVARD;
            }
            default -> {
                return null;
            }
        }
    }
}