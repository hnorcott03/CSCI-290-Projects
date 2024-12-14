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
            return bestSubstring.length();
        }
        return 0;
    }

    public static final Comparator<RankedSong> RankComparator = new Comparator<RankedSong>() {
        @Override
        public int compare(RankedSong a, RankedSong b) {

            return a.getRank() - b.getRank();
        }
    };

    public static void main(String args[]) {
        if (args.length < 2) {
            System.out.println("Usage : java PhraseRanking <song_file_path>\"<lyrics_phrase>\"");
            return;
        }
        String songFilepath = args[0];
        String lyricsPhrase = args[1];

        SongCollection sc = new SongCollection(songFilepath);
        int songCount = 0;
        List<RankedSong> matches = new ArrayList();
        for (Song currentSong : sc.getAllSongs()) {
            currSong = currentSong;
            int currSongRank = rankPhrase(currentSong.getLyrics(), lyricsPhrase);
            if (currSongRank > 0) {
                songCount++;
                RankedSong currPhraseRanked = new RankedSong(currSongRank, currSong);
                matches.add(currPhraseRanked);
            }
        }

        Collections.sort(matches, RankComparator);

        System.out.println("Total matches found: " + matches.size());
        System.out.println("First " + Math.min(10, matches.size()) + " matches:");
        for (int i = 0; i < Math.min(10, matches.size()); i++) {
            System.out.println(matches.get(i).getRank()
                    + ", " + matches.get(i).getSong().getTitle());
        }
    }
}
