package ui_package.admin_package;

import client_package.MainClientShop;
import ui_package.GeneralFrameSettings;

import javax.swing.*;

public class ImportPriceShop {
    private JPanel panelMain;
    private JLabel labelHeader;
    private JTextField textFieldShop;
    private JLabel labelShopName;
    private JButton buttonValidate;
    private JLabel labelState;

    public ImportPriceShop() {
        JFrame importPriceFrame = new JFrame("Admin page");
        GeneralFrameSettings generalFrameSettings = new GeneralFrameSettings(importPriceFrame);
        importPriceFrame.setContentPane(panelMain);
        importPriceFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        importPriceFrame.setLocation(generalFrameSettings.getLocationX(), generalFrameSettings.getLocationY());

        buttonValidate.addActionListener(e -> {
            try {
                MainClientShop clientShop = new MainClientShop();
                clientShop.getQueryShopInterface().importPriceFromSiegeDB(textFieldShop.getText());
                importPriceFrame.dispose();
            } catch (Exception remoteException) {
                labelState.setText("This shop does not exist");
                remoteException.printStackTrace();
            }
        });

        importPriceFrame.pack();
        importPriceFrame.setVisible(true);
    }
}
