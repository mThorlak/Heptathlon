package Ui_package;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Homepage {
    private JPanel MainPanel;
    private JButton EnterButton;

    public static void main(String[] args) {
        JFrame homepage = new JFrame("Homepage");
        homepage.setContentPane(new Homepage().MainPanel);
        homepage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        homepage.pack();
        homepage.setVisible(true);
    }

    public Homepage() {
        EnterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame controlPage = new JFrame("Control page");

            }
        });
    }
}
