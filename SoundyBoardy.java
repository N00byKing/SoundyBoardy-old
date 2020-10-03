import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.sound.sampled.*;
import java.awt.event.*;
import java.io.File;

class SoundyBoardy extends JFrame implements ActionListener {
    JFileChooser soundChooser;
    JLabel[] filenames;
    Clip[] clip;

    public static void main(String[] args) {
        new SoundyBoardy("Soundy Boardy");  
    }

    SoundyBoardy(String name) {
        super("Soundy Boardy");
        JPanel panel = new JPanel();
        JButton[] setButtons = new JButton[4];
        JButton[] stopButtons = new JButton[4];
        JButton[] playButtons = new JButton[4];
        filenames = new JLabel[4];
        clip = new Clip[4];

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panel.setLayout(null);  
        this.setSize(800 , 310);
        this.add(panel);
        
        for (int i = 0; i < 4; i++) {
            setButtons[i] = new JButton("Select Sound " + (i+1));
            setButtons[i].addActionListener(this);
            panel.add(setButtons[i]);
            setButtons[i].setBounds(10, 10+i*70, 150, 50);
            setButtons[i].setActionCommand("SET_" + i);

            playButtons[i] = new JButton("Play " + (i+1));
            playButtons[i].addActionListener(this);
            panel.add(playButtons[i]);
            playButtons[i].setBounds(170, 10+i*70, 100, 50);
            playButtons[i].setActionCommand("PLY_" + i);

            stopButtons[i] = new JButton("Stop " + (i+1));
            stopButtons[i].addActionListener(this);
            panel.add(stopButtons[i]);
            stopButtons[i].setBounds(280, 10+i*70, 90, 50);
            stopButtons[i].setActionCommand("STP_" + i);

            filenames[i] = new JLabel("No Sound Selected");
            panel.add(filenames[i]);
            filenames[i].setBounds(390,30+i*70, 370, 15);

            try {
                clip[i] = AudioSystem.getClip();
            } catch (Exception e) {
    
            }
        }

        this.setVisible(true);

        soundChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("WAVE Files (.wav)", "wav");
        soundChooser.setFileFilter(filter);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int index = Integer.parseInt(e.getActionCommand().substring(4));
        switch (e.getActionCommand().substring(0, 3)) {
            case "SET":
                int returnVal = soundChooser.showOpenDialog(this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String filename = soundChooser.getSelectedFile().getName();
                    filenames[index].setText(filename);
                    selectSound(soundChooser.getSelectedFile(), index);
                }
                break;
            case "PLY":
                clip[index].setFramePosition(0);
                clip[index].start();
                break;
            case "STP":
                clip[index].setFramePosition(0);
                clip[index].stop();
                break;
        }
    }

    private void selectSound(File a, int index) {
        try {
            clip[index].close();
            clip[index].open(AudioSystem.getAudioInputStream(a));
        } catch (Exception e) {

        }
    }
}