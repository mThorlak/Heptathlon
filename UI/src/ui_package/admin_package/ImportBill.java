package ui_package.admin_package;

import client_package.ClientSiege;
import rmi_general.BillCSVManager;
import ui_package.ui_general.GeneralFrameSettings;

import javax.swing.*;
import java.io.File;

public class ImportBill {
    private JPanel panelMain;
    private JButton buttonYes;
    private JButton buttonNo;
    private JLabel labelQuestion;
    private JLabel labelHeader;
    private JLabel labelState;
    private JCheckBox checkBoxBillNonPaid;
    private JCheckBox checkBoxBillPaid;

    public ImportBill() {
        JFrame importBill = new JFrame("Import bill page");
        GeneralFrameSettings generalFrameSettings = new GeneralFrameSettings(importBill);
        importBill.setContentPane(panelMain);
        importBill.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        importBill.setLocation(generalFrameSettings.getLocationX(), generalFrameSettings.getLocationY());

        buttonYes.addActionListener(e -> {
            try {
                ClientSiege clientSiege = new ClientSiege();
                if (checkBoxBillNonPaid.isSelected()) {
                    clientSiege.getQuerySiegeInterface().importCSVIntoDBSiege(false);
                    BillCSVManager csvManager = new BillCSVManager();
                    File file = new File(csvManager.BILL_PATH);
                    if (file.delete())
                        System.out.println("File bill.csv deleted");
                    else
                        System.out.println("File bill.csv not found");
                }
                if (checkBoxBillPaid.isSelected()) {
                    clientSiege.getQuerySiegeInterface().importCSVIntoDBSiege(true);
                    BillCSVManager csvManager = new BillCSVManager();
                    File file = new File(csvManager.BILL_PAID_PATH);
                    if (file.delete())
                        System.out.println("File bill_paid.csv deleted");
                    else
                        System.out.println("File bill_paid.csv not found");
                }
                if (!checkBoxBillPaid.isSelected() && !checkBoxBillNonPaid.isSelected())
                    labelState.setText("You have selected nothing");
            } catch (Exception remoteException) {
                labelState.setText("Error : bill with same data already imported or the bill selected does not exist");
                remoteException.printStackTrace();
            }
        });

        buttonNo.addActionListener(e -> {
            importBill.dispose();
        });

        importBill.pack();
        importBill.setVisible(true);
    }
}
