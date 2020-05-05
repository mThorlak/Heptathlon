package ui_package.bill_package;

import client_package.ClientSiege;
import model_table.TableBillSiege;
import rmi_siege.tables.Bill;
import ui_package.GeneralFrameSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class SeeBill {
    private JPanel panelMain;
    private JScrollPane scrollPaneTableDisplay;
    private JTable tableBill;
    private JPanel panelSort;
    private JTextField textFieldGetButtonByID;
    private JButton buttonGetBillByID;
    private JButton buttonBillByDateAndShop;
    private JTextField textFieldDate;
    private JTextField textFieldShop;

    public SeeBill() throws RemoteException, NotBoundException {
        // Frame settings
        JFrame seeBillFrame = new JFrame("Control page");
        GeneralFrameSettings generalFrameSettings = new GeneralFrameSettings(seeBillFrame);
        seeBillFrame.setContentPane(panelMain);
        seeBillFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        seeBillFrame.setLocation(generalFrameSettings.getLocationX(), generalFrameSettings.getLocationY());

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
            }

            else if (textFieldDate.getText().isEmpty() && !textFieldShop.getText().isEmpty()) {
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
            }

            else if (!textFieldDate.getText().isEmpty() && !textFieldShop.getText().isEmpty()) {
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

        seeBillFrame.pack();
        seeBillFrame.setVisible(true);
    }
}
