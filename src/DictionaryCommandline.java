import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author ACER
 */
public class DictionaryCommandline {

    void showAllWords(Dictionary dictionary) {
        System.out.println("No" + "\t" + "| English" + "\t" + "| Vietnamese");
        for (int i = 0; i < dictionary.wordList.size(); i++) {
            System.out.println(((i + 1)) + "\t" + "| " + dictionary.getWordTarget(i) + "\t" + "| " + dictionary.getWordExplain(i));
        }
    }

    public void clearTheFile(String filepath) throws IOException {
        FileWriter fr = new FileWriter(filepath, false);
        PrintWriter pw = new PrintWriter(fr, false);
        pw.flush();
        pw.close();
        fr.close();
    }
}
