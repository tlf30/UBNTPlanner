
package lcs.ubntplanner;

import java.io.Serializable;

/**
 *
 * @author Trevor Flynn
 */
public class Link implements Serializable {
    public boolean wired = true;
    public UBNTDevice a;
    public UBNTDevice b;
}
