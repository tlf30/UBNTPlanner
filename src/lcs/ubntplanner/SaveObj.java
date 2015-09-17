package lcs.ubntplanner;


import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Trevor Flynn
 */

public class SaveObj implements Serializable {
        public ArrayList<UBNTDevice> devs;
        public ArrayList<Link> links;
        public String img;
        public double keyScale;
    }