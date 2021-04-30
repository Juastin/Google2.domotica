package src.core;

import src.components.CButton;
import src.system.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Navbar extends JPanel implements ActionListener {
    private View parent;
    private JButton jbLogOut, jbHome, jbMusic, jbSettings;
    private JLabel white, white2;

    public Navbar(View parent) {
        this.parent = parent;
        setLayout(new BorderLayout());

        // Log out button must be fixed
        jbLogOut = new CButton(this, "⊏→", Color.black, Color.white);
        jbHome = new CButton(this, "⌂", Color.black, Color.white);
        jbMusic = new CButton(this, "♫", Color.black, Color.white);
        jbSettings = new CButton(this, "⛭", Color.black, Color.white);

        jbLogOut.setFont(new Font(jbLogOut.getFont().getFamily(), Font.PLAIN, 40));
        jbHome.setFont(new Font(jbHome.getFont().getFamily(), Font.PLAIN, 40));
        jbMusic.setFont(new Font(jbMusic.getFont().getFamily(), Font.PLAIN, 40));
        jbSettings.setFont(new Font(jbSettings.getFont().getFamily(), Font.PLAIN, 40));

        // Looking how to make empty space in GridLayout
        white = new JLabel(".");
        white.setForeground(new Color(255, 255, 255));
        white2 = new JLabel(".");
        white2.setForeground(new Color(255, 255, 255));

        JPanel menuBar = new JPanel();
        menuBar.setLayout(new BorderLayout(0, 0));

        JPanel formGrid = new JPanel();
        GridLayout formGridLayout = new GridLayout(6, 1);
        formGridLayout.setHgap(0);
        formGridLayout.setVgap(0);
        formGrid.setLayout(formGridLayout);
        formGrid.setBackground(new Color(255, 255, 255));
        formGrid.setPreferredSize(new Dimension(75, 500));

        formGrid.add(jbLogOut);
        formGrid.add(jbHome);
        formGrid.add(jbMusic);
        formGrid.add(white);
        formGrid.add(white2);
        formGrid.add(jbSettings);

        menuBar.add(formGrid);
        add(menuBar, BorderLayout.EAST);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbLogOut) {
            Audio.play("success_1.wav");
            User.logOut();
            parent.changeFocus("ProfileView");
            JOptionPane.showMessageDialog(this, "U bent afgemeld");
        } else if (e.getSource() == jbHome) {
            parent.changeFocus("MainScreenView");
        } else if (e.getSource() == jbMusic) {
            parent.changeFocus("MusicPlayerView");
        } else if (e.getSource() == jbSettings) {
            parent.changeFocus("PersonalSettingsView");
        }
        Audio.play("click.wav");
    }

}