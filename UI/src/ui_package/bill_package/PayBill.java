package ui_package.bill_package;

import client_package.ClientSiege;
import rmi_general.BillCSVManager;
import rmi_siege.tables.Bill;
import ui_package.ui_general.GeneralFrameSettings;

import javax.swing.*;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class PayBill {
    private JPanel panelMain;
    private JTextArea textAreaBillDisplay;
    private JButton buttonFindBill;
    private JTextField textFieldFindBill;
    private JButton buttonPayBill;
    private JComboBox<String> comboBoxPayment;
    private JRadioButton radioButtonBillFromToday;


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
            Bill bill = null;
            if (radioButtonBillFromToday.isSelected()) {
                BillCSVManager csvManager = new BillCSVManager();
                List<String[]> billsCSV = csvManager.readLineByLine(csvManager.BILL_PATH);
                for (String[] billCSV : billsCSV) {
                    if (billCSV[0].equals(billsCSV.get(0)[0]))
                        continue;
                    else if (billCSV[1].equals(textFieldFindBill.getText())) {
                        bill = csvManager.convertLineInBill(billCSV);
                        break;
                    }
                }
            }
            else {
                try {
                    bill = clientSiege.getQuerySiegeInterface().getBillByID(textFieldFindBill.getText());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            if (bill != null) {
                textAreaBillDisplay.setText(bill.toString());
                textAreaBillDisplay.setLineWrap(true);
                textAreaBillDisplay.setWrapStyleWord(true);
                payBillFrame.pack();
            }
        });

        buttonPayBill.addActionListener(e -> {
            if (radioButtonBillFromToday.isSelected()) {
                BillCSVManager csvManager = new BillCSVManager();
                try {
                    csvManager.payBill(textFieldFindBill.getText());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            else {
                try {
                    clientSiege.getQuerySiegeInterface().updateBillIsPaid(textFieldFindBill.getText(), (String) comboBoxPayment.getSelectedItem());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        payBillFrame.pack();
        payBillFrame.setVisible(true);

    }
}
