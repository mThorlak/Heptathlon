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


    public ControlPage() throws RemoteException, NotBoundException {
        JFrame controlFrame = new JFrame("Control page");
        GeneralFrameSettings generalFrameSettings = new GeneralFrameSettings(controlFrame);
        controlFrame.setContentPane(PanelMain);
        controlFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        controlFrame.setLocation(generalFrameSettings.getLocationX(), generalFrameSettings.getLocationY());
        controlFrame.pack();
        controlFrame.setVisible(true);

        MainClientShop clientShop = new MainClientShop();

        getAllArticleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clientShop.getQueryShopInterface().getAllArticle();
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
