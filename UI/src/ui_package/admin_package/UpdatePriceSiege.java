package ui_package.admin_package;

import client_package.ClientSiege;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UpdatePriceSiege {
    private JPanel panelMain;
    private JTextField textFieldReference;
    private JButton buttonValidate;
    private JTextField textFieldPrice;
    private JLabel labelState;
    private JLabel jLabelHeader;
    private JButton exitButton;

    public UpdatePriceSiege() {
        JFrame updateArticleFrame = new JFrame("Heptathlon");
        updateArticleFrame.setContentPane(panelMain);
        updateArticleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        labelState.setVisible(false);

        buttonValidate.addActionListener(e -> {
            try {
                ClientSiege clientSiege = new ClientSiege();
                clientSiege.getQuerySiegeInterface().updatePrice(
                        textFieldReference.getText(),
                        Double.parseDouble(textFieldPrice.getText())
                );
                labelState.setText("Article price is updated with success !");
                labelState.setForeground(Color.GREEN);
                labelState.setVisible(true);
            } catch (Exception remoteException) {
                labelState.setText("Error : cannot update price of this article");
                labelState.setForeground(Color.RED);
                labelState.setVisible(true);
                remoteException.printStackTrace();
            }
            updateArticleFrame.pack();
        });

        exitButton.addActionListener(e -> updateArticleFrame.dispose());

        updateArticleFrame.pack();
        updateArticleFrame.setVisible(true);
    }

    private void createUIComponents() throws IOException {
        BufferedImage myPicture = ImageIO.read(new File("UI/resources/adminPageHeader.png"));
        jLabelHeader = new JLabel(new ImageIcon(myPicture));
    }
}
