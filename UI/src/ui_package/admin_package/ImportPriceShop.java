package ui_package.admin_package;

import client_package.ClientShop;
import ui_package.ui_general.GeneralFrameSettings;

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
                ClientShop clientShop = new ClientShop();
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
