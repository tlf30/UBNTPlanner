package lcs.ubntplanner;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Trevor Flynn
 */

public class UBNTSwitch extends UBNTDevice {

    public UBNTSwitch() {

    }

    public UBNTSwitch(Point p) {
        super(p);
    }

    @Override
    public void paint1(Graphics g) {
        //System.out.println("Drawing Unifi");
        g.setColor(Color.orange);
        g.fillRect((int) (location.x * scale + xOffset) - 10, (int) (location.y * scale + yOffset) - 10, 20, 20);
    }
}
