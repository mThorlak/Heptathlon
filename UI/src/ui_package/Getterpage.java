package ui_package;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import client_package.ClientShop;
import client_package.ClientSiege;
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
    private JButton buttonGetArticleByFamilyShop;
    private JComboBox<String> comboBoxFamilyShop;
    private JComboBox<String> comboBoxFamilySiege;
    private JComboBox<String> comboBoxShopSiege;
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

        ClientShop clientShop = new ClientShop();
        ClientSiege clientSiege = new ClientSiege();

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
                listArticles.add(clientShop.getQueryShopInterface().getArticleByReference(fieldGetArticleByReferenceShop.getText()));
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
                        clientShop.getQueryShopInterface().getArticleByFamily((String) comboBoxFamilyShop.getSelectedItem()));
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
                        clientSiege.getQuerySiegeInterface().getArticleByFamily((String) comboBoxFamilySiege.getSelectedItem()));
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
                        clientSiege.getQuerySiegeInterface().getArticleByShop((String) comboBoxShopSiege.getSelectedItem()));
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
    private void createUIComponents() throws Exception {

        ClientShop clientShop = new ClientShop();
        List<String> familyShopList = clientShop.getQueryShopInterface().getAllFamily();
        String[] familyShopArray = new String[familyShopList.size()];
        familyShopList.toArray(familyShopArray);
        comboBoxFamilyShop = new JComboBox<>(familyShopArray);

        ClientSiege clientSiege = new ClientSiege();
        List<String> familySiegeList = clientSiege.getQuerySiegeInterface().getAllFamily();
        String[] familySiegeArray = new String[familySiegeList.size()];
        familySiegeList.toArray(familySiegeArray);
        comboBoxFamilySiege = new JComboBox<>(familySiegeArray);

        List<String> shopSiegeList = clientSiege.getQuerySiegeInterface().getAllShop();
        String[] shopSiegeArray = new String[shopSiegeList.size()];
        shopSiegeList.toArray(shopSiegeArray);
        comboBoxShopSiege = new JComboBox<>(shopSiegeArray);
    }
}
