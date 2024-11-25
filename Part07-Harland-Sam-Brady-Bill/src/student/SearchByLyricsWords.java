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
    private TreeMap<String, TreeSet<Song>> lyricsMap;//make it a class member.

    public SearchByLyricsWords(SongCollection songCol) {
        String fileName = "../commonWords.txt";
        comWords = new TreeSet<>();
        try {
            Scanner fileScnr = new Scanner(new FileReader(fileName));
            while (fileScnr.hasNext()) {
                comWords.add(fileScnr.next().toLowerCase());
            }
           
        } catch (Exception e) {
            System.err.println("usage: prog wordFile");
        }
        lyricsMap = new TreeMap<>();

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

    //Statistics method
    public void Statistics() {
        //1.number of keys in the map
        int numKeys = lyricsMap.size();
        System.out.println("Number of keys in the map: " + numKeys);
        //iterate thru map 
        int totalSongRef = 0;
        for (TreeSet<Song> songs : lyricsMap.values()) {
            totalSongRef += songs.size();
        }
        System.out.println("Total number of Song references: " + totalSongRef);
        
        int spaceByMap = numKeys * 32;
        System.out.println("Space used by the map: " + spaceByMap + " bytes");
        
        int spaceBySets = totalSongRef * 32; 
        System.out.println("Space used by all sets: " + spaceBySets + " bytes");

        int totalSpaceUsed = spaceByMap + spaceBySets;
        System.out.println("Total space used by the compound data structure: " + totalSpaceUsed + " bytes");

        System.out.println("Space usage as a function of N is O(N)");
        
    }
    //test method
    //method for testing, prints all elements in treeset
    public void printSet() {
        Object ar[];
        ar = comWords.toArray();
        for(int i = 0; i < ar.length; i++) {
            System.out.println((String) ar[i]);
        }
        System.out.println("Number Common words loaded: " + comWords.size());
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            SongCollection sc = new SongCollection(args[0]);
            SearchByLyricsWords sbl = new SearchByLyricsWords(sc);
            sbl.printSet();
            sbl.Statistics();
        } else {
            System.err.println("usage: prog songfile");
        }
    }
}
