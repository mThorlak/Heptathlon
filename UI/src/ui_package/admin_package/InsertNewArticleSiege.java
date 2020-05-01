package ui_package.admin_package;

import client_package.MainClientSiege;
import ui_package.GeneralFrameSettings;

import javax.swing.*;

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

    public InsertNewArticleSiege() {
        JFrame addArticleFrame = new JFrame("Admin page");
        GeneralFrameSettings generalFrameSettings = new GeneralFrameSettings(addArticleFrame);
        addArticleFrame.setContentPane(panelMain);
        addArticleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addArticleFrame.setLocation(generalFrameSettings.getLocationX(), generalFrameSettings.getLocationY());

        buttonValidate.addActionListener(e -> {
            try {
                MainClientSiege clientSiege = new MainClientSiege();
                clientSiege.getQuerySiegeInterface().insertNewArticle(
                        textFieldReference.getText(),
                        Double.parseDouble(textFieldPrice.getText()),
                        textFieldDescription.getText()
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
