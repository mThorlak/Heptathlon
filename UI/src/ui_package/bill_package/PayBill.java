package ui_package.bill_package;

import client_package.ClientSiege;
import rmi_siege.tables.Bill;
import ui_package.GeneralFrameSettings;

import javax.swing.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class PayBill {
    private JPanel panelMain;
    private JTextArea textAreaBillDisplay;
    private JButton buttonFindBill;
    private JTextField textFieldFindBill;
    private JButton buttonPayBill;
    private JComboBox<String> comboBoxPayment;


    public PayBill() throws RemoteException, NotBoundException {

        // Frame settings
        JFrame payBillFrame = new JFrame("Pay bill");
        GeneralFrameSettings generalFrameSettings = new GeneralFrameSettings(payBillFrame);
        payBillFrame.setContentPane(panelMain);
        payBillFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        payBillFrame.setLocation(generalFrameSettings.getLocationX(), generalFrameSettings.getLocationY());

        ClientSiege clientSiege = new ClientSiege();

        String[] paymentMethod = {"Cash", "Blue card", "Bitcoin", "Kidney"};
        for (String payment : paymentMethod)
            comboBoxPayment.addItem(payment);

        buttonFindBill.addActionListener(e -> {
            try {
                Bill bill = clientSiege.getQuerySiegeInterface().getBillByID(textFieldFindBill.getText());
                textAreaBillDisplay.setText(bill.toString());
                textAreaBillDisplay.setLineWrap(true);
                textAreaBillDisplay.setWrapStyleWord(true);
                payBillFrame.pack();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        buttonPayBill.addActionListener(e -> {
            try {
                clientSiege.getQuerySiegeInterface().updateBillIsPaid(textFieldFindBill.getText(), (String)comboBoxPayment.getSelectedItem());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        payBillFrame.pack();
        payBillFrame.setVisible(true);

    }
}
