package gui;

import javax.swing.*;
import java.awt.*;

public class LoadingScreen extends JFrame {

    public JProgressBar bar;

    public LoadingScreen() {
        setTitle("Loading...");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(null);
        setUndecorated(true);

        bar = new JProgressBar(0, 100);
        bar.setBounds(50, 80, 300, 30);
        bar.setStringPainted(true);

        JLabel label = new JLabel("Loading, please wait...");
        label.setBounds(120, 40, 200, 20);

        add(label);
        add(bar);
        setVisible(true);
    }
}
