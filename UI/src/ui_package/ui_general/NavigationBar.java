package ui_package.ui_general;

import ui_package.admin_package.ArticleManager;
import ui_package.admin_package.Adminpage;
import ui_package.admin_package.BillMainPage;

import javax.swing.*;

public class NavigationBar {

    public NavigationBar(JFrame jFrame, JButton buttonGetterPage, JButton buttonBillPage, JButton buttonAdminPage) {

        buttonGetterPage.addActionListener(e -> {
            jFrame.dispose();
            try {
                new ArticleManager();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        buttonBillPage.addActionListener(e -> {
            jFrame.dispose();
            try {
                new BillMainPage();
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
