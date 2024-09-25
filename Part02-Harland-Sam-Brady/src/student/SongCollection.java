/**
 * File: SongCollection.java
 ************************************************************************
 *                     Revision History (newest first)
 ************************************************************************
 *
 * 8.2016 - Anne Applin - formatting and JavaDoc skeletons added
 * 2015 -   Prof. Bob Boothe - Starting code and main for testing
 ************************************************************************
 */
package student;

import java.io.FileReader;
import java.util.*;
import java.util.stream.Stream;

/**
 * SongCollection.java Reads the specified data file and build an array of
 * songs.
 *
 * @author boothe
 */
public class SongCollection {

    private Song[] songs;

    /**
     * Note: in any other language, reading input inside a class is simply not
     * done!! No I/O inside classes because you would normally provide
     * precompiled classes and I/O is OS and Machine dependent and therefore not
     * portable. Java runs on a virtual machine that IS portable. So this is
     * permissable because we are programming in Java and Java runs on a virtual
     * machine not directly on the hardware.
     *
     * @param filename The path and filename to the datafile that we are using
     * must be set in the Project Properties as an argument.
     */
    public SongCollection(String filename) {

        // use a try catch block
        // read in the song file and build the songs array
        // there are several ways to read in the lyrics correctly.
        // the line feeds between lines and the blank lines between verses
        // must be retained.
        ArrayList<Song> songList = new ArrayList();
        try {
            Song song;
            String artist;
            String title;
            String lyrics;

            Scanner fileScnr = new Scanner(new FileReader(filename));
            fileScnr.useDelimiter("ARTIST=|TITLE=|LYRICS=");

            while (fileScnr.hasNext()) {
                artist = fileScnr.next().replace("\"", "").trim();
                title = fileScnr.next().replace("\"", "").trim();
                lyrics = fileScnr.next().replace("\"", "").trim();;
                song = new Song(artist, title, lyrics);
                songList.add(song);

            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        // sort the songs array using Arrays.sort (see the Java API)
        // this will use the compareTo() in Song to do the job.
        songs = songList.toArray(new Song[0]);
        Arrays.sort(songs);
    }

    /**
     * this is used as the data source for building other data structures
     *
     * @return the songs array
     */
    public Song[] getAllSongs() {
        return songs;
    }

    /**
     * unit testing method
     *
     * @param args
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("usage: prog songfile");
            return;
        }
        SongCollection sc = new SongCollection(args[0]);
        
        // todo: show song count
        Song[] list = sc.getAllSongs();
        System.out.println("Total songs: " + list.length + ", first songs: ");
        Stream.of(list).limit(10).forEach(System.out::println);
    }
}
