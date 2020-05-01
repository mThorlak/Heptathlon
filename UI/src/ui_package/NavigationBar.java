package ui_package;

import javax.swing.*;

public class NavigationBar {

    public NavigationBar(JFrame jFrame, JButton buttonGetterPage, JButton buttonBillPage, JButton buttonAdminPage) {

        buttonGetterPage.addActionListener(e -> {
            jFrame.dispose();
            try {
                new Getterpage();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        buttonBillPage.addActionListener(e -> {
            jFrame.dispose();
            try {
                new Billpage();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        buttonAdminPage.addActionListener(e -> {
            jFrame.dispose();
            try {
                new Adminpage();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }
}
