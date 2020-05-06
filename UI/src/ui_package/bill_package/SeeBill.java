package ui_package.bill_package;

import client_package.ClientSiege;
import model_table.TableBillSiege;
import rmi_general.BillCSVManager;
import rmi_siege.tables.Bill;
import ui_package.ui_general.GeneralFrameSettings;

import javax.swing.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class SeeBill {
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
    private JTextArea textAreaCABillNonPaid;
    private JTextArea textAreaCABillPaid;
    private JTextArea textAreaCATotalBill;
    private JButton detailBillsDisplayedButton;
    private JTextArea textAreaDetailBills;
    private JRadioButton radioButtonBillFromToday;
    private JPanel panelFunctionBillDB;
    private JTextField textFieldBillByIDCVS;
    private JButton buttonBillByIDCSV;
    private JPanel panelBillCSV;
    private JButton buttonGetAllBill;
    private JComboBox<String> comboBoxCSVFile;
    private JPanel panelCASection;
    private JTextArea textAreaCACSV;

    public SeeBill() throws RemoteException, NotBoundException {
        // Frame settings
        JFrame seeBillFrame = new JFrame("Control page");
        GeneralFrameSettings generalFrameSettings = new GeneralFrameSettings(seeBillFrame);
        seeBillFrame.setContentPane(panelMain);
        seeBillFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        seeBillFrame.setLocation(generalFrameSettings.getLocationX(), generalFrameSettings.getLocationY());

        textAreaDetailBills.setVisible(false);
        panelBillCSV.setVisible(false);

        ClientSiege clientSiege = new ClientSiege();

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

                    seeBillFrame.pack();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            }
        });

        buttonBillByDateAndShop.addActionListener(e -> {
            if (!textFieldDate.getText().isEmpty() && textFieldShop.getText().isEmpty()) {
                try {
                    List<Bill> bills = clientSiege.getQuerySiegeInterface().getBillByDate(textFieldDate.getText());
                    TableBillSiege modelTable = new TableBillSiege(bills);
                    tableBill = new JTable(modelTable);
                    scrollPaneTableDisplay.setViewportView(tableBill);
                    scrollPaneTableDisplay.setSize(tableBill.getSize());

                    seeBillFrame.pack();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            } else if (textFieldDate.getText().isEmpty() && !textFieldShop.getText().isEmpty()) {
                try {
                    List<Bill> bills = clientSiege.getQuerySiegeInterface().getBillByShop(textFieldShop.getText());
                    TableBillSiege modelTable = new TableBillSiege(bills);
                    tableBill = new JTable(modelTable);
                    scrollPaneTableDisplay.setViewportView(tableBill);
                    scrollPaneTableDisplay.setSize(tableBill.getSize());

                    seeBillFrame.pack();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            } else if (!textFieldDate.getText().isEmpty() && !textFieldShop.getText().isEmpty()) {
                try {
                    List<Bill> bills = clientSiege.getQuerySiegeInterface().getBillByDateAndShop(
                            textFieldDate.getText(), textFieldShop.getText());
                    TableBillSiege modelTable = new TableBillSiege(bills);
                    tableBill = new JTable(modelTable);
                    scrollPaneTableDisplay.setViewportView(tableBill);
                    scrollPaneTableDisplay.setSize(tableBill.getSize());

                    seeBillFrame.pack();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        buttonCalculateCA.addActionListener(e -> {
            if (!textFieldDateCA.getText().isEmpty() && !textFieldShopCA.getText().isEmpty()) {
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

        detailBillsDisplayedButton.addActionListener(e -> {
            List<Bill> bills = new ArrayList<>();
            try {
                for (int i = 0; i < tableBill.getRowCount(); i++) {
                    Bill bill = clientSiege.getQuerySiegeInterface().getBillByID((String) tableBill.getValueAt(i, 0));
                    bills.add(bill);
                }
                textAreaDetailBills.setText(bills.toString());
                textAreaDetailBills.setVisible(true);

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        radioButtonBillFromToday.addActionListener(e -> {
            if (radioButtonBillFromToday.isSelected()) {
                panelBillCSV.setVisible(true);
                panelFunctionBillDB.setVisible(false);
                panelCASection.setVisible(false);
            }
            else {
                panelBillCSV.setVisible(false);
                panelFunctionBillDB.setVisible(true);
                panelCASection.setVisible(true);
            }
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
                        scrollPaneTableDisplay.setViewportView(tableBill);
                        scrollPaneTableDisplay.setSize(tableBill.getSize());
                        seeBillFrame.pack();
                        break;
                    }
                }
            }
        });

        buttonGetAllBill.addActionListener(e -> {
            BillCSVManager csvManager = new BillCSVManager();
            List<String[]> billsCSV = csvManager.readLineByLine("Server/resources/bill.csv");
            List<Bill> bills = new ArrayList<>();
            for (String[] billCSV : billsCSV) {
                if (billCSV[0].equals(billsCSV.get(0)[0]))
                    continue;
                Bill bill = csvManager.convertLineInBill(billCSV);
                bills.add(bill);
            }

            textAreaCACSV.setText(String.valueOf(csvManager.getTotal("Server/resources/bill.csv")));

            TableBillSiege modelTable = new TableBillSiege(bills);
            tableBill = new JTable(modelTable);
            scrollPaneTableDisplay.setViewportView(tableBill);
            scrollPaneTableDisplay.setSize(tableBill.getSize());
            seeBillFrame.pack();
        });

        seeBillFrame.pack();
        seeBillFrame.setVisible(true);

    }

    private void createUIComponents() {
        String [] csvName = {"bill paid", "bill non paid"};
        comboBoxCSVFile = new JComboBox<>(csvName);
    }
}
