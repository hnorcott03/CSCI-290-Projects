/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package student;

import java.io.FileReader;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author sgatc
 */
public class SearchByLyricsWords {

    private Set<String> comWords;

    public SearchByLyricsWords(SongCollection songCol) {
        String fileName = "../commonWords.txt";
        comWords = new TreeSet<>();
        try {
            Scanner fileScnr = new Scanner(new FileReader(fileName));
            while (fileScnr.hasNext()) {
                comWords.add(fileScnr.next());
            }
        } catch (Exception e) {
            System.err.println("usage: prog wordFile");
        }

        TreeMap<String, TreeSet<Song>> lyricsMap = new TreeMap<>();

        for (Song currSong : songCol.getAllSongs()) {
            String[] words = currSong.getLyrics().toLowerCase().replaceAll("[^a-z ]", " ").split("\\s+");

            for (String currWord : words) {
                TreeSet<Song> tempSet = lyricsMap.get(currWord);
                if (currWord.length() > 1 && tempSet == null && !comWords.contains(currWord)) { 
                    tempSet = new TreeSet<>();
                    tempSet.add(currSong);
                    lyricsMap.put(currWord, tempSet);
                    
                    System.out.println(currWord + ", " + lyricsMap.get(currWord));
                }
            }
        }
    }

    //method for testing, prints all elements in treeset
    public void printSet() {
        Object ar[];
        ar = comWords.toArray();
        for (int i = 0; i < ar.length; i++) {
            System.out.println((String) ar[i]);
        }
    }

    //test method
    public static void main(String[] args) {
        if (args.length > 0) {
            SongCollection sc = new SongCollection(args[0]);
            SearchByLyricsWords sbl = new SearchByLyricsWords(sc);
            sbl.printSet();
            
        } else {
            System.err.println("usage: prog songfile");
            
        }

    }

}
