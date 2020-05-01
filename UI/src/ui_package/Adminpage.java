package ui_package;

import client_package.MainClientShop;
import client_package.MainClientSiege;

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
    private JButton buttonCalculCAShop;
    private JButton buttonAddShopSiege;
    private JButton buttonAddReferenceShop;
    private JButton buttonAddReferenceSiege;

    public Adminpage() throws RemoteException, NotBoundException {
        JFrame controlFrame = new JFrame("Admin page");
        GeneralFrameSettings generalFrameSettings = new GeneralFrameSettings(controlFrame);
        controlFrame.setContentPane(panelMain);
        controlFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        controlFrame.setLocation(generalFrameSettings.getLocationX(), generalFrameSettings.getLocationY());

        // Navigation bar
        new NavigationBar(controlFrame, buttonGetterPage, buttonBillPage, buttonAdminPage);

        MainClientShop clientShop = new MainClientShop();
        MainClientSiege clientSiege = new MainClientSiege();

        controlFrame.pack();
        controlFrame.setVisible(true);


        buttonAddReferenceShop.addActionListener(e -> {

        });

        buttonAddArticleShop.addActionListener(e -> {

        });

        buttonUpdatePriceShop.addActionListener(e -> {

        });

        buttonCalculCAShop.addActionListener(e -> {

        });


        buttonAddReferenceSiege.addActionListener(e -> {

        });

        buttonAddArticleSiege.addActionListener(e -> {

        });

        buttonAddShopSiege.addActionListener(e -> {

        });

        buttonImportBillSiege.addActionListener(e -> {

        });
    }
}
