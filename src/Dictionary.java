/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Dictionary {
    public ArrayList<Word> wordList = new ArrayList<>();

    public Dictionary() {

    }

    public Dictionary(List<Word> words) {
        this.wordList = (ArrayList<Word>) words;
    }

    public void reverse() {
        Collections.reverse(wordList);
    }

    public void pre(Word word) {
        wordList.add(0, word);
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

    public int size() {
        return wordList.size();
    }

    public void clear() {
        wordList.clear();
    }
}
