package ui_package.admin_package;

import client_package.ClientShop;
import rmi_shop.tables.Article;
import ui_package.ui_general.GeneralFrameSettings;

import javax.swing.*;

public class AddStockArticle {
    private JPanel panelMain;
    private JTextField textFieldShop;
    private JTextField textFieldReference;
    private JTextField textFieldQuantity;
    private JButton buttonCancel;
    private JButton buttonConfirm;

    public AddStockArticle() {

        JFrame addStockFrame = new JFrame("Heptathlon");
        GeneralFrameSettings generalFrameSettings = new GeneralFrameSettings(addStockFrame);
        addStockFrame.setContentPane(panelMain);
        addStockFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addStockFrame.setLocation(generalFrameSettings.getLocationX(), generalFrameSettings.getLocationY());

        buttonCancel.addActionListener(e -> {
            addStockFrame.dispose();
        });

        buttonConfirm.addActionListener(e -> {
            try {
                if (!textFieldShop.getText().isEmpty() && !textFieldQuantity.getText().isEmpty() && !textFieldReference.getText().isEmpty()) {
                    ClientShop clientShop = new ClientShop();
                    Article article = clientShop.getQueryShopInterface().getArticleByReference(textFieldReference.getText());
                    clientShop.getQueryShopInterface().updateStock(
                            textFieldShop.getText(),
                            textFieldReference.getText(),
                            article.getStock() + Integer.parseInt(textFieldQuantity.getText())
                    );
                }
            } catch (Exception remoteException) {
                remoteException.printStackTrace();
            }
        });

        addStockFrame.pack();
        addStockFrame.setVisible(true);

    }
}
