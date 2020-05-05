package ui_package.bill_package;

import ui_package.GeneralFrameSettings;
import ui_package.NavigationBar;

import javax.swing.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class BillMainPage {
    private JButton buttonBillPage;
    private JButton buttonGetterPage;
    private JButton buttonAdminPage;
    private JLabel LabelHeader;
    private JPanel panelMain;
    private JButton buttonSeeBill;
    private JButton buttonBuyArticle;
    private JButton buttonPayBill;

    public BillMainPage() {
        // Frame settings
        JFrame controlFrame = new JFrame("Control page");
        GeneralFrameSettings generalFrameSettings = new GeneralFrameSettings(controlFrame);
        controlFrame.setContentPane(panelMain);
        controlFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        controlFrame.setLocation(generalFrameSettings.getLocationX(), generalFrameSettings.getLocationY());

        // Navigation bar
        new NavigationBar(controlFrame, buttonGetterPage, buttonBillPage, buttonAdminPage);

        buttonBuyArticle.addActionListener(e -> {
            try {
                new BuyArticle();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        buttonSeeBill.addActionListener(e -> {
            try {
                new SeeBill();
            } catch (RemoteException | NotBoundException remoteException) {
                remoteException.printStackTrace();
            }
        });
        buttonPayBill.addActionListener(e -> {
            new PayBill();
        });

        controlFrame.pack();
        controlFrame.setVisible(true);
    }
}
