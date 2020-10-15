
import java.util.Comparator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ACER
 */
public class Word {
    private String word_target;
    private String word_explain;
    private String phonetic;
    

    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }

    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }

    public String getWord_explain() {
        return word_explain;
    }

    public String getWord_target() {
        return word_target;
    }
}

class SortbyDict implements Comparator<Word> 
{ 
    // Used for sorting in ascending order of 
    // roll number 
    public int compare(Word a, Word b) 
    { 
        return a.getWord_target().compareTo(b.getWord_target()); 
    } 
} 