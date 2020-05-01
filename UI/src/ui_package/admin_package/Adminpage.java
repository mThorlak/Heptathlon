package ui_package.admin_package;

import client_package.MainClientShop;
import client_package.MainClientSiege;
import ui_package.GeneralFrameSettings;
import ui_package.NavigationBar;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JButton buttonCalculateCAShop;
    private JButton buttonAddShopSiege;
    private JButton buttonAddReferenceShop;
    private JButton buttonAddReferenceSiege;

    public Adminpage() throws RemoteException, NotBoundException {
        JFrame adminPage = new JFrame("Admin page");
        GeneralFrameSettings generalFrameSettings = new GeneralFrameSettings(adminPage);
        adminPage.setContentPane(panelMain);
        adminPage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        adminPage.setLocation(generalFrameSettings.getLocationX(), generalFrameSettings.getLocationY());

        // Navigation bar
        new NavigationBar(adminPage, buttonGetterPage, buttonBillPage, buttonAdminPage);

        MainClientShop clientShop = new MainClientShop();
        MainClientSiege clientSiege = new MainClientSiege();

        adminPage.pack();
        adminPage.setVisible(true);


        buttonAddReferenceShop.addActionListener(e -> {
            new AddReference(true);
        });

        buttonAddArticleShop.addActionListener(e -> {
            new InsertNewArticleShop();
        });

        buttonUpdatePriceShop.addActionListener(e -> {

        });

        buttonCalculateCAShop.addActionListener(e -> {

        });


        buttonAddReferenceSiege.addActionListener(e -> {
            new AddReference(false);
        });

        buttonAddArticleSiege.addActionListener(e -> {
            new InsertNewArticleSiege();
        });

        buttonAddShopSiege.addActionListener(e -> {

        });

        buttonImportBillSiege.addActionListener(e -> {

        });
    }
}
