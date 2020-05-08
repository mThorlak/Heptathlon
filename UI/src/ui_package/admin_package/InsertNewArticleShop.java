package ui_package.admin_package;

import client_package.ClientShop;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class InsertNewArticleShop {
    private JTextField textFieldReference;
    private JButton buttonValidate;
    private JTextField textFieldPrice;
    private JTextField textFieldStock;
    private JTextField textFieldDescription;
    private JTextField textFieldShop;
    private JPanel panelMain;
    private JLabel labelState;
    private JLabel jLabelHeader;
    private JButton exitButton;

    public InsertNewArticleShop() {
        JFrame addArticleFrame = new JFrame("Heptathlon");
        addArticleFrame.setContentPane(panelMain);
        addArticleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        labelState.setVisible(false);

        buttonValidate.addActionListener(e -> {
            try {
                ClientShop clientShop = new ClientShop();
                if (textFieldShop.getText().isEmpty()) {
                    labelState.setText("Error :  cannot add article");
                    labelState.setForeground(Color.RED);
                }
                else {
                    clientShop.getQueryShopInterface().insertNewArticle(
                            textFieldReference.getText(),
                            Double.parseDouble(textFieldPrice.getText()),
                            Integer.parseInt(textFieldStock.getText()),
                            textFieldDescription.getText(),
                            textFieldShop.getText()
                    );
                    labelState.setText("Article added with success !");
                    labelState.setForeground(Color.GREEN);
                }
                labelState.setVisible(true);
            } catch (Exception remoteException) {
                labelState.setText("Error : cannot add article");
                labelState.setForeground(Color.RED);
                labelState.setVisible(true);
                remoteException.printStackTrace();
            }
            addArticleFrame.pack();

        });

        exitButton.addActionListener(e -> addArticleFrame.dispose());

        addArticleFrame.pack();
        addArticleFrame.setVisible(true);
    }

    private void createUIComponents() throws IOException {
        BufferedImage myPicture = ImageIO.read(new File("UI/resources/adminPageHeader.png"));
        jLabelHeader = new JLabel(new ImageIcon(myPicture));
    }
}
