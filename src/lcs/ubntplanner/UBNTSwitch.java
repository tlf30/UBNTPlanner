/* 
 * Copyright (C) 2015 Trevor Flynn
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package lcs.ubntplanner;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Trevor Flynn <trevorflynn@liquidcrystalstudios.com>
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
