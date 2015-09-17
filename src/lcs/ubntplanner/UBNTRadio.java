package lcs.ubntplanner;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Trevor Flynn
 */

public class UBNTRadio extends UBNTDevice {

    public UBNTRadio() {
        wirelessConnections = 10;
    }

    public UBNTRadio(Point p) {
        super(p);
    }

    @Override
    public void paint1(Graphics g) {
        //System.out.println("Drawing ");
        g.setColor(Color.GREEN);
        //System.out.println("Scale: " + scale);
        g.fillOval((int) (location.x * scale + xOffset) - 10, (int) (location.y * scale + yOffset) - 10, 20, 20);

    }

}
