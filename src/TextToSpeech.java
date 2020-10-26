///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
///**
// *
// * @author John
// */
import com.darkprograms.speech.synthesiser.SynthesiserV2;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.IOException;

public class TextToSpeech {
    private String text;
    SynthesiserV2 synthesizer=new SynthesiserV2("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");

    public void setText(String text) {
        this.text = text;
    }

    public void speak(){
        Thread thread = new Thread(() -> {

            try {
                AdvancedPlayer player = new AdvancedPlayer(synthesizer.getMP3Data(text));
                player.play();

            } catch (IOException | JavaLayerException e) {
                e.printStackTrace();
            }

        });
        thread.setDaemon(false);
        thread.start();
    }
}
