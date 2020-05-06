package ui_package.admin_package;

import client_package.ClientShop;
import ui_package.ui_general.GeneralFrameSettings;

import javax.swing.*;

public class InsertNewArticleShop {
    private JTextField textFieldReference;
    private JButton buttonValidate;
    private JLabel labelHeader;
    private JLabel labelReference;
    private JTextField textFieldPrice;
    private JLabel labelPrice;
    private JTextField textFieldStock;
    private JLabel labelStock;
    private JTextField textFieldDescription;
    private JTextField textFieldShop;
    private JLabel labelDescription;
    private JLabel labelShop;
    private JPanel panelMain;
    private JLabel labelState;

    public InsertNewArticleShop() {
        JFrame addArticleFrame = new JFrame("Admin page");
        GeneralFrameSettings generalFrameSettings = new GeneralFrameSettings(addArticleFrame);
        addArticleFrame.setContentPane(panelMain);
        addArticleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addArticleFrame.setLocation(generalFrameSettings.getLocationX(), generalFrameSettings.getLocationY());

        buttonValidate.addActionListener(e -> {
            try {
                ClientShop clientShop = new ClientShop();
                clientShop.getQueryShopInterface().insertNewArticle(
                        textFieldReference.getText(),
                        Double.parseDouble(textFieldPrice.getText()),
                        Integer.parseInt(textFieldStock.getText()),
                        textFieldDescription.getText(),
                        textFieldShop.getText()
                );
                addArticleFrame.dispose();
            } catch (Exception remoteException) {
                labelState.setText("Error : article is existing already or is not referenced");
                remoteException.printStackTrace();
            }

        });

        addArticleFrame.pack();
        addArticleFrame.setVisible(true);
    }
}
