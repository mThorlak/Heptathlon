package ui_package.admin_package;

import client_package.MainClientShop;
import client_package.MainClientSiege;
import ui_package.GeneralFrameSettings;

import javax.swing.*;

public class AddReference {
    private JPanel panelMain;
    private JTextField textFieldFamilyName;
    private JTextField textFieldReference;
    private JButton buttonValidate;
    private JLabel labelState;

    public AddReference(boolean requestFromShop) {
        JFrame addReferenceFrame = new JFrame("Admin page");
        GeneralFrameSettings generalFrameSettings = new GeneralFrameSettings(addReferenceFrame);
        addReferenceFrame.setContentPane(panelMain);
        addReferenceFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addReferenceFrame.setLocation(generalFrameSettings.getLocationX(), generalFrameSettings.getLocationY());

        buttonValidate.addActionListener(e -> {
            if (textFieldReference.getText().length() > 11)
                labelState.setText("Error : reference name is too long");
            else if (requestFromShop) {
                try {
                    MainClientShop clientShop = new MainClientShop();
                    clientShop.getQueryShopInterface().insertNewReference(
                            textFieldFamilyName.getText(),
                            textFieldReference.getText()
                    );
                    addReferenceFrame.dispose();
                } catch (Exception remoteException) {
                    remoteException.printStackTrace();
                    labelState.setText("Error : reference is existing already");
                }
            }
            else {
                try {
                    MainClientSiege clientSiege = new MainClientSiege();
                    clientSiege.getQuerySiegeInterface().insertNewReference(
                            textFieldFamilyName.getText(),
                            textFieldReference.getText()
                    );
                    addReferenceFrame.dispose();
                } catch (Exception remoteException) {
                    remoteException.printStackTrace();
                    labelState.setText("Error : reference is existing already");
                }
            }
        });

        addReferenceFrame.pack();
        addReferenceFrame.setVisible(true);
    }
}
