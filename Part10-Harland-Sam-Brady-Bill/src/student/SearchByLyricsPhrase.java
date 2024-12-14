/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package student;

import java.util.*;
import java.util.regex.*;

public class SearchByLyricsPhrase {
    private SongCollection songCollection;

    public SearchByLyricsPhrase(SongCollection songCollection) {
        this.songCollection = songCollection;
    }

    public Song[] search(String lyricsPhrase) {
        // Normalize the lyrics phrase and extract individual words
        Pattern phrasePattern = Pattern.compile("[a-zA-Z_0-9]+", Pattern.CASE_INSENSITIVE);
        Matcher phraseMatcher = phrasePattern.matcher(lyricsPhrase);
        List<String> phraseWords = new ArrayList<>();

        while (phraseMatcher.find()) {
            phraseWords.add(phraseMatcher.group());
        }

        List<RankedSong> rankedSongs = new ArrayList<>();

        for (Song song : songCollection.getAllSongs()) {
            String lyrics = song.getLyrics();
            int rank = rankPhrase(lyrics, phraseWords);
            if (rank > 0) {
                rankedSongs.add(new RankedSong(rank, song));
            }
        }

        // Sort by rank in descending order (best matches first)
        rankedSongs.sort((a, b) -> b.getRank() - a.getRank());

        // Extract songs into an array
        Song[] result = new Song[rankedSongs.size()];
        for (int i = 0; i < rankedSongs.size(); i++) {
            result[i] = rankedSongs.get(i).getSong();
        }

        return result;
    }

    private int rankPhrase(String lyrics, List<String> phraseWords) {
        List<List<Integer>> matchers = new ArrayList<>();

        // Find occurrences of each word in the lyrics
        for (String word : phraseWords) {
            Pattern wordPattern = Pattern.compile("\\b" + word + "\\b", Pattern.CASE_INSENSITIVE);
            Matcher wordMatcher = wordPattern.matcher(lyrics);
            List<Integer> wordIndexes = new ArrayList<>();

            while (wordMatcher.find()) {
                wordIndexes.add(wordMatcher.start());
            }

            if (wordIndexes.isEmpty()) {
                return 0; // If any word is missing, the phrase doesn't match
            }

            matchers.add(wordIndexes);
        }

        // Find the best match with the smallest distance between first and last words
        int smallestCurrentDistance = Integer.MAX_VALUE;

        for (Integer indexOfFirst : matchers.get(0)) {
            for (Integer indexOfLast : matchers.get(matchers.size() - 1)) {
                if (indexOfLast > indexOfFirst) {
                    boolean matchesInBetween = true;
                    int leftReference = indexOfFirst;

                    for (int i = 1; i < matchers.size() - 1; i++) {
                        List<Integer> currentList = matchers.get(i);
                        boolean found = false;

                        for (Integer currentIndex : currentList) {
                            if (currentIndex > leftReference && currentIndex < indexOfLast) {
                                leftReference = currentIndex;
                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            matchesInBetween = false;
                            break;
                        }
                    }

                    if (matchesInBetween) {
                        int distance = indexOfLast - indexOfFirst;
                        smallestCurrentDistance = Math.min(smallestCurrentDistance, distance);
                    }
                }
            }
        }

        return smallestCurrentDistance == Integer.MAX_VALUE ? 0 : smallestCurrentDistance;
    }
}
