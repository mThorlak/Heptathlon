package ui_package.admin_package;

import client_package.ClientShop;
import client_package.ClientSiege;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AddReference {
    private JPanel panelMain;
    private JTextField textFieldFamilyName;
    private JTextField textFieldReference;
    private JButton buttonValidate;
    private JLabel labelState;
    private JButton exitButton;
    private JLabel jLabelHeader;

    public AddReference(boolean requestFromShop) {
        JFrame addReferenceFrame = new JFrame("Heptathlon");
        addReferenceFrame.setContentPane(panelMain);
        addReferenceFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        labelState.setVisible(false);

        buttonValidate.addActionListener(e -> {
            if (textFieldReference.getText().length() > 11) {
                labelState.setVisible(true);
                labelState.setText("Error : reference name is too long");
                labelState.setForeground(Color.RED);
            }
            else if (requestFromShop) {
                try {
                    ClientShop clientShop = new ClientShop();
                    clientShop.getQueryShopInterface().insertNewReference(
                            textFieldFamilyName.getText(),
                            textFieldReference.getText()
                    );
                    labelState.setVisible(true);
                    labelState.setText("Reference added with success !");
                    labelState.setForeground(Color.GREEN);
                } catch (Exception remoteException) {
                    remoteException.printStackTrace();
                    labelState.setVisible(true);
                    labelState.setText("Error : cannot add reference");
                    labelState.setForeground(Color.RED);
                }
            }
            else {
                try {
                    ClientSiege clientSiege = new ClientSiege();
                    clientSiege.getQuerySiegeInterface().insertNewReference(
                            textFieldFamilyName.getText(),
                            textFieldReference.getText()
                    );
                    labelState.setText("Reference added with success !");
                    labelState.setForeground(Color.GREEN);
                } catch (Exception remoteException) {
                    remoteException.printStackTrace();
                    labelState.setVisible(true);
                    labelState.setText("Error : cannot add reference");
                    labelState.setForeground(Color.RED);
                }
            }
            addReferenceFrame.pack();
        });

        exitButton.addActionListener(e -> addReferenceFrame.dispose());

        addReferenceFrame.pack();
        addReferenceFrame.setVisible(true);

    }

    private void createUIComponents() throws IOException {
        BufferedImage myPicture = ImageIO.read(new File("UI/resources/adminPageHeader.png"));
        jLabelHeader = new JLabel(new ImageIcon(myPicture));
    }
}
