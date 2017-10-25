import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    static Thread th = null;
    static int instrument = 0, ticks = 0;
    static long temp = 0;
    static JTextField instrField = new JTextField("1", 3),
            ticksField = new JTextField("200", 5),
            tempField = new JTextField("3", 2);
    static Button button = new Button("Старт"), stop = new Button("Стоп");
    static JSlider jSliderMin = new JSlider(0,126),jSliderMax=new JSlider(0,126);

    public static void main(String[] args) {
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                if (a.getSource().equals(stop)) {
                    Player.finish();
                }
            }
        });
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                if (a.getSource().equals(button)) {
                    instrument = Integer.parseInt(instrField.getText());
                    ticks = Integer.parseInt(ticksField.getText());
                    temp = Long.parseLong(tempField.getText());
                    th = new Thread(new Player(instrument, ticks, temp,Math.min(jSliderMin.getValue(),jSliderMax.getValue()),Math.max(jSliderMin.getValue(),jSliderMax.getValue())));
                    th.start();
                }
            }
        });
        JFrame jF = new JFrame("MIDI test");
        jF.setResizable(false);
        JPanel jp = new JPanel();
        jp.setLayout(new GridLayout(3,1));
        JPanel jf = new JPanel();
        jF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.add(new JLabel("Инструмент"));
        jf.add(instrField);
        jf.add(new JLabel("Длина"));
        jf.add(ticksField);
        jf.add(new JLabel("Такт"));
        jf.add(tempField);
        jf.add(button);
        jf.add(stop);
        jp.add(jf);
        JPanel minJP=new JPanel(),maxJP=new JPanel();
        minJP.add(new JLabel("Нижняя нота"));
        minJP.add(new JLabel("0"));
        minJP.add(jSliderMin);
        minJP.add(new JLabel("127"));
        jp.add(minJP);
        maxJP.add(new JLabel("Верхняя нота"));
        maxJP.add(new JLabel("0"));
        maxJP.add(jSliderMax);
        maxJP.add(new JLabel("127"));
        jp.add(maxJP);
        jF.add(jp);
        jF.pack();
        jF.setVisible(true);
    }
}