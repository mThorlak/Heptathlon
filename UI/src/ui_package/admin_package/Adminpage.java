package ui_package.admin_package;

import ui_package.ui_general.GeneralFrameSettings;
import ui_package.ui_general.NavigationBar;

import javax.swing.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Adminpage {
    private JPanel panelMain;
    private JLabel labelHeader;
    private JButton buttonBillPage;
    private JButton buttonGetterPage;
    private JButton buttonAdminPage;
    private JButton buttonAddArticleShop;
    private JButton buttonAddArticleSiege;
    private JButton buttonUpdatePriceShop;
    private JButton buttonImportBillSiege;
    private JButton buttonAddReferenceShop;
    private JButton buttonAddReferenceSiege;
    private JButton buttonAddStockArticle;

    public Adminpage() throws RemoteException, NotBoundException {
        JFrame adminPage = new JFrame("Heptathlon");
        GeneralFrameSettings generalFrameSettings = new GeneralFrameSettings(adminPage);
        adminPage.setContentPane(panelMain);
        adminPage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        adminPage.setLocation(generalFrameSettings.getLocationX(), generalFrameSettings.getLocationY());

        // Navigation bar
        new NavigationBar(adminPage, buttonGetterPage, buttonBillPage, buttonAdminPage);

        buttonAddReferenceShop.addActionListener(e -> {
            new AddReference(true);
        });

        buttonAddArticleShop.addActionListener(e -> {
            new InsertNewArticleShop();
        });

        buttonUpdatePriceShop.addActionListener(e -> {
            new ImportPriceShop();
        });

        buttonAddReferenceSiege.addActionListener(e -> {
            new AddReference(false);
        });

        buttonAddArticleSiege.addActionListener(e -> {
            new InsertNewArticleSiege();
        });

        buttonImportBillSiege.addActionListener(e -> {
            new ImportBill();
        });

        buttonAddStockArticle.addActionListener(e -> {
            new AddStockArticle();
        });

        adminPage.pack();
        adminPage.setVisible(true);

    }
}
