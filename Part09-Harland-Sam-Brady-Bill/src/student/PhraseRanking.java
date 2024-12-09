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

    public static Song currSong;

    static int rankPhrase(String lyrics, String lyricsPhrase) {

        Pattern phrasePattern = Pattern.compile("[a-zA-z_0-9]+", Pattern.CASE_INSENSITIVE);
        Matcher phraseMatcher = phrasePattern.matcher(lyricsPhrase);
        List<String> phraseWords = new ArrayList<>();

        while (phraseMatcher.find()) {
            phraseWords.add(phraseMatcher.group());
        }

        List<List<Integer>> matchers = new ArrayList<>();
        // Simplified logic by using a standard for loop
        for (int i = 0; i < phraseWords.size(); i++) {
            Pattern wordPattern = Pattern.compile("\\b" + phraseWords.get(i) + "\\b", Pattern.CASE_INSENSITIVE);
            Matcher wordMatcher = wordPattern.matcher(lyrics);
            List<Integer> wordIndexes = new ArrayList<>();

            while (wordMatcher.find()) {
                if (i == phraseWords.size() - 1) {
                    wordIndexes.add(wordMatcher.end());
                } else {
                    wordIndexes.add(wordMatcher.start());
                }
            }
            matchers.add(wordIndexes);
        }

        int smallestCurrentDistance = Integer.MAX_VALUE;
        int bestFirst = -1;
        int bestLast = -1;

        for (Integer indexOfFirst : matchers.get(0)) {
            for (Integer indexOfLast : matchers.get(matchers.size() - 1)) {
                // You should also be checking that the current distance between the first and
                // last index is better than
                // the current best
                if (indexOfLast > indexOfFirst && (indexOfLast - indexOfFirst < smallestCurrentDistance)) {
                    int leftReference = indexOfFirst.intValue();
                    int rightReference = indexOfLast.intValue();
                    boolean betweenMatch = true;
                    // You don't need to iterate over the zeroth word index
                    for (int i = 1; i < matchers.size() - 1; i++) {
                        List<Integer> currentList = matchers.get(i);
                        boolean matchFound = false;
                        for (Integer currIndex : currentList) {
                            if (currIndex.intValue() > leftReference && currIndex.intValue() < rightReference) {
                                leftReference = currIndex.intValue();
                                matchFound = true;
                                break;
                            }
                        }
                        if (!matchFound) {
                            betweenMatch = false;
                            break;
                        }

                    }

                    if (betweenMatch) {
                        int matchDistance = indexOfLast - indexOfFirst;
                        if (matchDistance < smallestCurrentDistance) {
                            smallestCurrentDistance = matchDistance;
                            // this could just be assigned to indexOfFirst
                            bestFirst = indexOfFirst;
                            bestLast = indexOfLast;
                        }
                    }

                }
            }
        }
        if (smallestCurrentDistance > 0 && bestFirst != -1 && bestLast != -1) {
            StringBuilder bestSubstring = new StringBuilder();
            String matchSubstring = lyrics.substring(bestFirst, bestLast).replace("\n", " ");
            bestSubstring.append(matchSubstring);
            System.out.println(bestSubstring);
            return bestSubstring.length();
        }
        return 0;
    }

    public static void main(String args[]) {
        SongCollection sc = new SongCollection(args[0]);
        int songCount = 0;
        for (Song currentSong : sc.getAllSongs()) {
            currSong = currentSong;
            int currSongRank = rankPhrase(currentSong.getLyrics(), args[1]);
            if (currSongRank > 0) {
                System.out.println(currSongRank + ", " + currentSong.toString());
                songCount++;
            }
        }
        System.out.println("Total matches: " + songCount);
    }
}
