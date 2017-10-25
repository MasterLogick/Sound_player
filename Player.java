import javax.sound.midi.*;

public class Player implements Runnable {
    int instrument,ticks,min,max;
    long temp;
    static Sequencer sequencer=null;
    public Player(int instrument,int ticks,long temp,int min,int max){
        this.instrument=instrument;
        this.temp=temp;
        this.ticks=ticks;
        this.min = min;
        this.max = max;
    }
    @Override
    public void run() {
        try {
            sequencer = MidiSystem.getSequencer();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
        Sequence sequence = null;
        try {
            sequence = new Sequence(Sequence.PPQ,5);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        Track track = sequence.createTrack();
        try {
            track.add(new MidiEvent(new ShortMessage(ShortMessage.PROGRAM_CHANGE,instrument,0),0));
            for (int i = 0; i < ticks; i++) {
                track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, min + i % (max - min), 60), temp * i));
            }
            sequencer.open();
            sequencer.setSequence(sequence);
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        sequencer.start();
    }
    public static void finish(){
        sequencer.stop();
        sequencer.close();
    }
}
