![Heptathlon](https://github.com/mThorlak/Heptathlon/blob/master/UI/resources/logo.png)

## Installation

### Intellij installation and configuration

* The best way to try out the project is to use the [Intellij IDE](https://www.jetbrains.com/help/idea/installation-guide.html).

* Clone the project
```bash
git clone https://github.com/mThorlak/Heptathlon.git
```

* [Open the project](https://www.jetbrains.com/help/webstorm/opening-reopening-and-closing-projects.html) with Intellij

* (Optional) Import settings (available in Heptathlon/InstallProject/settings_Intellij.zip) and then reboot Intellij

* [Download and add jdk](https://www.jetbrains.com/help/idea/sdk.html) open jdv 14.XX to run the project

* In RMI/src/rmi_general/Database, change "user" and "password" (line 19 and 20) with your own ID to connect to your phpMyAdmin account

### Database configuration

* Install [phpMyAdmin](https://docs.phpmyadmin.net/fr/latest/setup.html)

* Connect to it : http://localhost/phpmyadmin

* Import Heptathlon_Shop.sql and Heptathlon_Siege.sql (available into the project : Heptathlon/InstallProject/DB)

* And it's done !

## How to test ?

* For testing the project, choose "Run project" in [run configuration](https://www.jetbrains.com/help/go/creating-and-editing-run-debug-configurations.html).

* If your database is empty you can use "Database Filler" to add some data for testing, be sure to run "Run servers" before.

## License
[MIT](https://choosealicense.com/licenses/mit/)
