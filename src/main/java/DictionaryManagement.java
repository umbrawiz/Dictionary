/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ACER
 */
import java.io.File;
import java.io.FileNotFoundException;
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


    Dictionary insertFromFile() {
        Dictionary dictionary = new Dictionary();
        String str;
        String tab = "\t";
        File dicTxt = new File("Dic.txt");
        try {
            Scanner scanner = new Scanner(dicTxt);
            while (scanner.hasNextLine()) {
                Word word = new Word();
                str = scanner.nextLine();
                word.setWord_target(str.substring(0, str.indexOf(tab)));
                word.setWord_explain(str.substring(str.indexOf(tab) + 1));
                dictionary.add(word);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            str = e.getLocalizedMessage();
        }
        dictionary.Soort();
        return dictionary;
    }
}
