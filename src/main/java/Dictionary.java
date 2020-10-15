/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ACER
 */
import java.util.ArrayList;
import java.util.*; 
import java.util.List;



public class Dictionary {
    public ArrayList<Word> wordList = new ArrayList<>();
    
    
    public void add(Word word) {
        wordList.add(word);
    }

    public String getWordTarget(int i) {
        return wordList.get(i).getWord_target();
    }

    public String getWordExplain(int i) {
        return wordList.get(i).getWord_explain();
    }
    
    public Word getWord(int i) {
        return wordList.get(i);
    }
    public void Soort() {
        Collections.sort(wordList, new SortbyDict()); 
    }
    
    public ArrayList<Word> subList(int a, int b) {
        return new ArrayList<Word>(wordList.subList(a,b));
    }
}
