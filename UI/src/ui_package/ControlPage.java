package ui_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import client_package.MainClientShop;
import client_package.MainClientSiege;
import model_table.TableArticleShop;
import model_table.TableArticleSiege;
import rmi_shop.tables.Article;

public class ControlPage extends Container {

    private JPanel panelMain;
    private JLabel labelTitle;
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
    private JButton getArticleButton;


    public ControlPage() throws Exception {
        JFrame controlFrame = new JFrame("Control page");
        GeneralFrameSettings generalFrameSettings = new GeneralFrameSettings(controlFrame);
        controlFrame.setContentPane(panelMain);
        controlFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        controlFrame.setLocation(generalFrameSettings.getLocationX(), generalFrameSettings.getLocationY());


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

        getArticleButton.addActionListener(e -> {
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

        controlFrame.pack();
        controlFrame.setVisible(true);
    }

    // place custom component creation code here
    private void createUIComponents() throws Exception {
    }
}
