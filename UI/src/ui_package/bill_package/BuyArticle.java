package ui_package.bill_package;

import client_package.ClientShop;
import model_table.TableArticleShop;
import rmi_general.BillManager;
import rmi_shop.tables.Article;
import rmi_siege.tables.Bill;
import ui_package.GeneralFrameSettings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    private JButton buttonCancel;
    private JTextField textboxQuantityToBuyArticle;
    private JTextField textFieldQuantityToRemoveArticle;
    private JLabel labelAddArticle;
    private JLabel labelRemoveArticle;
    private JScrollPane scrollPaneArticleSelected;
    private JButton buttonConfirm;
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
                if (!labelAddArticle.getText().isEmpty()) {
                    if (textboxQuantityToBuyArticle.getText().isEmpty()) {
                        textboxQuantityToBuyArticle.setText("1");
                    }

                    Article articleBeforeBuying = clientShop.getQueryShopInterface().getArticleByReference(
                            (String) tableDisplayArticle.getValueAt(tableDisplayArticle.getSelectedRow(), 0));

                    if (articleBeforeBuying.getStock() < Integer.parseInt(textboxQuantityToBuyArticle.getText())) {
                        System.out.println("Too much article selected");
                    } else {
                        Article articleBought = new Article();
                        articleBought.setReference(articleBeforeBuying.getReference());
                        articleBought.setStock(Integer.parseInt(textboxQuantityToBuyArticle.getText()));
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

                        tableSelectedArticle.getSelectionModel().addListSelectionListener(ef ->
                                labelRemoveArticle.setText((String) tableSelectedArticle.getValueAt(tableSelectedArticle.getSelectedRow(), 0)));
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        buttonRemoveArticleSelected.addActionListener(e -> {
            if (!labelRemoveArticle.getText().isEmpty()) {
                if (textFieldQuantityToRemoveArticle.getText().isEmpty()) {
                    textFieldQuantityToRemoveArticle.setText("1");
                }

                if (shopCartArticleList.get(tableSelectedArticle.getSelectedRow())
                        .getStock() <= Integer.parseInt(textFieldQuantityToRemoveArticle.getText()))
                    this.shopCartArticleList.remove(tableSelectedArticle.getSelectedRow());
                else
                    shopCartArticleList.get(tableSelectedArticle.getSelectedRow())
                            .setStock(shopCartArticleList.get(tableSelectedArticle.getSelectedRow()).getStock() -
                                    Integer.parseInt(textFieldQuantityToRemoveArticle.getText()));

                TableArticleShop tableArticleBought = new TableArticleShop(this.shopCartArticleList);
                tableSelectedArticle = new JTable(tableArticleBought);
                scrollPaneArticleSelected.setViewportView(tableSelectedArticle);
                scrollPaneArticleSelected.setSize(tableSelectedArticle.getSize());
                buyArticleFrame.pack();

                double total = 0;
                for (Article article : this.shopCartArticleList)
                    total = total + (article.getStock() * article.getPrice());
                labelTotal.setText(String.valueOf(total));

                tableSelectedArticle.getSelectionModel().addListSelectionListener(ef ->
                        labelAddArticle.setText((String) tableDisplayArticle.getValueAt(tableDisplayArticle.getSelectedRow(), 0)));

                labelRemoveArticle.setText("");

                tableSelectedArticle.getSelectionModel().addListSelectionListener(ef ->
                        labelRemoveArticle.setText((String) tableSelectedArticle.getValueAt(tableSelectedArticle.getSelectedRow(), 0)));

            }
        });

        buttonConfirm.addActionListener(e -> {

            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String strDate = formatter.format(date);
            String shopName = "shop1";
            String paymentMethod;

            if (Objects.equals(comboBoxPayBill.getSelectedItem(), "No"))
                paymentMethod = "No payment";
            else
                paymentMethod = "paid";

            Bill bill = new Bill(strDate, shopName, Float.parseFloat(labelTotal.getText()), paymentMethod, this.shopCartArticleList);
            BillManager billManager = new BillManager();
            try {
                billManager.writeNewBill(bill, false);
                buyArticleFrame.dispose();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        buttonCancel.addActionListener(e -> {
            buyArticleFrame.dispose();
        });

        buyArticleFrame.pack();
        buyArticleFrame.setVisible(true);

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
