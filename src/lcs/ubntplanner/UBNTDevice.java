package lcs.ubntplanner;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

/**
 *
 * @author Trevor Flynn
 */

public class UBNTDevice implements Cloneable, Serializable {

    private static final long serialVersionUID = 777001L;
    public int type = 0; //0 = AP, 1 = UNIFI, 2 = Switch
    public String name = "";
    public Point location = new Point();
    public double keyScale = 1;
    public double scale = 1;
    public double xOffset = 0;
    public double yOffset = 0;
    public int wiredConnections = 1;
    public int wirelessConnections = 0;

    public UBNTDevice(Point p) {
        location = p;
    }

    public UBNTDevice() {

    }

    public void paint0(Graphics g) {

    }

    public void paint1(Graphics g) {
        //System.out.println("Drawing Unifi");
        g.setColor(Color.black);
        g.fillRect((int) (location.x * scale + xOffset) - 10, (int) (location.y * scale + yOffset) - 10, 20, 20);

    }

    public UBNTDevice cloneDev() {
        try {
            return (UBNTDevice) clone();
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
