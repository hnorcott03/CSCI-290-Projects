/**
 * File: SearchByArtistPrefix.java
 *****************************************************************************
 *                       Revision History
 *****************************************************************************
 * 09/26/2024 updated search methods - added javadoc
 * 09/25/2024 - updated search and main methods
 * 8/2015 Anne Applin - Added formatting and JavaDoc
 * 2015 - Bob Boothe - starting code
 *****************************************************************************
 *
 */
package student;

import java.util.*;
import java.util.stream.Stream;

/**
 * Search by Artist Prefix searches the artists in the song database for artists
 * that begin with the input String
 *
 * @author Bob Booth
 */
public class SearchByArtistPrefix {

    // keep a local direct reference to the song array
    private Song[] songs;

    /**
     * constructor initializes the property. [Done]
     *
     * @param sc a SongCollection object
     */
    public SearchByArtistPrefix(SongCollection sc) {
        songs = sc.getAllSongs();
    }

    /**
     * find all songs matching artist prefix uses binary search should operate
     * in time log n + k (# matches) converts artistPrefix to lowercase and
     * creates a Song object with artist prefix as the artist in order to have a
     * Song to compare. walks back to find the first "beginsWith" match, then
     * walks forward adding to the arrayList until it finds the last match.
     *
     * @param artistPrefix all or part of the artist's name
     * @return an array of songs by artists with substrings that match the
     * prefix
     */
    public Song[] search(String artistPrefix) {
        // write this method
        /*
         * searchResult holds the idx of found match from Arrays.binarySearch()
         * matchingSongs holds all song matches (starts empty)
         * artistPrefix gets made lowercase to help with case-insensitivity
         * - Jemma
         */
        int searchResult;
        int counter = 0;
        ArrayList matchingSongs = new ArrayList();
        artistPrefix = artistPrefix.toLowerCase();

        Song.CmpArtist comp = new Song.CmpArtist();

        Song key = new Song(artistPrefix, "", "");

        // put return value from binarySearch
        /*
         * Arrays.binarySearch returns the positive index an exact match was found or
         * the two's compliment of the insertion point (for our case it should be the
         * first prefix match)
         * - Jemma
         */
        searchResult = Arrays.binarySearch(songs, key, comp);
        //outputs index of binary search
        System.out.println("index from BS: " + searchResult);
        System.out.println("Binary search comparions:"
                + ((CmpCnt) comp).getCmpCnt());

        //finds front from negative binary search result
        //int i = searchResult;
        if (searchResult < 0) {
            searchResult = -searchResult - 1;
        }
        //outputs front
        System.out.println("Front found at " + searchResult);

        // If an exact match was found (searchResult is not negative) - J
        if (searchResult > -1) {
            // Add the current song to matching songs - J
            matchingSongs.add(songs[searchResult]);
            // index gets the position for searchResult and loops backwards for matches - J
            int index = searchResult;
            while (index > 0 && songs[index - 1].getArtist().toLowerCase().startsWith(artistPrefix)) {
                --index;
                counter++;
                matchingSongs.add(songs[index]);
            }
            // index gets the position for searchResult and loops forwards for matches - J
            index = searchResult;
            while (index < songs.length && songs[index].getArtist().toLowerCase().startsWith(artistPrefix)) {
                ++index;
                counter++;
                matchingSongs.add(songs[index]);
            }
        } else {
            int index = (searchResult * -1) + 1;
            while (index + 1 < songs.length && songs[index + 1].getArtist().toLowerCase().startsWith(artistPrefix)) {
                ++index;
                counter++;
                matchingSongs.add(songs[index]);
            }
        }
        // else? - J
        // Partial match is two's compliment: index = -(searchResult + 1) - J
        // index gets the position for two's compliment of searchResult and loops
        // forwards for matches - J

        int compCount = ((CmpCnt) comp).getCmpCnt() + counter;
        int log = (int) (Math.log(songs.length) / Math.log(2));
        //rest of instrument outputs
        System.out.println("Comparisons to build the list: " + counter);
        System.out.println("Actual complexity is: " + compCount);
        System.out.println("k is " + matchingSongs.size());
        System.out.println("log_{2}(n) = " + log);
        System.out.println("Theorectial complexity k + log(n): "
                + (matchingSongs.size() + log));

        return (Song[]) matchingSongs.toArray(Song[]::new);
    }

    /**
     * testing method for this unit
     *
     * @param args command line arguments set in Project Properties - the first
     * argument is the data file name and the second is the partial artist name,
     * e.g. be which should return beatles, beach boys, bee gees, etc.
     */
    public static void main(String[] args) {
        System.out.println(args[0]);
        if (args.length == 0) {
            System.err.println("usage: prog songfile [search string]");
            return;
        }

        SongCollection sc = new SongCollection(args[0]);
        SearchByArtistPrefix sbap = new SearchByArtistPrefix(sc);

        if (args.length >= 0) {
            System.out.println("searching for: " + args[1]);
            Song[] byArtistResult = sbap.search(args[1]);

            Stream.of(byArtistResult).limit(10).forEach(System.out::println);
        }
    }
}
