package src.components;
import java.lang.*;

import src.core.Arduino;

import java.io.IOException;


public class PlayMusic {
    private Arduino ar = new Arduino();
    private Integer note;
    private int divider =0, noteDuration =0;
    private int tempo=40;
    private int wholenote = (6000*4)/tempo;
    private int thisNote = 0;
    private long lasttime =0;
    private int totalnotes=0;

    public PlayMusic(){}



    public void sendnotes(Integer[] melody) throws InterruptedException, IOException {
        int notes = melody.length;
        totalnotes = notes;

        if(thisNote < notes * 2&&System.currentTimeMillis()- lasttime >noteDuration) {
            thisNote = thisNote + 2;
//            System.out.println(thisNote);
            // calculates the duration of each note
            try {
                divider = melody[thisNote + 1];
            } catch (ArrayIndexOutOfBoundsException e) {
                divider = 0;
            }
            if (divider > 0) {
                // regular note, just proceed
                noteDuration = (wholenote) / divider;
            } else if (divider < 0) {
                // dotted notes are represented with negative durations!!
                noteDuration = (wholenote) / Math.abs(divider);
                noteDuration *= 1.5; // increases the duration in half for dotted notes
            }
            // we only play the note for 90% of the duration, leaving 10% as a pause
            //========tone(buzzer, melody[thisNote], noteDuration*0.9);==========
            Integer notenduratie = noteDuration;
            try {
                note = melody[thisNote];
            } catch (ArrayIndexOutOfBoundsException e) {
                note = 0;
                thisNote = 0;
            }
            ar.getoutputstream(note);
            // Wait for the specief duration before playing the next note.
            // stop the waveform generation before the next note.
            lasttime = System.currentTimeMillis();
        }
    }
    public void pause() throws IOException {
        ar.getoutputstream(0);
    }

    public int getLengthNotes(){
        return totalnotes;
    }

    public int getThisNote() {
        return thisNote;
    }
}
