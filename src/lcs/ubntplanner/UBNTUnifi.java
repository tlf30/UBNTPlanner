package lcs.ubntplanner;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Trevor Flynn
 */

public class UBNTUnifi extends UBNTDevice {

        public int range = 600;

        public UBNTUnifi(Point p) {
            super(p);
        }

        public UBNTUnifi() {

        }

        @Override
        public void paint0(Graphics g) {
            //System.out.println("Drawing Unifi");
            Color c = new Color(10, 100, 50, 100);
            g.setColor(c);
            g.fillOval((int) ((location.x * scale + xOffset) - ((range * keyScale * scale) / 2)), (int) ((location.y * scale + yOffset) - ((range * keyScale * scale) / 2)), (int) (range * keyScale * scale), (int) (range * keyScale * scale));
            

        }
         @Override
        public void paint1(Graphics g) {
            //System.out.println("Drawing Unifi");
            g.setColor(Color.MAGENTA);
            //System.out.println("Scale: " + scale);
            g.fillOval((int) (location.x * scale + xOffset) - 10, (int) (location.y * scale + yOffset) - 10, 20, 20);

        }
    }