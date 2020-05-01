package ui_package;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import client_package.MainClientShop;
import client_package.MainClientSiege;
import model_table.TableArticleShop;
import model_table.TableArticleSiege;
import rmi_shop.tables.Article;

public class Getterpage extends Container {

    private JPanel panelMain;
    private JLabel labelHeader;
    private JPanel panelControl;
    private JPanel panelShop;
    private JPanel PanelSiege;
    private JButton buttonGetAllArticleShop;
    private JScrollPane JScrollContentPane;
    private JTable tableDisplay;
    private JLabel labelShop;
    private JLabel labelSiege;
    private JButton buttonGetAllArticleSiege;
    private JTextField fieldGetArticleByReferenceShop;
    private JButton buttonGetArticleByReferenceShop;
    private JTextField fieldGetArticleByFamilyShop;
    private JButton buttonGetArticleByFamilyShop;
    private JTextField fieldGetArticleByFamilySiege;
    private JTextField fieldGetArticleByShopSiege;
    private JButton buttonGetArticleByFamilySiege;
    private JButton buttonGetArticleByShopSiege;
    private JButton buttonBillPage;
    private JButton buttonGetterPage;
    private JButton buttonAdminPage;


    public Getterpage() throws Exception {

        // Frame settings
        JFrame controlFrame = new JFrame("Control page");
        GeneralFrameSettings generalFrameSettings = new GeneralFrameSettings(controlFrame);
        controlFrame.setContentPane(panelMain);
        controlFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        controlFrame.setLocation(generalFrameSettings.getLocationX(), generalFrameSettings.getLocationY());

        // Navigation bar
        new NavigationBar(controlFrame, buttonGetterPage, buttonBillPage, buttonAdminPage);

        MainClientShop clientShop = new MainClientShop();
        MainClientSiege clientSiege = new MainClientSiege();

        buttonGetAllArticleShop.addActionListener(e -> {
            try {
                TableArticleShop modelTable = new TableArticleShop(clientShop.getQueryShopInterface().getAllArticle());
                tableDisplay = new JTable(modelTable);
                JScrollContentPane.setViewportView(tableDisplay);
                JScrollContentPane.setSize(tableDisplay.getSize());
                controlFrame.pack();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        buttonGetArticleByReferenceShop.addActionListener(e -> {
            TableArticleShop modelTable;
            List<Article> listArticles = new ArrayList<>();
            try {
                listArticles.add(clientShop.getQueryShopInterface().findArticleByReference(fieldGetArticleByReferenceShop.getText()));
                modelTable = new TableArticleShop(listArticles);
                tableDisplay = new JTable(modelTable);
                JScrollContentPane.setViewportView(tableDisplay);
                JScrollContentPane.setSize(tableDisplay.getSize());
                controlFrame.pack();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        buttonGetArticleByFamilyShop.addActionListener(e -> {
            try {
                TableArticleShop tableArticleShop = new TableArticleShop(
                        clientShop.getQueryShopInterface().getArticleByFamily(fieldGetArticleByFamilyShop.getText()));
                tableDisplay = new JTable(tableArticleShop);
                JScrollContentPane.setViewportView(tableDisplay);
                JScrollContentPane.setSize(tableDisplay.getSize());
                controlFrame.pack();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        buttonGetAllArticleSiege.addActionListener(e -> {
            try {
                TableArticleSiege tableArticleSiege = new TableArticleSiege(clientSiege.getQuerySiegeInterface().getAllArticle());
                tableDisplay = new JTable(tableArticleSiege);
                JScrollContentPane.setViewportView(tableDisplay);
                JScrollContentPane.setSize(tableDisplay.getSize());
                controlFrame.pack();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        buttonGetArticleByFamilySiege.addActionListener(e -> {
            TableArticleSiege tableArticleSiege;
            try {
                tableArticleSiege = new TableArticleSiege(
                        clientSiege.getQuerySiegeInterface().getArticleByFamily(fieldGetArticleByFamilySiege.getText()));
                tableDisplay = new JTable(tableArticleSiege);
                JScrollContentPane.setViewportView(tableDisplay);
                JScrollContentPane.setSize(tableDisplay.getSize());
                controlFrame.pack();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        buttonGetArticleByShopSiege.addActionListener(e -> {
            TableArticleSiege tableArticleSiege;
            try {
                tableArticleSiege = new TableArticleSiege(
                        clientSiege.getQuerySiegeInterface().getArticleByShop(fieldGetArticleByShopSiege.getText()));
                tableDisplay = new JTable(tableArticleSiege);
                JScrollContentPane.setViewportView(tableDisplay);
                JScrollContentPane.setSize(tableDisplay.getSize());
                controlFrame.pack();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        controlFrame.pack();
        controlFrame.setVisible(true);
    }

    // place custom component creation code here
    private void createUIComponents() {
    }
}
