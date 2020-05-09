package ui_package.client_package;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientPage {
    private JLabel jLabelLogo;
    private JPanel mainPanel;
    private JButton buttonBuyArticle;
    private JButton buttonPayBill;

    public ClientPage () {
        JFrame clientPage = new JFrame("Heptathlon");
        clientPage.setContentPane(mainPanel);
        clientPage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        buttonBuyArticle.addActionListener(e -> {
            try {
                new BuyArticle();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });


        buttonPayBill.addActionListener(e -> {
            try {
                new PayBill();
            } catch (RemoteException | NotBoundException remoteException) {
                remoteException.printStackTrace();
            }
        });

        clientPage.pack();
        clientPage.setVisible(true);
    }

    private void createUIComponents() throws IOException {
        BufferedImage myPicture = ImageIO.read(new File("UI/resources/clientPage.png"));
        jLabelLogo = new JLabel(new ImageIcon(myPicture));
    }
}
