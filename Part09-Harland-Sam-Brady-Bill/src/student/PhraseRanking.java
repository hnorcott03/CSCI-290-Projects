package student;

import java.util.*;
import java.util.regex.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author harlandnorcott
 */
public class PhraseRanking {

    static int rankPhrase(String lyrics, String lyricsPhrase) {

        Pattern phrasePattern = Pattern.compile("[a-zA-z_0-9]+", Pattern.CASE_INSENSITIVE);
        Matcher phraseMatcher = phrasePattern.matcher(lyricsPhrase);
        List<String> phraseWords = new ArrayList<>();

        while (phraseMatcher.find()) {
            phraseWords.add(phraseMatcher.group());
        }

        List<List<Integer>> matchers = new ArrayList<>();
        for (String word : phraseWords) {
            Pattern wordPattern = Pattern.compile("\\b" + word + "\\b", Pattern.CASE_INSENSITIVE);
            Matcher wordMatcher = wordPattern.matcher(lyrics);
            List<Integer> wordIndices = new ArrayList<>();

            while (wordMatcher.find()) {
                if(word.equals(phraseWords.get(phraseWords.size() - 1))) {
                    wordIndices.add(wordMatcher.end());
                } else {
                    wordIndices.add(wordMatcher.start());
                }
            }
            matchers.add(wordIndices);
        }
        
        Integer smallestCurrentDistance = Integer.MAX_VALUE;
        Integer bestFirst = -1;
        Integer bestLast = -1;
        
        for (Integer indexOfFirst : matchers.get(0)) {
            for (Integer indexOfLast : matchers.get(matchers.size() - 1)) {
                if(indexOfLast > indexOfFirst && (indexOfLast - indexOfFirst < smallestCurrentDistance)) {
                    int leftIndex = indexOfLast;
                    int rightIndex = indexOfFirst;
                    boolean betweenMatch = true;
                    
                    
                    
                }
            }
        }



//        lyrics = lyrics.toLowerCase().replaceAll("[^a-z ]", " ");
//        lyricsPhrase = lyricsPhrase.toLowerCase().replaceAll("[^a-z ]", " ");
//        String[] searchWords = lyricsPhrase.split("\\s+");
//        String[] lyricsWords = lyrics.split("\\s+");
//        
//        int rank = 0;
//        
//        // Iterate through lyrics array until a word matches the first
//        // the first word of the search phrase.
//        
//        int[] firstWordMatchLocs = new int[30];
//        int j = 0;
//        
//        for(int i = 0; i < lyricsWords.length; i++) {
//            if(lyricsWords[i].equals(searchWords[0])) {
//                firstWordMatchLocs[j] = i;
//                j++;
//            }
//        }
//        
//        StringBuilder fullLyricPhrase = new StringBuilder();
//        
//        for(int k = 0; k < firstWordMatchLocs.length; k++) {
//            int l = firstWordMatchLocs[k];
//            while(!lyricsWords[l].equals(searchWords[searchWords.length - 1])) {
//                fullLyricPhrase.append(" ").append(lyricsWords[l]);
//                l++;
//            }
//            
//        }
//        
//        rank = fullLyricPhrase.toString().trim().length();
//        for(int i = 0; i < lyricsWords.length; i++) {
//            if(lyricsWords[i].equals(searchWords[0])) {
//                fullLyricPhrase.append(lyricsWords[i]);
//                int j = 1;
//                int k = i + 1;
//                while(j < searchWords.length && k < lyricsWords.length) {
//                    
//                    fullLyricPhrase.append(" ").append(lyricsWords[k]);
//                    if(lyricsWords[k].equals(searchWords[j])) {
//                        j++;
//                    }
//                    
//                    String[] possMatchArr = fullLyricPhrase.toString().split("\\s+");
//                    if(j == searchWords.length && possMatchArr[possMatchArr.length - 1].equals(searchWords[searchWords.length - 1]))  {
//                        rank = fullLyricPhrase.toString().trim().length();
//                    }
//                    
//                    k++;
//                }
//            }
//        }
        //System.out.println(lyrics + ", " + lyricsPhrase);
//        if (rank == 0) {
//            return -1;
//        } else {
//            return rank;
//        }
        return 0;
    }

    public static void main(String args[]) {
        SongCollection sc = new SongCollection(args[0]);
        int songCount = 0;
        for (Song currSong : sc.getAllSongs()) {
            int currSongRank = rankPhrase(currSong.getLyrics(), args[1]);
            if (currSongRank > -1) {
                System.out.println(currSongRank + ", " + currSong.toString());
                songCount++;
            }
        }
        System.out.println("Total matches: " + songCount);
    }
}
