package ui_package.admin_package;

import client_package.ClientShop;
import client_package.ClientSiege;
import ui_package.ui_general.GeneralFrameSettings;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class ImportPriceShop {
    private JPanel panelMain;
    private JComboBox<String> comboBoxShop;
    private JButton buttonValidate;
    private JLabel labelState;
    private JLabel jLabelHeader;
    private JButton buttonExit;

    public ImportPriceShop() throws RemoteException, NotBoundException {
        JFrame importPriceFrame = new JFrame("Heptathlon");
        importPriceFrame.setContentPane(panelMain);
        importPriceFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        labelState.setVisible(false);

        ClientSiege clientSiege = new ClientSiege();

        comboBoxShop.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                try {
                    List<String> shopSiegeList = clientSiege.getQuerySiegeInterface().getAllShop();
                    comboBoxShop.removeAllItems();
                    for (String item : shopSiegeList)
                        comboBoxShop.addItem(item);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        buttonValidate.addActionListener(e -> {
            try {
                ClientShop clientShop = new ClientShop();
                clientShop.getQueryShopInterface().importPriceFromSiegeDB((String) comboBoxShop.getSelectedItem());
                labelState.setText("Price imported");
                labelState.setForeground(Color.GREEN);
                labelState.setVisible(true);
            } catch (Exception remoteException) {
                labelState.setText("This shop does not exist");
                labelState.setForeground(Color.RED);
                labelState.setVisible(true);
                remoteException.printStackTrace();
            }
            importPriceFrame.pack();
        });

        buttonExit.addActionListener(e -> importPriceFrame.dispose());

        importPriceFrame.pack();
        importPriceFrame.setVisible(true);

    }

    private void createUIComponents() throws Exception {
        
        BufferedImage myPicture = ImageIO.read(new File("UI/resources/adminPageHeader.png"));
        jLabelHeader = new JLabel(new ImageIcon(myPicture));

        ClientSiege clientSiege = new ClientSiege();
        List<String> shopSiegeList = clientSiege.getQuerySiegeInterface().getAllShop();
        String[] shopSiegeArray = new String[shopSiegeList.size()];
        shopSiegeList.toArray(shopSiegeArray);
        comboBoxShop = new JComboBox<>(shopSiegeArray);
    }
}
