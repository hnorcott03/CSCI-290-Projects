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
    private TreeMap<String, TreeSet<Song>> lyricsMap;

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
                } else if (lyricsMap.containsKey(currWord)) {
                    lyricsMap.get(currWord).add(currSong);
                }

            }
        }
    }

    public Song[] search(String lyricsWords) {
        String[] wordArr = lyricsWords.toLowerCase().replaceAll("[^a-z ]", " ").split("\\s+");
        TreeSet<Song> searchSet = new TreeSet<>();
        TreeSet<Song> tempSet;
        TreeMap<String, TreeSet<Song>> searchMap = new TreeMap<>();
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
        for (String currWord : searchMap.keySet()) {
            searchSet.retainAll(searchMap.get(currWord));
        }

        Song[] matches = searchSet.toArray(new Song[0]);

        return matches;
    }

    public void Statistics() {
        int numKeys = lyricsMap.size();
        System.out.println("Number of keys in the map: " + numKeys);

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

    public void printSet() {
        for (Object com : comWords.toArray()) {
            System.out.println((String) com);
        }
        System.out.println("Number Common words loaded: " + comWords.size());
    }

    public static void main(String[] args) {
        if (args.length >= 2) {
            // First argument: name of the song file
            String songFile = args[0];
            // Second argument: string of lyric words to search
            String lyricWords = args[1];

            // Load the song collection and initialize the search object
            SongCollection sc = new SongCollection(songFile);
            SearchByLyricsWords sbl = new SearchByLyricsWords(sc);

            // Perform the search
            Song[] matches = sbl.search(lyricWords);

            // Print the results
            System.out.println("Total matches found: " + matches.length);
            System.out.println("First 10 matches:");
            for (int i = 0; i < Math.min(10, matches.length); i++) {
                System.out.println("Artist: " + matches[i].getArtist() + ", Title: " + matches[i].getTitle());
            }
        } else {
            System.err.println("usage: prog songfile \"lyric words\"");
        }
    }
}
