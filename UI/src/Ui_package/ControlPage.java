package Ui_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client_package.MainClientShop;

public class ControlPage extends Container {

    private JPanel PanelMain;
    private JLabel LabelTitle;
    private JPanel PanelControl;
    private JPanel PanelShop;
    private JPanel PanelSiege;
    private JButton getAllArticleButton;
    private JScrollPane JScrollContentPane;
    private JTable TableDisplay;
    private JLabel LabelShop;


    public ControlPage() throws Exception {
        JFrame controlFrame = new JFrame("Control page");
        GeneralFrameSettings generalFrameSettings = new GeneralFrameSettings(controlFrame);
        controlFrame.setContentPane(PanelMain);
        controlFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        controlFrame.setLocation(generalFrameSettings.getLocationX(), generalFrameSettings.getLocationY());


        MainClientShop clientShop = new MainClientShop();

        getAllArticleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    TableArticleModel modelTable = new TableArticleModel(clientShop.getQueryShopInterface().getAllArticle());
                    TableDisplay = new JTable(modelTable);
                    JScrollContentPane.setViewportView(TableDisplay);
                    JScrollContentPane.setSize(TableDisplay.getSize());
                    controlFrame.pack();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        controlFrame.pack();
        controlFrame.setVisible(true);

    }

    // place custom component creation code here
    private void createUIComponents() throws Exception {
    }
}
