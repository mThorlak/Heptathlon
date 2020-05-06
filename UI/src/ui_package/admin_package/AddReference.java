package ui_package.admin_package;

import client_package.ClientShop;
import client_package.ClientSiege;
import ui_package.ui_general.GeneralFrameSettings;

import javax.swing.*;

public class AddReference {
    private JPanel panelMain;
    private JTextField textFieldFamilyName;
    private JTextField textFieldReference;
    private JButton buttonValidate;
    private JLabel labelState;
    private JLabel labelHeader;
    private JLabel labelFamilyName;
    private JLabel labelReference;

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
                    ClientShop clientShop = new ClientShop();
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
                    ClientSiege clientSiege = new ClientSiege();
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
