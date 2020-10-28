/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedHashSet;
import java.util.Scanner;


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

    public int lookUpSE(String x, Dictionary dict) {
        for (int i = 0; i < dict.size(); i++) {
            if (dict.getWordTarget(i) == x)
                return i;
        }
        return -1;
    }

    public int lookUp(String x, Dictionary dict) {
        int l = 0, r = dict.size();
        while (l <= r) {
            int m = l + (r - l) / 2;

            int res = x.compareTo(dict.getWordTarget(m));

            if (res == 0)
                return m;

            if (res > 0)
                l = m + 1;

            else
                r = m - 1;
        }

        return -1;
    }

    public Dictionary insertFromFile(String a) {
        Dictionary dictionary = new Dictionary();
        String str;
        File dicTxt = new File(a);
        try {
            int i;
            Scanner scanner = new Scanner(dicTxt);
            while (scanner.hasNextLine()) {
                Word word = new Word();
                str = scanner.nextLine();
                i = str.indexOf("<html>");
                if (i == -1) {
                    continue;
                }
                word.setWord_target(str.substring(0, i));
                word.setWord_explain(str.substring(i));
                dictionary.pre(word);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            str = e.getLocalizedMessage();
        }
        if (a == "E_V.txt" || a == "BASIX.txt") {
            dictionary.Soort();
        }
        return dictionary;
    }

    public Word wordBuilder(String x, String y) {
        Word newWord = new Word();
        newWord.setWord_target(x);
        y = y.replaceAll("\n", "</b></font></li></ul><ul><li><font color='#cc0000'><b>");
        StringBuilder ex = new StringBuilder(y);
        ex.insert(0, "<html><i>" + x + "</i><br/><ul><li><font color='#cc0000'><b>");
        ex.append("</b></font></li></ul></html>");
        newWord.setWord_explain(ex.toString());
        return newWord;
    }

    public void fileRewriter(Dictionary dictionary, String a) {
        File old = new File(a);
        old.delete();
        File tmp = new File(a);
        try {
            tmp.createNewFile();
            FileWriter fw = new FileWriter(tmp, true);
            for (int i = 0; i < dictionary.size(); i++) {
                fw.write(dictionary.getWord(i).toString());
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(Word word, String a) {
        File in = new File(a);
        try {
            FileWriter fw = new FileWriter(in, true);
            fw.write(word.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void removeDuplicates(Dictionary dictionary) {
        LinkedHashSet<Word> hashSet = new LinkedHashSet<>(dictionary.wordList);
        dictionary.clear();
        dictionary.wordList.addAll(hashSet);
    }

    public void restore() {
        File src = new File("BASIX.txt");
        File old = new File("E_V.txt");
        old.delete();
        File dest = new File("E_V.txt");
        try {
//            dest.createNewFile();
            Files.copy(src.toPath(), dest.toPath());
        } catch (Exception ex) {
            System.out.print("cant copy");
        }
    }


}
