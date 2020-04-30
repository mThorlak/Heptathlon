package ui_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client_package.MainClientShop;
import client_package.MainClientSiege;
import model_table.TableArticleShop;
import model_table.TableArticleSiege;

public class ControlPage extends Container {

    private JPanel panelMain;
    private JLabel labelTitle;
    private JPanel panelControl;
    private JPanel panelShop;
    private JPanel PanelSiege;
    private JButton buttonGetAllArticleShop;
    private JScrollPane JScrollContentPane;
    private JTable tableDisplay;
    private JLabel labelShop;
    private JLabel labelSiege;
    private JButton buttonGetAllArticleSiege;


    public ControlPage() throws Exception {
        JFrame controlFrame = new JFrame("Control page");
        GeneralFrameSettings generalFrameSettings = new GeneralFrameSettings(controlFrame);
        controlFrame.setContentPane(panelMain);
        controlFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        controlFrame.setLocation(generalFrameSettings.getLocationX(), generalFrameSettings.getLocationY());


        MainClientShop clientShop = new MainClientShop();
        MainClientSiege clientSiege = new MainClientSiege();

        buttonGetAllArticleShop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    TableArticleShop modelTable = new TableArticleShop(clientShop.getQueryShopInterface().getAllArticle());
                    tableDisplay = new JTable(modelTable);
                    JScrollContentPane.setViewportView(tableDisplay);
                    JScrollContentPane.setSize(tableDisplay.getSize());
                    controlFrame.pack();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        controlFrame.pack();
        controlFrame.setVisible(true);

        buttonGetAllArticleSiege.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    TableArticleSiege tableArticleSiege = new TableArticleSiege(clientSiege.getQuerySiegeInterface().getAllArticle());
                    tableDisplay = new JTable(tableArticleSiege);
                    JScrollContentPane.setViewportView(tableDisplay);
                    JScrollContentPane.setSize(tableDisplay.getSize());
                    controlFrame.pack();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    // place custom component creation code here
    private void createUIComponents() throws Exception {
    }
}
