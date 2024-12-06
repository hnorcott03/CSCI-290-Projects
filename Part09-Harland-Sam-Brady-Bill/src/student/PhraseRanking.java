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
        StringBuilder bestSubstring = new StringBuilder();

        while (phraseMatcher.find()) {
            phraseWords.add(phraseMatcher.group());
        }

        List<List<Integer>> matchers = new ArrayList<>();
        for (String word : phraseWords) {
            Pattern wordPattern = Pattern.compile("\\b" + word + "\\b", Pattern.CASE_INSENSITIVE);
            Matcher wordMatcher = wordPattern.matcher(lyrics);
            List<Integer> wordIndexes = new ArrayList<>();

            while (wordMatcher.find()) {
                if (word.equals(phraseWords.get(phraseWords.size() - 1))) {
                    wordIndexes.add(wordMatcher.end() - 1);
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
                if (indexOfLast > indexOfFirst) {
                    int leftReference = indexOfFirst;
                    int rightReference = indexOfLast;
                    boolean betweenMatch = true;

                    for (int i = 0; i < matchers.size() - 1; i++) {
                        List<Integer> currentList = matchers.get(i);
                        boolean matchFound = false;
                        for (Integer currIndex : currentList) {
                            if (currIndex > leftReference && currIndex < rightReference) {
                                leftReference = currIndex;
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
                        int matchDistance = indexOfLast - indexOfFirst + 1;
                        if (matchDistance < smallestCurrentDistance && matchDistance > 0) {
                            smallestCurrentDistance = matchDistance;
                            bestFirst = leftReference;
                            bestLast = rightReference;
                        }
                    }

                }
            }
        }
        if (smallestCurrentDistance > 0 && bestFirst > -1 && bestLast > -1) {
            String matchSubstring = lyrics.substring(bestFirst, bestLast + 1).replace("\n", "nn");
            System.out.println(matchSubstring);
            bestSubstring.append(matchSubstring);
            return bestSubstring.length();
        } 
        return 0;
    }

    public static void main(String args[]) {
        SongCollection sc = new SongCollection(args[0]);
        int songCount = 0;
        for (Song currSong : sc.getAllSongs()) {
            int currSongRank = rankPhrase(currSong.getLyrics(), args[1]);
            if (currSongRank > 0) {
                System.out.println(currSongRank + ", " + currSong.toString());
                songCount++;
            }
        }
        System.out.println("Total matches: " + songCount);
    }
}
