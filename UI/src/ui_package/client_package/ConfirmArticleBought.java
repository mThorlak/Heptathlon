package ui_package.client_package;

import rmi_siege.tables.Bill;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ConfirmArticleBought {
    private JTextPane textPane1;
    private JPanel panelMain;
    private JLabel jLabelHeader;

    public ConfirmArticleBought(Bill bill) {

        JFrame buyArticleFrame = new JFrame("Heptathlon");
        buyArticleFrame.setContentPane(panelMain);
        buyArticleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        textPane1.setText(bill.toString());

        buyArticleFrame.pack();
        buyArticleFrame.setVisible(true);
    }

    private void createUIComponents() throws IOException {
        BufferedImage myPicture = ImageIO.read(new File("UI/resources/clientPageHeader.png"));
        jLabelHeader = new JLabel(new ImageIcon(myPicture));
    }
}
