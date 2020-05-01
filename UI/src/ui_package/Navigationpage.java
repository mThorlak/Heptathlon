package ui_package;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Navigationpage {
    private JButton buttonGetterPage;
    private JButton buttonAdminPage;
    private JButton buttonBillePage;
    private JLabel labelNavigationBar;
    private JPanel panelMain;

    public Navigationpage() {

        JFrame navigationPage = new JFrame("Homepage");
        GeneralFrameSettings generalFrameSettings = new GeneralFrameSettings(navigationPage);
        navigationPage.setContentPane(panelMain);
        navigationPage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        navigationPage.setSize(300, 200);
        navigationPage.setLocation(generalFrameSettings.getLocationX(), generalFrameSettings.getLocationY());
        navigationPage.setVisible(true);

        buttonGetterPage.addActionListener(e -> {
            navigationPage.dispose();
            try {
                new Getterpage();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        buttonBillePage.addActionListener(e -> {
            navigationPage.dispose();
            try {
                new Billpage();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        buttonAdminPage.addActionListener(e -> {
            navigationPage.dispose();
            try {
                new Adminpage();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }
}
