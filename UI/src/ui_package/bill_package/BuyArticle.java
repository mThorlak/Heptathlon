package ui_package.bill_package;

import client_package.ClientShop;
import model_table.TableArticleShop;
import rmi_shop.tables.Article;
import ui_package.GeneralFrameSettings;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.util.ArrayList;
import java.util.List;

public class BuyArticle {
    private JPanel panelMain;
    private JScrollPane scrollPaneTableDisplay;
    private JTable tableDisplayArticle;
    private JPanel panelSelectedArticle;
    private JTable tableSelectedArticle;
    private JButton buttonAddArticleSelected;
    private JButton buttonRemoveArticleSelected;
    private JComboBox<String> comboBoxFamily;
    private JButton buttonSortByFamily;
    private JTextField textFieldGetArticleByReference;
    private JButton buttonGetArticleByReference;
    private JLabel labelTotal;
    private JComboBox<String> comboBoxPayBill;
    private JButton buttonConfirm;
    private JButton buttonCancel;
    private JTextField textboxHowManyAddArticle;
    private JComboBox comboBox2;
    private JLabel labelAddArticle;
    private JLabel labelRemoveArticle;
    private JScrollPane scrollPaneArticleSelected;
    private final String shopName = "shop1";
    private List<Article> shopCartArticleList;
    private boolean alreadyInShopCartArticleList;

    public BuyArticle() throws Exception {

        // Frame settings
        JFrame buyArticleFrame = new JFrame("Buy article");
        GeneralFrameSettings generalFrameSettings = new GeneralFrameSettings(buyArticleFrame);
        buyArticleFrame.setContentPane(panelMain);
        buyArticleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        buyArticleFrame.setLocation(generalFrameSettings.getLocationX(), generalFrameSettings.getLocationY());

        ClientShop clientShop = new ClientShop();
        TableArticleShop modelTable = new TableArticleShop(clientShop.getQueryShopInterface().getAllArticle());
        tableDisplayArticle = new JTable(modelTable);
        scrollPaneTableDisplay.setViewportView(tableDisplayArticle);
        scrollPaneTableDisplay.setSize(tableDisplayArticle.getSize());

        buyArticleFrame.pack();
        buyArticleFrame.setVisible(true);

        shopCartArticleList = new ArrayList<>();

        tableDisplayArticle.getSelectionModel().addListSelectionListener(e ->
                labelAddArticle.setText((String) tableDisplayArticle.getValueAt(tableDisplayArticle.getSelectedRow(), 0)));

        buttonGetArticleByReference.addActionListener(e -> {
            List<Article> listArticles = new ArrayList<>();
            try {
                TableArticleShop modelTable1;
                listArticles.add(clientShop.getQueryShopInterface().getArticleByReference(textFieldGetArticleByReference.getText()));
                modelTable1 = new TableArticleShop(listArticles);
                tableDisplayArticle = new JTable(modelTable1);
                scrollPaneTableDisplay.setViewportView(tableDisplayArticle);
                scrollPaneTableDisplay.setSize(tableDisplayArticle.getSize());
                buyArticleFrame.pack();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        buttonSortByFamily.addActionListener(e -> {
            try {
                TableArticleShop tableArticle = new TableArticleShop(
                        clientShop.getQueryShopInterface().getArticleByFamily((String) comboBoxFamily.getSelectedItem()));
                tableDisplayArticle = new JTable(tableArticle);
                scrollPaneTableDisplay.setViewportView(tableDisplayArticle);
                scrollPaneTableDisplay.setSize(tableDisplayArticle.getSize());
                buyArticleFrame.pack();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        buttonAddArticleSelected.addActionListener(e -> {
            try {
                if (textboxHowManyAddArticle.getText().isEmpty()) {
                    textboxHowManyAddArticle.setText("1");
                }

                Article articleBeforeBuying = clientShop.getQueryShopInterface().getArticleByReference(
                        (String) tableDisplayArticle.getValueAt(tableDisplayArticle.getSelectedRow(), 0));

                if (articleBeforeBuying.getStock() < Integer.parseInt(textboxHowManyAddArticle.getText())) {
                    System.out.println("Too much article selected");
                }
                else {
                    Article articleBought = new Article();
                    articleBought.setReference(articleBeforeBuying.getReference());
                    articleBought.setStock(Integer.parseInt(textboxHowManyAddArticle.getText()));
                    articleBought.setPrice(articleBeforeBuying.getPrice());
                    articleBought.setDescription(articleBeforeBuying.getDescription());

                    for (Article article : this.shopCartArticleList) {
                        if (article.getReference().equals(articleBought.getReference())) {
                            this.alreadyInShopCartArticleList = true;
                            if (articleBeforeBuying.getStock() < article.getStock() + articleBought.getStock())
                                System.out.println("nop");
                            else
                                article.setStock(article.getStock() + articleBought.getStock());
                        }
                    }
                    if (!alreadyInShopCartArticleList)
                        this.shopCartArticleList.add(articleBought);

                    TableArticleShop tableArticleBought = new TableArticleShop(this.shopCartArticleList);
                    tableSelectedArticle = new JTable(tableArticleBought);
                    scrollPaneArticleSelected.setViewportView(tableSelectedArticle);
                    scrollPaneArticleSelected.setSize(tableSelectedArticle.getSize());
                    buyArticleFrame.pack();
                    this.alreadyInShopCartArticleList = false;

                    double total = 0;
                    for (Article article : this.shopCartArticleList)
                        total = total + (article.getStock() * article.getPrice());
                    labelTotal.setText(String.valueOf(total));
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

    }

    private void createUIComponents() throws Exception {

        ClientShop clientShop = new ClientShop();

        List<String> familyShopList = clientShop.getQueryShopInterface().getAllFamily();
        String[] familyShopArray = new String[familyShopList.size()];
        familyShopList.toArray(familyShopArray);
        comboBoxFamily = new JComboBox<>(familyShopArray);

        String[] yesOrNo = {"Yes", "No"};
        comboBoxPayBill = new JComboBox<>(yesOrNo);
    }
}