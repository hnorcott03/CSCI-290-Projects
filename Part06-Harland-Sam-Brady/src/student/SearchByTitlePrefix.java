/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package student;

import student.Song.CmpTitle;

/**
 *
 * @author harlandnorcott
 */
public class SearchByTitlePrefix {
    
    private CmpTitle titleCmp = new CmpTitle();
    private RaggedArrayList RAL = new RaggedArrayList(titleCmp);
    
    public SearchByTitlePrefix(SongCollection sC) {
        
        for(Song currSong : sC.getAllSongs()) {
            RAL.add(currSong);
        }
        
    }
    
    public Song[] search(String titlePrefix) {
        
    }
}
