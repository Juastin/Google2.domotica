package src.views;
import src.core.*;
import src.components.*;
import src.system.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class ProfileView extends SubPanel implements ActionListener {
    private JButton jbNewProfile;
    private JLabel jlKies, jlProfileAmount;
    private ArrayList<CButton> userButtons;
    private JPanel center, userGrid;
    private ArrayList<ArrayList<String>> users;

    public ProfileView(MainPanel parent, String panel_name) {
        super(parent, panel_name);
        setLayout(new BorderLayout());
        setVisible(false);

        // TOP
        JPanel top = new JPanel();
        top.setBorder(BorderFactory.createEmptyBorder(40, 0, 10, 0));
        jlKies = new JLabel("Kies uw profiel:");
        jlKies.setFont(jlKies.getFont().deriveFont(24.0f));
        jbNewProfile = new CButton(this, "＋", Color.black, Color.white);
        jbNewProfile.setFont(new Font(jbNewProfile.getFont().getFamily(), Font.PLAIN, 24));
        top.add(jlKies);
        add(top, BorderLayout.NORTH);

        // CENTER
        center = new JPanel();
        userGrid = new JPanel();
        center.add(userGrid);
        add(center, BorderLayout.CENTER);

        // BOTTOM
        JPanel bottom = new JPanel();
        bottom.setLayout(new BorderLayout());
        bottom.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JPanel nieuwProfielButtonPanel = new JPanel();
        nieuwProfielButtonPanel.add(jbNewProfile);
        bottom.add(nieuwProfielButtonPanel, BorderLayout.NORTH);

        JPanel nieuwProfielLabelPanel = new JPanel();
        jlProfileAmount = new JLabel();
        nieuwProfielLabelPanel.add(jlProfileAmount, BorderLayout.SOUTH);
        bottom.add(nieuwProfielLabelPanel, BorderLayout.SOUTH);

        add(bottom, BorderLayout.SOUTH);
    }

    public void updateProfileList() {
        // GET PROFILES FROM DB
        users = Queries.getProfiles();

        userGrid.setPreferredSize(new Dimension(600, 20*users.size()));
        GridLayout userGridLayout = new GridLayout((users.size()/3), 3);
        userGridLayout.setHgap(10); userGridLayout.setVgap(10);
        userGrid.setLayout(userGridLayout);

        userGrid.removeAll();
        userButtons = new ArrayList<CButton>();

        for (ArrayList<String> row: users) {
            CButton button = new CButton(this, row.get(0), Color.black, Color.white);
            button.setFont(new Font(button.getFont().getFamily(), Font.PLAIN, 16));
            userGrid.add(button);
            userButtons.add(button);
        }

        jlProfileAmount.setText("Profiel toevoegen (" + users.size() + "/15)");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbNewProfile) {
            changeFocus("MakeProfileView");
        }

        for (CButton button: userButtons) {
            if (e.getSource() == button) {
                User.setUsername(button.getText());
                changeFocus("LoginView");
            }
        }

        Audio.play("click.wav");
    }

    @Override
    public void onFocus() {
        updateProfileList();
    }

}
