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

                if (currWord.length() > 1 && tempSet == null && !comWords.contains(currWord) && !lyricsMap.containsKey(currWord)) {
                    tempSet = new TreeSet<>();
                    tempSet.add(currSong);
                    lyricsMap.put(currWord, tempSet);
                    //  System.out.println(currWord + ", " + lyricsMap.get(currWord));
                } else if (lyricsMap.containsKey(currWord)) {
                    lyricsMap.get(currWord).add(currSong);
                }

            }
        }
    }

    public Song[] search(String lyricsWords) {
        String[] wordArr = lyricsWords.toLowerCase().replaceAll("[^a-z ]", " ").split("\\s+");
        TreeSet<Song> searchSet = new TreeSet();
        TreeSet<Song> tempSet = new TreeSet();
        TreeMap<String, TreeSet<Song>> searchMap = new TreeMap();
        for (String currWord : wordArr) {
            if (currWord.length() > 1 && !comWords.contains(currWord)) {
                tempSet = new TreeSet<>();
                if (lyricsMap.containsKey(currWord)) {
                    searchMap.put(currWord, lyricsMap.get(currWord));
                } else {
                    tempSet = new TreeSet<>();
                    searchMap.put(currWord, tempSet);

                }
            }
        }
        searchSet = searchMap.get(searchMap.firstKey());
        for (String currWord : searchMap.sequencedKeySet()) {
            searchSet.retainAll(searchMap.get(currWord));
        }
        //  System.out.println(searchSet);

        Song[] matches = new Song[searchSet.toArray().length];

        System.arraycopy(searchSet.toArray(), 0, matches, 0, searchSet.toArray().length);

        for (Song currSong : matches) {
            System.out.println(currSong);
        }

        return null;
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
        for (Object com : comWords.toArray()) {
            System.out.println((String) com);
        }
        System.out.println("Number Common words loaded: " + comWords.size());
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            SongCollection sc = new SongCollection(args[0]);
            SearchByLyricsWords sbl = new SearchByLyricsWords(sc);
            sbl.search("We don’t need no education");
            // sbl.printSet();
            //  sbl.Statistics();
        } else {
            System.err.println("usage: prog songfile");
        }
    }
}
