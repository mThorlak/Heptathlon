package ui_package.admin_package;

import client_package.ClientSiege;
import ui_package.ui_general.GeneralFrameSettings;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class InsertNewArticleSiege {
    private JPanel panelMain;
    private JTextField textFieldReference;
    private JButton buttonValidate;
    private JTextField textFieldPrice;
    private JTextField textFieldDescription;
    private JLabel labelDescription;
    private JLabel labelPrice;
    private JLabel labelReference;
    private JLabel labelState;
    private JLabel jLabelHeader;
    private JButton exitButton;

    public InsertNewArticleSiege() {
        JFrame addArticleFrame = new JFrame("Heptathlon");
        addArticleFrame.setContentPane(panelMain);
        addArticleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        labelState.setVisible(false);

        buttonValidate.addActionListener(e -> {
            try {
                ClientSiege clientSiege = new ClientSiege();
                clientSiege.getQuerySiegeInterface().insertNewArticle(
                        textFieldReference.getText(),
                        Double.parseDouble(textFieldPrice.getText()),
                        textFieldDescription.getText()
                );
                labelState.setText("Article is added with success !");
                labelState.setForeground(Color.GREEN);
                labelState.setVisible(true);
            } catch (Exception remoteException) {
                labelState.setText("Error : cannot add article");
                labelState.setForeground(Color.RED);
                labelState.setVisible(true);
                remoteException.printStackTrace();
            }
            addArticleFrame.pack();
        });

        exitButton.addActionListener(e -> {
            addArticleFrame.dispose();
        });

        addArticleFrame.pack();
        addArticleFrame.setVisible(true);
    }

    private void createUIComponents() throws IOException {
        BufferedImage myPicture = ImageIO.read(new File("UI/resources/adminPageHeader.png"));
        jLabelHeader = new JLabel(new ImageIcon(myPicture));
    }
}
