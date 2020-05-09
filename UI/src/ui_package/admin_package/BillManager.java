package ui_package.admin_package;

import client_package.ClientSiege;
import model_table.TableBillSiege;
import rmi_general.BillCSVManager;
import rmi_siege.tables.Bill;
import ui_package.ui_general.HintTextField;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BillManager {
    private JPanel panelMain;
    private JScrollPane scrollPaneTableDisplay;
    private JTable tableBill;
    private JTextField textFieldGetButtonByID;
    private JButton buttonGetBillByID;
    private JButton buttonBillByDateAndShop;
    private JTextField textFieldDate;
    private JTextField textFieldShop;
    private JTextField textFieldDateCA;
    private JTextField textFieldShopCA;
    private JButton buttonCalculateCA;
    private JTextPane textAreaCABillNonPaid;
    private JTextPane textAreaCABillPaid;
    private JTextPane textAreaCATotalBill;
    private JButton buttonDetailBillsDisplayed;
    private JTextPane textAreaDetailBills;
    private JRadioButton radioButtonBillFromToday;
    private JPanel panelFunctionBillDB;
    private JTextField textFieldBillByIDCVS;
    private JButton buttonBillByIDCSV;
    private JPanel panelBillCSV;
    private JButton buttonGetAllBillCSV;
    private JComboBox<String> comboBoxCSVFile;
    private JPanel panelCASection;
    private JTextPane textAreaCACSV;
    private JLabel jLabelHeader;
    private JButton buttonGetAllBill;
    private JPanel panelCASectionCSV;
    private JButton buttonClearFieldDetailBills;
    private JButton buttonDetailBillDisplayedCSV;
    private JButton buttonClearFieldDetailBillsCSV;
    private final List<Bill> billBufferCSV;

    public BillManager() throws RemoteException, NotBoundException {
        // Frame settings
        JFrame billManagerFrame = new JFrame("Heptathlon");
        billManagerFrame.setContentPane(panelMain);
        billManagerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        textAreaDetailBills.setVisible(false);
        panelBillCSV.setVisible(false);
        panelCASectionCSV.setVisible(false);

        ClientSiege clientSiege = new ClientSiege();
        billBufferCSV = new ArrayList<>();

        buttonGetAllBill.addActionListener(e -> {
            try {
                List<Bill> bills = clientSiege.getQuerySiegeInterface().getAllBill();
                TableBillSiege modelTable = new TableBillSiege(bills);
                tableBill = new JTable(modelTable);
                tableBill.setAutoCreateRowSorter(true);
                scrollPaneTableDisplay.setViewportView(tableBill);
                scrollPaneTableDisplay.setSize(tableBill.getSize());

                billManagerFrame.pack();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        buttonGetBillByID.addActionListener(e -> {
            if (!textFieldGetButtonByID.getText().isEmpty()) {
                try {
                    List<Bill> bills = new ArrayList<>();
                    Bill bill = clientSiege.getQuerySiegeInterface().getBillByID(textFieldGetButtonByID.getText());
                    bills.add(bill);
                    TableBillSiege modelTable = new TableBillSiege(bills);
                    tableBill = new JTable(modelTable);
                    scrollPaneTableDisplay.setViewportView(tableBill);
                    scrollPaneTableDisplay.setSize(tableBill.getSize());

                    billManagerFrame.pack();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            }
        });

        buttonBillByDateAndShop.addActionListener(e -> {
            if (!textFieldDate.getText().equals("Date") && textFieldShop.getText().equals("Shop")) {
                try {
                    List<Bill> bills = clientSiege.getQuerySiegeInterface().getBillByDate(textFieldDate.getText());
                    TableBillSiege modelTable = new TableBillSiege(bills);
                    tableBill = new JTable(modelTable);
                    tableBill.setAutoCreateRowSorter(true);
                    scrollPaneTableDisplay.setViewportView(tableBill);
                    scrollPaneTableDisplay.setSize(tableBill.getSize());

                    billManagerFrame.pack();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            } else if (textFieldDate.getText().equals("Date") && !textFieldShop.getText().equals("Shop")) {
                try {
                    List<Bill> bills = clientSiege.getQuerySiegeInterface().getBillByShop(textFieldShop.getText());
                    TableBillSiege modelTable = new TableBillSiege(bills);
                    tableBill = new JTable(modelTable);
                    tableBill.setAutoCreateRowSorter(true);
                    scrollPaneTableDisplay.setViewportView(tableBill);
                    scrollPaneTableDisplay.setSize(tableBill.getSize());
                    billManagerFrame.pack();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            } else if (!textFieldDate.getText().equals("Date") && !textFieldShop.getText().equals("Shop")) {
                try {
                    List<Bill> bills = clientSiege.getQuerySiegeInterface().getBillByDateAndShop(
                            textFieldDate.getText(), textFieldShop.getText());
                    TableBillSiege modelTable = new TableBillSiege(bills);
                    tableBill = new JTable(modelTable);
                    tableBill.setAutoCreateRowSorter(true);
                    scrollPaneTableDisplay.setViewportView(tableBill);
                    scrollPaneTableDisplay.setSize(tableBill.getSize());

                    billManagerFrame.pack();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        buttonCalculateCA.addActionListener(e -> {
            if (!textFieldDateCA.getText().equals("Date") && !textFieldShopCA.getText().equals("Shop")) {
                try {
                    textAreaCABillNonPaid.setText(String.valueOf(clientSiege.getQuerySiegeInterface().calculateCAShopDayBillNonPaid(
                            textFieldDateCA.getText(), textFieldShopCA.getText())));
                    textAreaCABillPaid.setText(String.valueOf(clientSiege.getQuerySiegeInterface().calculateCAShopDayBillPaid(
                            textFieldDateCA.getText(), textFieldShopCA.getText())));
                    textAreaCATotalBill.setText(String.valueOf(clientSiege.getQuerySiegeInterface().calculateCAShopDayAllBill(
                            textFieldDateCA.getText(), textFieldShopCA.getText())));
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        buttonDetailBillsDisplayed.addActionListener(e -> {
            List<Bill> bills = new ArrayList<>();
            try {
                for (int i = 0; i < tableBill.getRowCount(); i++) {
                    Bill bill = clientSiege.getQuerySiegeInterface().getBillByID((String) tableBill.getValueAt(i, 0));
                    bills.add(bill);
                }
                textAreaDetailBills.setText(bills.toString());
                textAreaDetailBills.setVisible(true);

                billManagerFrame.pack();

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        buttonClearFieldDetailBills.addActionListener(e -> {
            textAreaDetailBills.setText("");
            textAreaDetailBills.setVisible(false);
        });

        radioButtonBillFromToday.addActionListener(e -> {
            if (radioButtonBillFromToday.isSelected()) {
                panelBillCSV.setVisible(true);
                panelFunctionBillDB.setVisible(false);
                panelCASection.setVisible(false);
                panelCASectionCSV.setVisible(true);
            }
            else {
                panelBillCSV.setVisible(false);
                panelFunctionBillDB.setVisible(true);
                panelCASection.setVisible(true);
                panelCASectionCSV.setVisible(false);
            }
            billManagerFrame.pack();
        });

        buttonBillByIDCSV.addActionListener(e -> {
            if (!textFieldGetButtonByID.getText().isEmpty()) {
                BillCSVManager csvManager = new BillCSVManager();
                List<String[]> billsCSV = csvManager.readLineByLine("Server/resources/bill.csv");
                for (String[] billCSV : billsCSV) {
                    if (billCSV[1].equals(textFieldGetButtonByID.getText())) {
                        Bill bill = csvManager.convertLineInBill(billCSV);
                        List<Bill> bills = new ArrayList<>();
                        bills.add(bill);
                        TableBillSiege modelTable = new TableBillSiege(bills);
                        tableBill = new JTable(modelTable);
                        tableBill.setAutoCreateRowSorter(true);
                        scrollPaneTableDisplay.setViewportView(tableBill);
                        scrollPaneTableDisplay.setSize(tableBill.getSize());
                        billManagerFrame.pack();
                        break;
                    }
                }
            }
        });

        buttonGetAllBillCSV.addActionListener(e -> {
            billBufferCSV.clear();
            BillCSVManager csvManager = new BillCSVManager();
            String filePath;
            if (Objects.equals(comboBoxCSVFile.getSelectedItem(), "Bill non paid"))
                filePath = csvManager.BILL_PATH;
            else
                filePath = csvManager.BILL_PAID_PATH;
            List<String[]> billsCSV = csvManager.readLineByLine(filePath);
            for (String[] billCSV : billsCSV) {
                if (billCSV[0].equals(billsCSV.get(0)[0]))
                    continue;
                Bill bill = csvManager.convertLineInBill(billCSV);
                billBufferCSV.add(bill);
            }

            textAreaCACSV.setText(String.valueOf(csvManager.getTotal(filePath)));

            TableBillSiege modelTable = new TableBillSiege(billBufferCSV);
            tableBill = new JTable(modelTable);
            tableBill.setAutoCreateRowSorter(true);
            scrollPaneTableDisplay.setViewportView(tableBill);
            scrollPaneTableDisplay.setSize(tableBill.getSize());
            billManagerFrame.pack();
        });

        buttonBillByIDCSV.addActionListener(e -> {
            billBufferCSV.clear();
            BillCSVManager csvManager = new BillCSVManager();
            String filePath;
            if (Objects.equals(comboBoxCSVFile.getSelectedItem(), "Bill non paid"))
                filePath = csvManager.BILL_PATH;
            else
                filePath = csvManager.BILL_PAID_PATH;

            List<String[]> billsCSV = csvManager.readLineByLine(filePath);
            for (String[] billCSV : billsCSV) {
                if (billCSV[0].equals(billsCSV.get(0)[0]))
                    continue;
                else if (billCSV[1].equals(textFieldBillByIDCVS.getText())) {
                    Bill bill = csvManager.convertLineInBill(billCSV);
                    billBufferCSV.add(bill);
                    break;
                }
            }

            TableBillSiege modelTable = new TableBillSiege(billBufferCSV);
            tableBill = new JTable(modelTable);
            scrollPaneTableDisplay.setViewportView(tableBill);
            scrollPaneTableDisplay.setSize(tableBill.getSize());
            billManagerFrame.pack();

        });

        buttonDetailBillDisplayedCSV.addActionListener(e -> {
            textAreaDetailBills.setText(billBufferCSV.toString());
            textAreaDetailBills.setVisible(true);

            billManagerFrame.pack();

        });

        buttonClearFieldDetailBillsCSV.addActionListener(e -> {
            textAreaDetailBills.setText("");
            textAreaDetailBills.setVisible(false);
        });

        billManagerFrame.pack();
        billManagerFrame.setVisible(true);

    }

    private void createUIComponents() throws IOException {

        BufferedImage myPicture = ImageIO.read(new File("UI/resources/adminPageHeader.png"));
        jLabelHeader = new JLabel(new ImageIcon(myPicture));

        String [] csvName = {"Bill paid", "Bill non paid"};
        comboBoxCSVFile = new JComboBox<>(csvName);

        textFieldBillByIDCVS = new HintTextField("ID bill");
        textFieldDate = new HintTextField("Date");
        textFieldDateCA = new HintTextField("Date");
        textFieldGetButtonByID = new HintTextField("ID bill");
        textFieldShop = new HintTextField("Shop");
        textFieldShopCA = new HintTextField("Shop");
    }
}
