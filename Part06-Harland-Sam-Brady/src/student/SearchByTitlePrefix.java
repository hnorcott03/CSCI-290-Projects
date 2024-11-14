/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package student;

import java.util.stream.Stream;
import student.Song.CmpTitle;

/**
 *
 * @author harlandnorcott
 */
public class SearchByTitlePrefix {

    private CmpTitle titleCmp = new CmpTitle();
    private RaggedArrayList RAL = new RaggedArrayList(titleCmp);

    public SearchByTitlePrefix(SongCollection sC) {

        for (Song currSong : sC.getAllSongs()) {
            RAL.add(currSong);
        }

    }

    public Song[] search(String titlePrefix) {

        String endElement = "";
        
        if ("".equals(titlePrefix)) {
            endElement = "a";
        }
        int i = titlePrefix.length() - 1;
        while (titlePrefix.charAt(i) == 'z' && i >= 0) {
            i--;
        }
        
        if (i == -1) {
            endElement = endElement + 'a';
        } else {
            endElement = titlePrefix.substring(0, i)
                    + (char) ((int) (titlePrefix.charAt(i)) + 1)
                    + titlePrefix.substring(i + 1);
        }

        Song searchSong = new Song("", titlePrefix, "");
        Song endSong = new Song("", endElement, "");

        int size = RAL.subList(searchSong, endSong).size();
        Song[] searchResults = new Song[size];
        RAL.subList(searchSong, endSong).toArray(searchResults);

        return searchResults;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("usage: prog songfile [search string]");
            return;
        }
        SongCollection sc = new SongCollection(args[0]);
        SearchByTitlePrefix sbtp = new SearchByTitlePrefix(sc);

        if (args.length >= 1) {
            System.out.println("searching for: " + args[1]);
            Song[] byTitleResult = sbtp.search(args[1]);
            System.out.println("Total matches: " + byTitleResult.length);
            Stream.of(byTitleResult).limit(10).forEach(System.out::println);
        }
    }
}
