package src.views.musicMenuPanels;

import src.components.SongsTableCellRenderer;
import src.components.SongsTableLayout;
import src.components.SongsTableModel;
import src.core.Audio;
import src.system.Queries;
import src.system.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MusicMenuPlaylist extends JPanel implements ActionListener{
    private JTable jtSongs;
    private JPanel top, center;
    private JLabel jlTitel;
    private ArrayList<ArrayList<String>> playlistSongsList, playlistData;
    private SongsTableCellRenderer songTableCell;
    private JScrollPane scroll;
    private JComboBox comboList;

    public MusicMenuPlaylist(int id, String name) {
        setVisible(false);
        setLayout(new BorderLayout());

        playlistSongsList = Queries.getPlaylistSongsList(id);
        playlistData = Queries.getPlaylistData(User.getUsername());

        // Test scroller
        /*
        ArrayList<String> test = new ArrayList<>();
        test.add("4");
        test.add("null");
        test.add("test");
        test.add("145");
        ArrayList<String> test2 = new ArrayList<>();
        test2.add("1");
        test2.add("null");
        test2.add("liedje");
        test2.add("60");
        playlistSongsList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            if (i % 2 == 0) {
                playlistSongsList.add(test);
            } else {
                playlistSongsList.add(test2);
            }
        }
        */

        // Top of panel Title
        top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jlTitel = new JLabel("Naam afspeellijst: " + name);
        jlTitel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jlTitel.setFont(jlTitel.getFont().deriveFont(20.0f));
        top.add(jlTitel);

        if (playlistData != null) {
            comboList = new JComboBox<String>();
            for (ArrayList<String> playlistName : playlistData) {
                comboList.addItem(playlistName.get(1));
            }
            comboList.addActionListener(this);
            comboList.setEditable(false);
            top.add(comboList, BorderLayout.NORTH);
        }

        // Center panel table
        center = new JPanel();
        center.setLayout(new BorderLayout());

        // Table songs
        if (playlistSongsList != null) {
            songTableCell = new SongsTableCellRenderer();
            jtSongs = new SongsTableLayout(new SongsTableModel(playlistSongsList), songTableCell);
            center.add(jtSongs.getTableHeader(), BorderLayout.NORTH);
            center.add(jtSongs, BorderLayout.CENTER);
        }

        // Add
        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        /* Scroller */
        scroll = new JScrollPane(jtSongs);
//        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setViewportBorder(BorderFactory.createEmptyBorder());
        center.add(scroll);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == comboList) {
            String option = (String) comboList.getSelectedItem();
            jlTitel.setText("Naam afspeellijst: " + option);
            playlistSongsList = Queries.getPlaylistSongsList(option);
            System.out.println(playlistSongsList);
        }
    }

}