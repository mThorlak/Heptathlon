package ui_package.admin_package;

import client_package.ClientSiege;
import ui_package.GeneralFrameSettings;

import javax.swing.*;

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
                if (checkBoxBillNonPaid.isSelected())
                    clientSiege.getQuerySiegeInterface().importCSVIntoDBSiege(false);
                if (checkBoxBillPaid.isSelected())
                    clientSiege.getQuerySiegeInterface().importCSVIntoDBSiege(true);
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
