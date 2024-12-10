/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package student;

import java.util.Comparator;

/**
 *
 * @author harlandnorcott
 */
public class RankedSong implements Comparable {
    
    private int rank;
    
    private Song song;
    
    public RankedSong(int songRank, Song currSong) {
        this.song = currSong;
        this.rank = songRank;
    }

    public int getRank() {
        return rank;
    }

    public Song getSong() {
        return song;
    }
    
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        
        str.append(String.format("%d, %s", this.rank, this.song.toString()));
        
        return str.toString();
    }

    @Override
    public int compareTo(Object that) {
        int compareVar = this.toString().compareToIgnoreCase(that.toString());
        
        return compareVar;
    }
}
