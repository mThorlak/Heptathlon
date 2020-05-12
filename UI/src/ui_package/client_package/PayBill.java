package ui_package.client_package;

import client_package.ClientSiege;
import rmi_general.BillCSVManager;
import rmi_siege.tables.Bill;
import ui_package.ui_general.GeneralFrameSettings;
import ui_package.ui_general.HintTextField;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class PayBill {
    private JPanel panelMain;
    private JTextPane textPaneBillDisplay;
    private JButton buttonFindBill;
    private JTextField textFieldFindBill;
    private JButton buttonPayBill;
    private JComboBox<String> comboBoxPayment;
    private JRadioButton radioButtonBillFromToday;
    private JTextPane textPaneStateBill;
    private JLabel jLabelHeader;
    private final String[] paymentMethod = {"Cash", "Blue card", "Bitcoin", "Kidney"};


    public PayBill() throws RemoteException, NotBoundException {

        // Frame settings
        JFrame payBillFrame = new JFrame("Pay bill");
        GeneralFrameSettings generalFrameSettings = new GeneralFrameSettings(payBillFrame);
        payBillFrame.setContentPane(panelMain);
        payBillFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        payBillFrame.setLocation(generalFrameSettings.getLocationX(), generalFrameSettings.getLocationY());
        textPaneStateBill.setVisible(false);

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
            } else {
                try {
                    bill = clientSiege.getQuerySiegeInterface().getBillByID(textFieldFindBill.getText());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            if (bill != null) {
                textPaneBillDisplay.setText(bill.toString());
                payBillFrame.pack();
            }
        });

        buttonPayBill.addActionListener(e -> {
            if (radioButtonBillFromToday.isSelected()) {
                BillCSVManager csvManager = new BillCSVManager();
                Bill bill = null;
                try {
                    csvManager = new BillCSVManager();
                    List<String[]> billsCSV = csvManager.readLineByLine(csvManager.BILL_PATH);
                    for (String[] billCSV : billsCSV) {
                        if (billCSV[0].equals(billsCSV.get(0)[0]))
                            continue;
                        else if (billCSV[1].equals(textFieldFindBill.getText())) {
                            bill = csvManager.convertLineInBill(billCSV);
                            break;
                        }
                    }

                    if (bill != null) {
                        csvManager.payBill(textFieldFindBill.getText(), (String) comboBoxPayment.getSelectedItem());
                        textPaneStateBill.setText("Bill is paid, thanks !");
                        textPaneStateBill.setForeground(Color.GREEN);
                    }
                    else {
                        textPaneStateBill.setText("The bill is already paid or does not exist");
                        textPaneStateBill.setForeground(Color.RED);
                    }
                    textPaneStateBill.setVisible(true);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } else {
                try {
                    Bill bill = clientSiege.getQuerySiegeInterface().getBillByID(textFieldFindBill.getText());
                    if (bill.isPaid()) {
                        textPaneStateBill.setText("The bill is already paid or does not exist");
                        textPaneStateBill.setForeground(Color.RED);
                    } else {
                        clientSiege.getQuerySiegeInterface().updateBillIsPaid(textFieldFindBill.getText(), (String) comboBoxPayment.getSelectedItem());
                        textPaneStateBill.setText("Bill is paid, thanks !");
                        textPaneStateBill.setForeground(Color.GREEN);
                    }
                    textPaneStateBill.setVisible(true);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            payBillFrame.pack();
        });

        payBillFrame.pack();
        payBillFrame.setVisible(true);

    }

    private void createUIComponents() throws IOException {
        BufferedImage myPicture = ImageIO.read(new File("UI/resources/clientPageHeader.png"));
        jLabelHeader = new JLabel(new ImageIcon(myPicture));

        textFieldFindBill = new HintTextField("Bill ID");
    }
}
