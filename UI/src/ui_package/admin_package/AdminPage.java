package ui_package.admin_package;

import ui_package.client_package.BuyArticle;
import ui_package.client_package.PayBill;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class AdminPage {
    private JLabel jLabelLogo;
    private JButton buttonArticleManager;
    private JButton buttonBillManager;
    private JPanel panelMain;

    public AdminPage () {
        JFrame clientPage = new JFrame("Heptathlon");
        clientPage.setContentPane(panelMain);
        clientPage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        buttonArticleManager.addActionListener(e -> {
            try {
                new BuyArticle();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });


        buttonBillManager.addActionListener(e -> {
            try {
                new BillManager();
            } catch (RemoteException | NotBoundException remoteException) {
                remoteException.printStackTrace();
            }
        });

        clientPage.pack();
        clientPage.setVisible(true);
    }

    private void createUIComponents() throws IOException {
        BufferedImage myPicture = ImageIO.read(new File("UI/resources/adminPage.png"));
        jLabelLogo = new JLabel(new ImageIcon(myPicture));
    }
}
