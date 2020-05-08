package ui_package;

import ui_package.client_package.ClientPage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Homepage {
    private JPanel MainPanel;
    private JButton EnterButton;
    private JLabel jLabelLogo;
    private JButton buttonClientPage;
    private JButton buttonAdminPage;
    private JPanel panelMenu;
    private JPanel panelHomepage;
    private JPanel panelLogo;
    private BufferedImage image;

    public static void main(String[] args) {
        new Homepage();
    }

    public Homepage() {
        JFrame homepage = new JFrame("Heptathlon");
        homepage.setContentPane(MainPanel);
        homepage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panelHomepage.setVisible(true);
        panelMenu.setVisible(false);

        EnterButton.addActionListener(e -> {
                panelHomepage.setVisible(false);
                panelMenu.setVisible(true);
        });

        buttonClientPage.addActionListener(e -> {
            new ClientPage();
            homepage.dispose();
        });

        buttonAdminPage.addActionListener(e -> {

        });

        homepage.pack();
        homepage.setVisible(true);
    }

    private void createUIComponents() throws IOException {
        BufferedImage myPicture = ImageIO.read(new File("UI/resources/logo.png"));
        jLabelLogo = new JLabel(new ImageIcon(myPicture));
    }
}
