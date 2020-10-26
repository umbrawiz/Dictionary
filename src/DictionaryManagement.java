/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DictionaryManagement {
    
    
    void insertFromCommandline(Dictionary dictionary) {
        Scanner s = new Scanner(System.in);
        int n;
        n = s.nextInt();
        s.nextLine();
        for (int i = 0; i < n; i++) {
            Word word = new Word();
            word.setWord_target(s.nextLine());
            word.setWord_explain(s.nextLine());
            dictionary.wordList.add(word);
        }
    }


    public int htmlLocation (String str) {
        return str.indexOf("<html>");
    }



    public Dictionary insertFromFile(String a) {
        Dictionary dictionary = new Dictionary();
        String str;
        File dicTxt = new File(a);
        try {
            int i;
            Scanner scanner = new Scanner(dicTxt);
            while (scanner.hasNextLine() ) {
                Word word = new Word();
                str = scanner.nextLine();
                i = htmlLocation(str);
                if (i==-1) {
                    continue;
                }
                //pho(str.substring(0, i), word);
                word.setWord_target(str.substring(0, i));
                word.setWord_explain(str.substring(i));
                dictionary.add(word);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            str = e.getLocalizedMessage();
        }
        dictionary.Soort();
        return dictionary;
    }

    public Word wordBuilder(String x, String y) {
        Word newWord = new Word();
        newWord.setWord_target(x);
        y = y.replaceAll("\n", "</b></font></li></ul><ul><li><font color='#cc0000'><b>");
        StringBuilder ex = new StringBuilder(y);
        ex.insert(0, "<i>"+ x + "</i><br/><ul><li><font color='#cc0000'><b>");
        ex.append("</b></font></li></ul></html>");
        newWord.setWord_explain(ex.toString());
        return newWord;
    }

    public void addWord(String x, String y, Dictionary dictionary){
        Word newWord = wordBuilder(x, y);
        dictionary.add(newWord);
        File file = new File("E_V.txt");
        FileWriter fr;
        String app = newWord.getWord_target() + "<html>" + newWord.getWord_explain() + System.lineSeparator();
        try {
            fr = new FileWriter(file, true);
            fr.write(app);
            fr.close();
            File add = new File("addedHistory.txt");
            fr = new FileWriter(add, true);
            fr.write(app);
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void removeWord(int index ,Dictionary dictionary ){
        Word removedWord = dictionary.wordList.get(index);
        dictionary.wordList.remove(index);
        FileWriter fr;
        String app = removedWord.getWord_target() + removedWord.getWord_explain() + System.lineSeparator();
        try {
            File add = new File("removedHistory.txt");
            fr = new FileWriter(add, true);
            fr.write(app);
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void searchedWord(int index, Dictionary dictionary){
        FileWriter fr;
        String app = dictionary.wordList.get(index).getWord_target() + dictionary.wordList.get(index).getWord_explain() + System.lineSeparator();
        try {
            File add = new File("History.txt");
            fr = new FileWriter(add, true);
            fr.write(app);
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void restore() {
        File src = new File("BASIX.txt");
        File dest = new File("jota.txt");
        try {
            Files.copy(src.toPath(), dest.toPath());
        } catch (IOException ex) {
            System.out.print("cant copy");
        }
    }

    
}
