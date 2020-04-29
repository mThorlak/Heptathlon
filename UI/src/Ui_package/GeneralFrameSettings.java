package Ui_package;

import javax.swing.*;
import java.awt.*;

public class GeneralFrameSettings {
    private final Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    private final int locationX;
    private final int locationY;

    public GeneralFrameSettings(JFrame jFrame) {
        this.locationX = this.dimension.width/2 - jFrame.getWidth()/2;
        this.locationY = this.dimension.height/2 - jFrame.getHeight()/2;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public int getLocationX() {
        return locationX;
    }

    public int getLocationY() {
        return locationY;
    }
}
