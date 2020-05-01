package ui_package;

import javax.swing.*;

public class Homepage {
    private JPanel MainPanel;
    private JButton EnterButton;

    public static void main(String[] args) {
        new Homepage();
    }

    public Homepage() {
        JFrame homepage = new JFrame("Homepage");
        GeneralFrameSettings generalFrameSettings = new GeneralFrameSettings(homepage);
        homepage.setContentPane(MainPanel);
        homepage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        homepage.setSize(300, 200);
        homepage.setLocation(generalFrameSettings.getLocationX(), generalFrameSettings.getLocationY());
        homepage.setVisible(true);

        EnterButton.addActionListener(e -> {
            try {
                homepage.dispose();
                new Getterpage();
            } catch (Exception remoteException) {
                remoteException.printStackTrace();
            }
        });
    }
}
