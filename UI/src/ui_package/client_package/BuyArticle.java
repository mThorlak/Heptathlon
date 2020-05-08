package ui_package.client_package;

import client_package.ClientShop;
import model_table.TableArticleShop;
import rmi_general.BillCSVManager;
import rmi_shop.tables.Article;
import rmi_siege.tables.Bill;
import ui_package.ui_general.GeneralFrameSettings;
import ui_package.ui_general.HintTextField;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class BuyArticle {
    private JPanel panelMain;
    private JScrollPane scrollPaneTableDisplay;
    private JTable tableDisplayArticle;
    private JTable tableSelectedArticle;
    private JButton buttonAddArticleSelected;
    private JButton buttonRemoveArticleSelected;
    private JComboBox<String> comboBoxFamily;
    private JButton buttonSortByFamily;
    private JTextField textFieldGetArticleByReference;
    private JButton buttonGetArticleByReference;
    private JTextArea textAreaTotal;
    private JComboBox<String> comboBoxPayBill;
    private JButton buttonCancel;
    private JTextField textFieldQuantityToBuyArticle;
    private JTextField textFieldQuantityToRemoveArticle;
    private JTextArea textAreaAddArticle;
    private JTextArea textAreaRemoveArticle;
    private JScrollPane scrollPaneArticleSelected;
    private JButton buttonConfirm;
    private JComboBox<String> comboBoxPaymentMethod;
    private JButton buttonSeeAllArticle;
    private JLabel jLabelHeader;
    private final List<Article> shopCartArticleList;
    private boolean alreadyInShopCartArticleList;
    private final String[] paymentMethod = {"Cash", "Blue card", "Bitcoin", "Kidney"};

    public BuyArticle() throws Exception {

        // Frame settings
        JFrame buyArticleFrame = new JFrame("Heptathlon");
        buyArticleFrame.setContentPane(panelMain);
        buyArticleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ClientShop clientShop = new ClientShop();

        TableArticleShop modelTable = new TableArticleShop(clientShop.getQueryShopInterface().getAllArticle());
        tableDisplayArticle = new JTable(modelTable);
        scrollPaneTableDisplay.setViewportView(tableDisplayArticle);
        scrollPaneTableDisplay.setSize(tableDisplayArticle.getSize());

        shopCartArticleList = new ArrayList<>();

        tableDisplayArticle.getSelectionModel().addListSelectionListener(e ->
                textAreaAddArticle.setText((String) tableDisplayArticle.getValueAt(tableDisplayArticle.getSelectedRow(), 0)));

        buttonGetArticleByReference.addActionListener(e -> {
            List<Article> listArticles = new ArrayList<>();
            try {
                TableArticleShop modelTable1;
                listArticles.add(clientShop.getQueryShopInterface().getArticleByReference(textFieldGetArticleByReference.getText()));
                modelTable1 = new TableArticleShop(listArticles);
                tableDisplayArticle = new JTable(modelTable1);
                tableDisplayArticle.getSelectionModel().addListSelectionListener(ef ->
                        textAreaAddArticle.setText((String) tableDisplayArticle.getValueAt(tableDisplayArticle.getSelectedRow(), 0)));
                scrollPaneTableDisplay.setViewportView(tableDisplayArticle);
                scrollPaneTableDisplay.setSize(tableDisplayArticle.getSize());
                buyArticleFrame.pack();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        buttonSeeAllArticle.addActionListener(e -> {
            try {
                TableArticleShop tableArticle = new TableArticleShop(
                        clientShop.getQueryShopInterface().getAllArticle());
                tableDisplayArticle = new JTable(tableArticle);
                tableDisplayArticle.getSelectionModel().addListSelectionListener(ef ->
                        textAreaAddArticle.setText((String) tableDisplayArticle.getValueAt(tableDisplayArticle.getSelectedRow(), 0)));
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
                tableDisplayArticle.getSelectionModel().addListSelectionListener(ef ->
                        textAreaAddArticle.setText((String) tableDisplayArticle.getValueAt(tableDisplayArticle.getSelectedRow(), 0)));
                scrollPaneTableDisplay.setViewportView(tableDisplayArticle);
                scrollPaneTableDisplay.setSize(tableDisplayArticle.getSize());
                buyArticleFrame.pack();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        buttonAddArticleSelected.addActionListener(e -> {
            try {
                if (!textAreaAddArticle.getText().isEmpty()) {
                    Article articleBeforeBuying = clientShop.getQueryShopInterface().getArticleByReference(
                            (String) tableDisplayArticle.getValueAt(tableDisplayArticle.getSelectedRow(), 0));

                    if (articleBeforeBuying.getStock() < Integer.parseInt(textFieldQuantityToBuyArticle.getText())) {
                        System.out.println("Too much article selected");
                    } else {
                        Article articleBought = new Article();
                        articleBought.setReference(articleBeforeBuying.getReference());
                        articleBought.setStock(Integer.parseInt(textFieldQuantityToBuyArticle.getText()));
                        articleBought.setPrice(articleBeforeBuying.getPrice());
                        articleBought.setDescription(articleBeforeBuying.getDescription());

                        for (Article article : this.shopCartArticleList) {
                            if (article.getReference().equals(articleBought.getReference())) {
                                this.alreadyInShopCartArticleList = true;
                                if (articleBeforeBuying.getStock() < article.getStock() + articleBought.getStock())
                                    System.out.println("Too much for the actual stock");
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
                        textAreaTotal.setText(String.valueOf(total));

                        tableSelectedArticle.getSelectionModel().addListSelectionListener(ef ->
                                textAreaRemoveArticle.setText((String) tableSelectedArticle.getValueAt(tableSelectedArticle.getSelectedRow(), 0)));
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        buttonRemoveArticleSelected.addActionListener(e -> {
            if (!textAreaRemoveArticle.getText().isEmpty()) {
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
                textAreaTotal.setText(String.valueOf(total));

                tableSelectedArticle.getSelectionModel().addListSelectionListener(ef ->
                        textAreaAddArticle.setText((String) tableDisplayArticle.getValueAt(tableDisplayArticle.getSelectedRow(), 0)));

                textAreaRemoveArticle.setText("");

                tableSelectedArticle.getSelectionModel().addListSelectionListener(ef ->
                        textAreaRemoveArticle.setText((String) tableSelectedArticle.getValueAt(tableSelectedArticle.getSelectedRow(), 0)));

            }
        });

        comboBoxPayBill.addItemListener(e -> {
            if (Objects.equals(comboBoxPayBill.getSelectedItem(), "Yes")) {
                comboBoxPaymentMethod.removeAllItems();
                for (String payment : paymentMethod)
                    comboBoxPaymentMethod.addItem(payment);
            }
            else {
                comboBoxPaymentMethod.removeAllItems();
                comboBoxPaymentMethod.addItem("No payment method");
            }
        });

        buttonConfirm.addActionListener(e -> {

            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String strDate = formatter.format(date);
            String shopName = "shop1";
            String paymentMethod = String.valueOf(comboBoxPaymentMethod.getSelectedItem());
            boolean inPaidBill;
            inPaidBill = Objects.equals(comboBoxPayBill.getSelectedItem(), "Yes");

            Bill bill = new Bill(strDate, shopName, Float.parseFloat(textAreaTotal.getText()), paymentMethod, this.shopCartArticleList);
            BillCSVManager billCSVManager = new BillCSVManager();

            try {
                for (Article article : shopCartArticleList) {
                    Article articleBeforeBought = clientShop.getQueryShopInterface().getArticleByReference(article.getReference());
                    clientShop.getQueryShopInterface().updateStock(shopName, article.getReference(),
                            articleBeforeBought.getStock() - article.getStock());
                }

                billCSVManager.writeNewBill(bill, inPaidBill);
                new ConfirmArticleBought(bill);
                buyArticleFrame.dispose();

            } catch (Exception ioException) {
                ioException.printStackTrace();
            }
        });

        buttonCancel.addActionListener(e -> buyArticleFrame.dispose());

        buyArticleFrame.pack();
        buyArticleFrame.setVisible(true);
    }

    private void createUIComponents() throws Exception {

        BufferedImage myPicture = ImageIO.read(new File("UI/resources/clientPageHeader.png"));
        jLabelHeader = new JLabel(new ImageIcon(myPicture));

        ClientShop clientShop = new ClientShop();

        List<String> familyShopList = clientShop.getQueryShopInterface().getAllFamily();
        String[] familyShopArray = new String[familyShopList.size()];
        familyShopList.toArray(familyShopArray);
        comboBoxFamily = new JComboBox<>(familyShopArray);

        String[] yesOrNo = {"Yes", "No"};
        comboBoxPayBill = new JComboBox<>(yesOrNo);

        comboBoxPaymentMethod = new JComboBox<>(paymentMethod);

        textFieldQuantityToBuyArticle = new HintTextField("Quantity");
        textFieldGetArticleByReference = new HintTextField("Reference");
        textFieldQuantityToRemoveArticle = new HintTextField("Quantity");
    }
}
