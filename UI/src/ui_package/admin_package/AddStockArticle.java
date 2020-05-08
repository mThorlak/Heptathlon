package ui_package.admin_package;

import client_package.ClientShop;
import client_package.ClientSiege;
import rmi_shop.tables.Article;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class AddStockArticle {
    private JPanel panelMain;
    private JTextField textFieldShop;
    private JTextField textFieldReference;
    private JTextField textFieldQuantity;
    private JButton buttonCancel;
    private JButton buttonConfirm;
    private JLabel jLabelHeader;
    private JLabel labelState;

    public AddStockArticle() throws RemoteException, NotBoundException {

        JFrame addStockFrame = new JFrame("Heptathlon");
        addStockFrame.setContentPane(panelMain);
        addStockFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ClientSiege clientSiege = new ClientSiege();

        labelState.setVisible(false);

        buttonCancel.addActionListener(e -> {
            addStockFrame.dispose();
        });

        buttonConfirm.addActionListener(e -> {
            try {
                if (!textFieldShop.getText().isEmpty() && !textFieldQuantity.getText().isEmpty() && !textFieldReference.getText().isEmpty()) {
                    List<String> shops =  clientSiege.getQuerySiegeInterface().getAllShop();
                    boolean shopExist = false;
                    for (String shop : shops) {
                        if (shop.equals(textFieldShop.getText())) {
                            shopExist = true;
                            break;
                        }
                    }

                    if (shopExist) {
                        ClientShop clientShop = new ClientShop();
                        Article article = clientShop.getQueryShopInterface().getArticleByReference(textFieldReference.getText());
                        clientShop.getQueryShopInterface().updateStock(
                                textFieldShop.getText(),
                                textFieldReference.getText(),
                                article.getStock() + Integer.parseInt(textFieldQuantity.getText())
                        );
                        labelState.setText("Stock updated with success !");
                        labelState.setForeground(Color.GREEN);
                    }
                    else {
                        labelState.setText("This shop does not exist");
                        labelState.setForeground(Color.RED);
                    }
                }
                else {
                    labelState.setText("Error : please check the information given");
                    labelState.setForeground(Color.RED);
                }
                labelState.setVisible(true);
            } catch (Exception remoteException) {
                labelState.setText("Error : cannot update article given");
                labelState.setForeground(Color.RED);
                labelState.setVisible(true);
                remoteException.printStackTrace();
            }
            addStockFrame.pack();
        });

        buttonCancel.addActionListener(e -> addStockFrame.dispose());

        addStockFrame.pack();
        addStockFrame.setVisible(true);
    }

    private void createUIComponents() throws IOException {
        BufferedImage myPicture = ImageIO.read(new File("UI/resources/adminPageHeader.png"));
        jLabelHeader = new JLabel(new ImageIcon(myPicture));
    }
}
