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
import ui_package.ui_general.GeneralFrameSettings;
import ui_package.ui_general.HintTextField;
import ui_package.ui_general.NavigationBar;

public class ArticleManager extends Container {

    private JPanel panelMain;
    private JButton buttonGetAllArticleShop;
    private JScrollPane JScrollContentPane;
    private JTable tableDisplay;
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


    public ArticleManager() throws Exception {

        // Frame settings
        JFrame articleManagerFrame = new JFrame("Control page");
        GeneralFrameSettings generalFrameSettings = new GeneralFrameSettings(articleManagerFrame);
        articleManagerFrame.setContentPane(panelMain);
        articleManagerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        articleManagerFrame.setLocation(generalFrameSettings.getLocationX(), generalFrameSettings.getLocationY());

        // Navigation bar
        new NavigationBar(articleManagerFrame, buttonGetterPage, buttonBillPage, buttonAdminPage);

        ClientShop clientShop = new ClientShop();
        ClientSiege clientSiege = new ClientSiege();

        buttonGetAllArticleShop.addActionListener(e -> {
            try {
                TableArticleShop tableArticleShop = new TableArticleShop(clientShop.getQueryShopInterface().getAllArticle());
                createTableShopAndDisplayIt(tableArticleShop, articleManagerFrame);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        buttonGetArticleByReferenceShop.addActionListener(e -> {
            List<Article> listArticles = new ArrayList<>();
            try {
                listArticles.add(clientShop.getQueryShopInterface().getArticleByReference(fieldGetArticleByReferenceShop.getText()));
                TableArticleShop tableArticleShop = new TableArticleShop(listArticles);
                createTableShopAndDisplayIt(tableArticleShop, articleManagerFrame);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        buttonGetArticleByFamilyShop.addActionListener(e -> {
            try {
                TableArticleShop tableArticleShop = new TableArticleShop(
                        clientShop.getQueryShopInterface().getArticleByFamily((String) comboBoxFamilyShop.getSelectedItem()));
                createTableShopAndDisplayIt(tableArticleShop, articleManagerFrame);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        buttonGetAllArticleSiege.addActionListener(e -> {
            try {
                TableArticleSiege tableArticleSiege = new TableArticleSiege(clientSiege.getQuerySiegeInterface().getAllArticle());
                createTableSiegeAndDisplayIt(tableArticleSiege, articleManagerFrame);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        buttonGetArticleByFamilySiege.addActionListener(e -> {
            TableArticleSiege tableArticleSiege;
            try {
                tableArticleSiege = new TableArticleSiege(
                        clientSiege.getQuerySiegeInterface().getArticleByFamily((String) comboBoxFamilySiege.getSelectedItem()));
                createTableSiegeAndDisplayIt(tableArticleSiege, articleManagerFrame);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        buttonGetArticleByShopSiege.addActionListener(e -> {

            try {
                TableArticleSiege tableArticleSiege = new TableArticleSiege(
                        clientSiege.getQuerySiegeInterface().getArticleByShop((String) comboBoxShopSiege.getSelectedItem()));
                createTableSiegeAndDisplayIt(tableArticleSiege, articleManagerFrame);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        articleManagerFrame.pack();
        articleManagerFrame.setVisible(true);

    }

    // place custom component creation code here
    private void createUIComponents() throws Exception {

        fieldGetArticleByReferenceShop = new HintTextField("Reference");

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

    private void createTableSiegeAndDisplayIt(TableArticleSiege tableArticleSiege, JFrame jFrame) {
        tableDisplay = new JTable(tableArticleSiege);
        tableDisplay.setAutoCreateRowSorter(true);
        JScrollContentPane.setViewportView(tableDisplay);
        JScrollContentPane.setSize(tableDisplay.getSize());
        jFrame.pack();
    }

    private void createTableShopAndDisplayIt(TableArticleShop tableArticleShop, JFrame jFrame) {
        tableDisplay = new JTable(tableArticleShop);
        tableDisplay.setAutoCreateRowSorter(true);
        JScrollContentPane.setViewportView(tableDisplay);
        JScrollContentPane.setSize(tableDisplay.getSize());
        jFrame.pack();
    }

}
