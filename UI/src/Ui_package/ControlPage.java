package Ui_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import client_package.MainClientShop;

public class ControlPage extends Container {

    private JPanel PanelMain;
    private JLabel LabelTitle;
    private JLabel LabelDisplayer;
    private JPanel PanelControl;
    private JPanel PanelShop;
    private JPanel PanelSiege;
    private JButton getAllArticleButton;
    private JLabel LabelShop;

    public static void main(String[] args) throws RemoteException, NotBoundException {
        JFrame controlPage = new JFrame("Control page");
        controlPage.setContentPane(new ControlPage().PanelMain);
        controlPage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        controlPage.pack();
        controlPage.setVisible(true);
    }

    public ControlPage() throws RemoteException, NotBoundException {
        MainClientShop clientShop = new MainClientShop();
        getAllArticleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clientShop.getQueryShopInterface().getArticleByFamily("Longboard");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
