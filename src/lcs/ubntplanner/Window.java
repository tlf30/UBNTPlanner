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
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Vector;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javax.imageio.ImageIO;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Trevor Flynn <trevorflynn@liquidcrystalstudios.com>
 */
public class Window extends javax.swing.JFrame {

    public int selectedDevice = -1;
    public boolean link = false;
    public boolean unlink = false;
    public boolean linkWireless = false;
    public boolean move = false;
    public ArrayList<UBNTDevice> devices = new ArrayList<>(0);
    public ArrayList<Link> links = new ArrayList<>(0);
    public BufferedImage image = null;
    public double scale = 1;
    public double keyScale = 0;
    public String imagePath;
    public double imgX = 0;
    public double imgY = 0;
    public UBNTPanel panel = new UBNTPanel();
    public Vector<UBNTDevice> devs = new Vector<>();
    public ComboBoxModel<UBNTDevice> devModel = new DefaultComboBoxModel<>(devs);
    public boolean setScale = false;
    public Point a;
    public Point b;
    public JFrame frame;
    public boolean addDevice = false;
    public FileFilter fileFilter = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            return true;
        }

        @Override
        public String getDescription() {
            return "PNG File Filter";
        }

    };

    /**
     * Creates new form Window
     */
    public Window() {
        frame = this;
        //Load Devices
        //Unifi
        UBNTUnifi uap = new UBNTUnifi();
        uap.name = "UAP";
        uap.wiredConnections = 2;
        uap.range = 400;
        UBNTUnifi uap_lr = new UBNTUnifi();
        uap_lr.name = "UAP-LR";
        uap_lr.wiredConnections = 2;
        uap_lr.range = 600;
        UBNTUnifi uap_pro = new UBNTUnifi();
        uap_pro.name = "UAP-PRO";
        uap_pro.wiredConnections = 2;
        uap_pro.range = 400;
        UBNTUnifi uap_ac = new UBNTUnifi();
        uap_ac.name = "UAP-AC";
        uap_ac.wiredConnections = 2;
        uap_ac.range = 400;
        UBNTUnifi uap_o5 = new UBNTUnifi();
        uap_o5.name = "UAP-Outdoors5";
        uap_o5.wiredConnections = 2;
        UBNTUnifi uap_op = new UBNTUnifi();
        uap_op.name = "UAP-Outdoors+";
        uap_op.wiredConnections = 2;
        UBNTUnifi uap_ac_o = new UBNTUnifi();
        uap_ac_o.name = "UAP-AC-Outdoors";
        uap_ac_o.wiredConnections = 2;
        UBNTDevice usg = new UBNTDevice();
        usg.wiredConnections = 3;
        usg.name = "USG";
        UBNTSwitch us_24 = new UBNTSwitch();
        us_24.wiredConnections = 24;
        us_24.name = "US_24";
        UBNTSwitch us_48 = new UBNTSwitch();
        us_48.wiredConnections = 24;
        us_48.name = "US_48";
        //Tough Swithc
        UBNTSwitch ts_5 = new UBNTSwitch();
        ts_5.wiredConnections = 5;
        ts_5.name = "TS-5-POE";
        UBNTSwitch ts_8 = new UBNTSwitch();
        ts_8.wiredConnections = 8;
        ts_8.name = "TS-8-POE";
        //AirMax AC
        UBNTRadio r5ac_ptmp = new UBNTRadio();
        r5ac_ptmp.name = "R5AC-PTMP";
        UBNTRadio r5ac_ptp = new UBNTRadio();
        r5ac_ptp.name = "R5AC-PTP";
        UBNTRadio r5ac_lite = new UBNTRadio();
        r5ac_lite.name = "R5AC-Lite";
        UBNTRadio pbe_5ac_300_iso = new UBNTRadio();
        pbe_5ac_300_iso.name = "PBE-5AC-300_ISO";
        UBNTRadio pbe_5ac_400_iso = new UBNTRadio();
        pbe_5ac_400_iso.name = "PBE-5AC-400_ISO";
        UBNTRadio pbe_5ac_500 = new UBNTRadio();
        pbe_5ac_500.name = "PBE-5AC-500";
        UBNTRadio pbe_5ac_620 = new UBNTRadio();
        pbe_5ac_620.name = "PBE-5AC-620";
        UBNTRadio nbe_5ac_19 = new UBNTRadio();
        nbe_5ac_19.name = "NBE-5AC-19";
        //AirMax
        UBNTRadio agy = new UBNTRadio();
        agy.name = "AirGateway";
        agy.wiredConnections = 2;
        UBNTRadio agy_lr = new UBNTRadio();
        agy_lr.name = "AirGateway-LR";
        agy_lr.wiredConnections = 2;
        UBNTRadio agy_pro = new UBNTRadio();
        agy_pro.name = "AirGateway-PRO";
        agy_pro.wiredConnections = 2;
        UBNTRadio ag_hp_2g_16 = new UBNTRadio();
        ag_hp_2g_16.name = "AG-HP-2G-16";
        UBNTRadio ag_hp_2g_20 = new UBNTRadio();
        ag_hp_2g_20.name = "AG-HP-2G-20";
        UBNTRadio ag_hp_5g_23 = new UBNTRadio();
        ag_hp_5g_23.name = "AG-HP-5G-23";
        UBNTRadio ag_hp_5g_27 = new UBNTRadio();
        ag_hp_5g_27.name = "AG-HP-5G-27";
        UBNTRadio b_m2_hp = new UBNTRadio();
        b_m2_hp.name = "B-M2-HP";
        UBNTRadio b_m2_ti = new UBNTRadio();
        b_m2_ti.name = "B-M2-Ti";
        UBNTRadio b_m5_hp = new UBNTRadio();
        b_m5_hp.name = "B-M5-HP";
        UBNTRadio b_m5_ti = new UBNTRadio();
        b_m5_ti.name = "B-M5-Ti";
        //
        devs.add(uap);
        devs.add(uap_lr);
        devs.add(uap_pro);
        devs.add(uap_ac);
        devs.add(uap_o5);
        devs.add(uap_op);
        devs.add(uap_ac_o);
        devs.add(usg);
        devs.add(us_24);
        devs.add(us_48);
        devs.add(r5ac_ptmp);
        devs.add(r5ac_ptp);
        devs.add(r5ac_lite);
        devs.add(pbe_5ac_300_iso);
        devs.add(pbe_5ac_400_iso);
        devs.add(pbe_5ac_500);
        devs.add(pbe_5ac_620);
        devs.add(nbe_5ac_19);
        devs.add(ts_5);
        devs.add(ts_8);
        //Build GUI
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        ubnts = new javax.swing.JComboBox();
        add = new javax.swing.JButton();
        selectImage = new javax.swing.JButton();
        zin = new javax.swing.JButton();
        zout = new javax.swing.JButton();
        save = new javax.swing.JButton();
        load = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane(panel);
        jButton1 = new javax.swing.JButton();

        jFileChooser1.setFileFilter(fileFilter);
        jFileChooser1.setFileSelectionMode(javax.swing.JFileChooser.FILES_AND_DIRECTORIES);

        jMenuItem6.setText("jMenuItem6");
        jMenuItem6.setEnabled(false);
        jPopupMenu1.add(jMenuItem6);

        jMenuItem8.setEnabled(false);
        jPopupMenu1.add(jMenuItem8);

        jMenuItem1.setText("Set Scale");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        jMenuItem2.setText("Move");
        jMenuItem2.setEnabled(false);
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem2);

        jMenuItem3.setText("Delete");
        jMenuItem3.setEnabled(false);
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem3);

        jMenuItem4.setText("Add Wired Connection");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem4);

        jMenuItem5.setText("Add Wireless Connection");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem5);

        jMenuItem7.setText("Unlink");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem7);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ubnts.setModel(devModel);

        add.setText("Add");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        selectImage.setText("Select Image");
        selectImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectImageActionPerformed(evt);
            }
        });

        zin.setText("Zoom In");
        zin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zinActionPerformed(evt);
            }
        });

        zout.setText("Zoom Out");
        zout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoutActionPerformed(evt);
            }
        });

        save.setText("Save");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });

        load.setText("Load");
        load.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadActionPerformed(evt);
            }
        });

        jButton1.setText("About");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ubnts, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(add)
                        .addGap(18, 18, 18)
                        .addComponent(zin)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zout)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(load)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(save)
                        .addGap(18, 18, 18)
                        .addComponent(selectImage)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ubnts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(add)
                    .addComponent(selectImage)
                    .addComponent(zin)
                    .addComponent(zout)
                    .addComponent(save)
                    .addComponent(load)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        addDevice = true;
    }//GEN-LAST:event_addActionPerformed

    private void zinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zinActionPerformed
        panel.setScale(scale + 0.5);
    }//GEN-LAST:event_zinActionPerformed

    private void zoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoutActionPerformed
        panel.setScale(scale - 0.5);
    }//GEN-LAST:event_zoutActionPerformed

    private void loadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadActionPerformed
        ObjectInputStream oos = null;

        try {
            String name = JOptionPane.showInputDialog("Enter name", JOptionPane.INFORMATION_MESSAGE);
            FileInputStream fout = new FileInputStream(name + ".ups");
            oos = new ObjectInputStream(fout);
            SaveObj s = (SaveObj) oos.readObject();
            devices = s.devs;
            keyScale = s.keyScale;
            image = ImageIO.read(new File(s.img));
            imagePath = s.img;
            links = s.links;
            panel.setScale(1);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                oos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_loadActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        ObjectOutputStream oos = null;
        SaveObj saveobj = new SaveObj();
        saveobj.devs = devices;
        saveobj.img = imagePath;
        saveobj.keyScale = keyScale;
        saveobj.links = links;
        try {
            String name = JOptionPane.showInputDialog("Enter name", JOptionPane.INFORMATION_MESSAGE);
            FileOutputStream fout = new FileOutputStream(name + ".ups");
            oos = new ObjectOutputStream(fout);
            oos.writeObject(saveobj);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                oos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_saveActionPerformed

    private void selectImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectImageActionPerformed
        jFileChooser1.showDialog(this, "Open Img");
        try {
            if (jFileChooser1.getSelectedFile().getName().endsWith(".png")) {
                image = ImageIO.read(jFileChooser1.getSelectedFile());
                imagePath = jFileChooser1.getSelectedFile().getPath();
                panel.setScale(1);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_selectImageActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        setScale = true;
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        move = true;
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        if (selectedDevice > -1) {
            for (int i = 0; i < links.size(); i++) {
                if (links.get(i).a == devices.get(selectedDevice) || links.get(i).b == devices.get(selectedDevice)) {
                    links.remove(i);
                    i--;
                }
            }
            devices.remove(selectedDevice);
            repaint();
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        //Wired
        link = true;
        System.out.println("Ready for link");
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        //Wireless
        link = true;
        linkWireless = true;
        System.out.println("Ready for link");
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        unlink = true;
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        String msg
                = "UBNT Planner by Trevor Flynn (tlf30)"
                + "\ntlfalaska@hotmail.com"
                + "\n"
                + "\nTrevor Flynn"
                + "\nPO Box 623"
                + "\nHomer, AK, 99603"
                + "\n"
                + "\nCopyright (C) 2015 Trevor Flynn\n"
                + " *\n"
                + " * This program is free software; you can redistribute it and/or\n"
                + " * modify it under the terms of the GNU General Public License\n"
                + " * as published by the Free Software Foundation; either version 2\n"
                + " * of the License, or (at your option) any later version.\n"
                + " *\n"
                + " * This program is distributed in the hope that it will be useful,\n"
                + " * but WITHOUT ANY WARRANTY; without even the implied warranty of\n"
                + " * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n"
                + " * GNU General Public License for more details.\n"
                + " *\n"
                + " * You should have received a copy of the GNU General Public License\n"
                + " * along with this program; if not, write to the Free Software\n"
                + " * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA."
                + "\n"
                + "\n"
                + "GNU GENERAL PUBLIC LICENSE\n"
                + "                       Version 2, June 1991\n"
                + "\n"
                + " Copyright (C) 1989, 1991 Free Software Foundation, Inc., <http://fsf.org/>\n"
                + " 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA\n"
                + " Everyone is permitted to copy and distribute verbatim copies\n"
                + " of this license document, but changing it is not allowed.\n"
                + "\n"
                + "                            Preamble\n"
                + "\n"
                + "  The licenses for most software are designed to take away your\n"
                + "freedom to share and change it.  By contrast, the GNU General Public\n"
                + "License is intended to guarantee your freedom to share and change free\n"
                + "software--to make sure the software is free for all its users.  This\n"
                + "General Public License applies to most of the Free Software\n"
                + "Foundation's software and to any other program whose authors commit to\n"
                + "using it.  (Some other Free Software Foundation software is covered by\n"
                + "the GNU Lesser General Public License instead.)  You can apply it to\n"
                + "your programs, too.\n"
                + "\n"
                + "  When we speak of free software, we are referring to freedom, not\n"
                + "price.  Our General Public Licenses are designed to make sure that you\n"
                + "have the freedom to distribute copies of free software (and charge for\n"
                + "this service if you wish), that you receive source code or can get it\n"
                + "if you want it, that you can change the software or use pieces of it\n"
                + "in new free programs; and that you know you can do these things.\n"
                + "\n"
                + "  To protect your rights, we need to make restrictions that forbid\n"
                + "anyone to deny you these rights or to ask you to surrender the rights.\n"
                + "These restrictions translate to certain responsibilities for you if you\n"
                + "distribute copies of the software, or if you modify it.\n"
                + "\n"
                + "  For example, if you distribute copies of such a program, whether\n"
                + "gratis or for a fee, you must give the recipients all the rights that\n"
                + "you have.  You must make sure that they, too, receive or can get the\n"
                + "source code.  And you must show them these terms so they know their\n"
                + "rights.\n"
                + "\n"
                + "  We protect your rights with two steps: (1) copyright the software, and\n"
                + "(2) offer you this license which gives you legal permission to copy,\n"
                + "distribute and/or modify the software.\n"
                + "\n"
                + "  Also, for each author's protection and ours, we want to make certain\n"
                + "that everyone understands that there is no warranty for this free\n"
                + "software.  If the software is modified by someone else and passed on, we\n"
                + "want its recipients to know that what they have is not the original, so\n"
                + "that any problems introduced by others will not reflect on the original\n"
                + "authors' reputations.\n"
                + "\n"
                + "  Finally, any free program is threatened constantly by software\n"
                + "patents.  We wish to avoid the danger that redistributors of a free\n"
                + "program will individually obtain patent licenses, in effect making the\n"
                + "program proprietary.  To prevent this, we have made it clear that any\n"
                + "patent must be licensed for everyone's free use or not licensed at all.\n"
                + "\n"
                + "  The precise terms and conditions for copying, distribution and\n"
                + "modification follow.\n"
                + "\n"
                + "                    GNU GENERAL PUBLIC LICENSE\n"
                + "   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION\n"
                + "\n"
                + "  0. This License applies to any program or other work which contains\n"
                + "a notice placed by the copyright holder saying it may be distributed\n"
                + "under the terms of this General Public License.  The \"Program\", below,\n"
                + "refers to any such program or work, and a \"work based on the Program\"\n"
                + "means either the Program or any derivative work under copyright law:\n"
                + "that is to say, a work containing the Program or a portion of it,\n"
                + "either verbatim or with modifications and/or translated into another\n"
                + "language.  (Hereinafter, translation is included without limitation in\n"
                + "the term \"modification\".)  Each licensee is addressed as \"you\".\n"
                + "\n"
                + "Activities other than copying, distribution and modification are not\n"
                + "covered by this License; they are outside its scope.  The act of\n"
                + "running the Program is not restricted, and the output from the Program\n"
                + "is covered only if its contents constitute a work based on the\n"
                + "Program (independent of having been made by running the Program).\n"
                + "Whether that is true depends on what the Program does.\n"
                + "\n"
                + "  1. You may copy and distribute verbatim copies of the Program's\n"
                + "source code as you receive it, in any medium, provided that you\n"
                + "conspicuously and appropriately publish on each copy an appropriate\n"
                + "copyright notice and disclaimer of warranty; keep intact all the\n"
                + "notices that refer to this License and to the absence of any warranty;\n"
                + "and give any other recipients of the Program a copy of this License\n"
                + "along with the Program.\n"
                + "\n"
                + "You may charge a fee for the physical act of transferring a copy, and\n"
                + "you may at your option offer warranty protection in exchange for a fee.\n"
                + "\n"
                + "  2. You may modify your copy or copies of the Program or any portion\n"
                + "of it, thus forming a work based on the Program, and copy and\n"
                + "distribute such modifications or work under the terms of Section 1\n"
                + "above, provided that you also meet all of these conditions:\n"
                + "\n"
                + "    a) You must cause the modified files to carry prominent notices\n"
                + "    stating that you changed the files and the date of any change.\n"
                + "\n"
                + "    b) You must cause any work that you distribute or publish, that in\n"
                + "    whole or in part contains or is derived from the Program or any\n"
                + "    part thereof, to be licensed as a whole at no charge to all third\n"
                + "    parties under the terms of this License.\n"
                + "\n"
                + "    c) If the modified program normally reads commands interactively\n"
                + "    when run, you must cause it, when started running for such\n"
                + "    interactive use in the most ordinary way, to print or display an\n"
                + "    announcement including an appropriate copyright notice and a\n"
                + "    notice that there is no warranty (or else, saying that you provide\n"
                + "    a warranty) and that users may redistribute the program under\n"
                + "    these conditions, and telling the user how to view a copy of this\n"
                + "    License.  (Exception: if the Program itself is interactive but\n"
                + "    does not normally print such an announcement, your work based on\n"
                + "    the Program is not required to print an announcement.)\n"
                + "\n"
                + "These requirements apply to the modified work as a whole.  If\n"
                + "identifiable sections of that work are not derived from the Program,\n"
                + "and can be reasonably considered independent and separate works in\n"
                + "themselves, then this License, and its terms, do not apply to those\n"
                + "sections when you distribute them as separate works.  But when you\n"
                + "distribute the same sections as part of a whole which is a work based\n"
                + "on the Program, the distribution of the whole must be on the terms of\n"
                + "this License, whose permissions for other licensees extend to the\n"
                + "entire whole, and thus to each and every part regardless of who wrote it.\n"
                + "\n"
                + "Thus, it is not the intent of this section to claim rights or contest\n"
                + "your rights to work written entirely by you; rather, the intent is to\n"
                + "exercise the right to control the distribution of derivative or\n"
                + "collective works based on the Program.\n"
                + "\n"
                + "In addition, mere aggregation of another work not based on the Program\n"
                + "with the Program (or with a work based on the Program) on a volume of\n"
                + "a storage or distribution medium does not bring the other work under\n"
                + "the scope of this License.\n"
                + "\n"
                + "  3. You may copy and distribute the Program (or a work based on it,\n"
                + "under Section 2) in object code or executable form under the terms of\n"
                + "Sections 1 and 2 above provided that you also do one of the following:\n"
                + "\n"
                + "    a) Accompany it with the complete corresponding machine-readable\n"
                + "    source code, which must be distributed under the terms of Sections\n"
                + "    1 and 2 above on a medium customarily used for software interchange; or,\n"
                + "\n"
                + "    b) Accompany it with a written offer, valid for at least three\n"
                + "    years, to give any third party, for a charge no more than your\n"
                + "    cost of physically performing source distribution, a complete\n"
                + "    machine-readable copy of the corresponding source code, to be\n"
                + "    distributed under the terms of Sections 1 and 2 above on a medium\n"
                + "    customarily used for software interchange; or,\n"
                + "\n"
                + "    c) Accompany it with the information you received as to the offer\n"
                + "    to distribute corresponding source code.  (This alternative is\n"
                + "    allowed only for noncommercial distribution and only if you\n"
                + "    received the program in object code or executable form with such\n"
                + "    an offer, in accord with Subsection b above.)\n"
                + "\n"
                + "The source code for a work means the preferred form of the work for\n"
                + "making modifications to it.  For an executable work, complete source\n"
                + "code means all the source code for all modules it contains, plus any\n"
                + "associated interface definition files, plus the scripts used to\n"
                + "control compilation and installation of the executable.  However, as a\n"
                + "special exception, the source code distributed need not include\n"
                + "anything that is normally distributed (in either source or binary\n"
                + "form) with the major components (compiler, kernel, and so on) of the\n"
                + "operating system on which the executable runs, unless that component\n"
                + "itself accompanies the executable.\n"
                + "\n"
                + "If distribution of executable or object code is made by offering\n"
                + "access to copy from a designated place, then offering equivalent\n"
                + "access to copy the source code from the same place counts as\n"
                + "distribution of the source code, even though third parties are not\n"
                + "compelled to copy the source along with the object code.\n"
                + "\n"
                + "  4. You may not copy, modify, sublicense, or distribute the Program\n"
                + "except as expressly provided under this License.  Any attempt\n"
                + "otherwise to copy, modify, sublicense or distribute the Program is\n"
                + "void, and will automatically terminate your rights under this License.\n"
                + "However, parties who have received copies, or rights, from you under\n"
                + "this License will not have their licenses terminated so long as such\n"
                + "parties remain in full compliance.\n"
                + "\n"
                + "  5. You are not required to accept this License, since you have not\n"
                + "signed it.  However, nothing else grants you permission to modify or\n"
                + "distribute the Program or its derivative works.  These actions are\n"
                + "prohibited by law if you do not accept this License.  Therefore, by\n"
                + "modifying or distributing the Program (or any work based on the\n"
                + "Program), you indicate your acceptance of this License to do so, and\n"
                + "all its terms and conditions for copying, distributing or modifying\n"
                + "the Program or works based on it.\n"
                + "\n"
                + "  6. Each time you redistribute the Program (or any work based on the\n"
                + "Program), the recipient automatically receives a license from the\n"
                + "original licensor to copy, distribute or modify the Program subject to\n"
                + "these terms and conditions.  You may not impose any further\n"
                + "restrictions on the recipients' exercise of the rights granted herein.\n"
                + "You are not responsible for enforcing compliance by third parties to\n"
                + "this License.\n"
                + "\n"
                + "  7. If, as a consequence of a court judgment or allegation of patent\n"
                + "infringement or for any other reason (not limited to patent issues),\n"
                + "conditions are imposed on you (whether by court order, agreement or\n"
                + "otherwise) that contradict the conditions of this License, they do not\n"
                + "excuse you from the conditions of this License.  If you cannot\n"
                + "distribute so as to satisfy simultaneously your obligations under this\n"
                + "License and any other pertinent obligations, then as a consequence you\n"
                + "may not distribute the Program at all.  For example, if a patent\n"
                + "license would not permit royalty-free redistribution of the Program by\n"
                + "all those who receive copies directly or indirectly through you, then\n"
                + "the only way you could satisfy both it and this License would be to\n"
                + "refrain entirely from distribution of the Program.\n"
                + "\n"
                + "If any portion of this section is held invalid or unenforceable under\n"
                + "any particular circumstance, the balance of the section is intended to\n"
                + "apply and the section as a whole is intended to apply in other\n"
                + "circumstances.\n"
                + "\n"
                + "It is not the purpose of this section to induce you to infringe any\n"
                + "patents or other property right claims or to contest validity of any\n"
                + "such claims; this section has the sole purpose of protecting the\n"
                + "integrity of the free software distribution system, which is\n"
                + "implemented by public license practices.  Many people have made\n"
                + "generous contributions to the wide range of software distributed\n"
                + "through that system in reliance on consistent application of that\n"
                + "system; it is up to the author/donor to decide if he or she is willing\n"
                + "to distribute software through any other system and a licensee cannot\n"
                + "impose that choice.\n"
                + "\n"
                + "This section is intended to make thoroughly clear what is believed to\n"
                + "be a consequence of the rest of this License.\n"
                + "\n"
                + "  8. If the distribution and/or use of the Program is restricted in\n"
                + "certain countries either by patents or by copyrighted interfaces, the\n"
                + "original copyright holder who places the Program under this License\n"
                + "may add an explicit geographical distribution limitation excluding\n"
                + "those countries, so that distribution is permitted only in or among\n"
                + "countries not thus excluded.  In such case, this License incorporates\n"
                + "the limitation as if written in the body of this License.\n"
                + "\n"
                + "  9. The Free Software Foundation may publish revised and/or new versions\n"
                + "of the General Public License from time to time.  Such new versions will\n"
                + "be similar in spirit to the present version, but may differ in detail to\n"
                + "address new problems or concerns.\n"
                + "\n"
                + "Each version is given a distinguishing version number.  If the Program\n"
                + "specifies a version number of this License which applies to it and \"any\n"
                + "later version\", you have the option of following the terms and conditions\n"
                + "either of that version or of any later version published by the Free\n"
                + "Software Foundation.  If the Program does not specify a version number of\n"
                + "this License, you may choose any version ever published by the Free Software\n"
                + "Foundation.\n"
                + "\n"
                + "  10. If you wish to incorporate parts of the Program into other free\n"
                + "programs whose distribution conditions are different, write to the author\n"
                + "to ask for permission.  For software which is copyrighted by the Free\n"
                + "Software Foundation, write to the Free Software Foundation; we sometimes\n"
                + "make exceptions for this.  Our decision will be guided by the two goals\n"
                + "of preserving the free status of all derivatives of our free software and\n"
                + "of promoting the sharing and reuse of software generally.\n"
                + "\n"
                + "                            NO WARRANTY\n"
                + "\n"
                + "  11. BECAUSE THE PROGRAM IS LICENSED FREE OF CHARGE, THERE IS NO WARRANTY\n"
                + "FOR THE PROGRAM, TO THE EXTENT PERMITTED BY APPLICABLE LAW.  EXCEPT WHEN\n"
                + "OTHERWISE STATED IN WRITING THE COPYRIGHT HOLDERS AND/OR OTHER PARTIES\n"
                + "PROVIDE THE PROGRAM \"AS IS\" WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED\n"
                + "OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF\n"
                + "MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.  THE ENTIRE RISK AS\n"
                + "TO THE QUALITY AND PERFORMANCE OF THE PROGRAM IS WITH YOU.  SHOULD THE\n"
                + "PROGRAM PROVE DEFECTIVE, YOU ASSUME THE COST OF ALL NECESSARY SERVICING,\n"
                + "REPAIR OR CORRECTION.\n"
                + "\n"
                + "  12. IN NO EVENT UNLESS REQUIRED BY APPLICABLE LAW OR AGREED TO IN WRITING\n"
                + "WILL ANY COPYRIGHT HOLDER, OR ANY OTHER PARTY WHO MAY MODIFY AND/OR\n"
                + "REDISTRIBUTE THE PROGRAM AS PERMITTED ABOVE, BE LIABLE TO YOU FOR DAMAGES,\n"
                + "INCLUDING ANY GENERAL, SPECIAL, INCIDENTAL OR CONSEQUENTIAL DAMAGES ARISING\n"
                + "OUT OF THE USE OR INABILITY TO USE THE PROGRAM (INCLUDING BUT NOT LIMITED\n"
                + "TO LOSS OF DATA OR DATA BEING RENDERED INACCURATE OR LOSSES SUSTAINED BY\n"
                + "YOU OR THIRD PARTIES OR A FAILURE OF THE PROGRAM TO OPERATE WITH ANY OTHER\n"
                + "PROGRAMS), EVEN IF SUCH HOLDER OR OTHER PARTY HAS BEEN ADVISED OF THE\n"
                + "POSSIBILITY OF SUCH DAMAGES.\n"
                + "\n"
                + "                     END OF TERMS AND CONDITIONS";
        JTextArea textArea = new JTextArea(msg);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane.setPreferredSize(new Dimension(500, 500));
        JOptionPane.showMessageDialog(null, scrollPane, "",
                JOptionPane.OK_OPTION);
    }//GEN-LAST:event_jButton1ActionPerformed

    public class UBNTPanel extends JFXPanel {

        Scene scene;
        int mouseX = 0;
        int mouseY = 0;

        public UBNTPanel() {

            setBackground(Color.lightGray);
            MouseAdapter ma = new MouseAdapter() {

                @Override
                public void mouseDragged(MouseEvent e) {
                    if (!setScale) {
                        if (!e.isPopupTrigger()) {
                            //System.out.println("Performing Drag");
                            JViewport viewPort = jScrollPane1.getViewport();
                            Point vpp = viewPort.getViewPosition();
                            vpp.translate(mouseX - e.getX(), mouseY - e.getY());
                            scrollRectToVisible(new Rectangle(vpp, viewPort.getSize()));
                        }
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    if (unlink) {
                        unlink = false;
                        System.out.println("UnLinking...");
                        int deviceA = selectedDevice;
                        findSelection(new Point((int) ((e.getX() / scale) - (imgX * 2)), (int) ((e.getY() / scale) - (imgY * 2))));
                        int deviceB = selectedDevice;
                        if (deviceA == deviceB || deviceB == -1) {
                            return;
                        }
                        for (int i = 0; i < links.size(); i++) {
                            Link l = links.get(i);
                            if (l.a == devices.get(deviceA) && l.a == devices.get(deviceA) || l.b == devices.get(deviceA) && l.a == devices.get(deviceA)) {
                                links.remove(l);
                                break;
                            }
                        }
                        repaint();
                    }
                    if (link) {
                        link = false;

                        System.out.println("Linking...");
                        int deviceA = selectedDevice;
                        findSelection(new Point((int) ((e.getX() / scale) - (imgX * 2)), (int) ((e.getY() / scale) - (imgY * 2))));
                        int deviceB = selectedDevice;
                        if (deviceA == deviceB || deviceB == -1) {
                            linkWireless = false;
                            System.out.println("Invalid device link abort");
                            return;
                        }
                        if (linkWireless) {
                            if (devices.get(deviceA).wirelessConnections > getWirelessLinks(deviceA) && devices.get(deviceB).wirelessConnections > getWirelessLinks(deviceB)) {
                                Link l = new Link();
                                l.wired = false;
                                l.a = devices.get(deviceA);
                                l.b = devices.get(deviceB);
                                links.add(l);
                                System.out.println("Connected link");
                            } else {
                                System.out.println("Too many wireless links");
                            }
                            linkWireless = false;
                        } else {
                            if (devices.get(deviceA).wiredConnections > getWiredLinks(deviceA) && devices.get(deviceB).wiredConnections > getWiredLinks(deviceB)) {
                                Link l = new Link();
                                l.a = devices.get(deviceA);
                                l.b = devices.get(deviceB);
                                links.add(l);
                                System.out.println("Connected link");
                            } else {
                                System.out.println("Too many wired links");
                            }
                        }

                        repaint();
                        return;
                    }
                    if (move) {
                        ((UBNTDevice) devices.get(selectedDevice)).location = new Point((int) ((e.getX() / scale) - (imgX * 2)), (int) ((e.getY() / scale) - (imgY * 2)));
                        move = false;
                        repaint();
                        return;
                    }
                    if (addDevice) {
                        System.out.println("Adding Device at: " + e.getPoint());
                        UBNTDevice d = ((UBNTDevice) ubnts.getSelectedItem()).cloneDev();
                        d.location = new Point((int) ((e.getX() / scale) - (imgX * 2)), (int) ((e.getY() / scale) - (imgY * 2)));
                        System.out.println("Correction at: " + d.location);
                        devices.add(d);
                        addDevice = false;
                        panel.repaint();
                        return;
                    }
                    if (setScale) {
                        a = e.getPoint();
                        return;
                    }
                    if (e.isPopupTrigger()) {
                        showPopup(e);
                        return;
                    }
                    System.out.println("Dragging");
                    mouseX = e.getX();
                    mouseY = e.getY();

                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (setScale) {
                        setScale = false;
                        b = e.getPoint();
                        double distance = a.distance(b);
                        System.out.println("Distance: " + distance);
                        distance = distance / scale;
                        System.out.println("Normilized: " + distance);
                        double ans = Double.valueOf(JOptionPane.showInputDialog("Enter distance in feet", JOptionPane.INFORMATION_MESSAGE));
                        keyScale = distance / ans;
                        System.out.println("1 ft = " + keyScale);
                        return;
                    }
                    showPopup(e);
                }

                private void showPopup(MouseEvent e) {
                    findSelection(new Point((int) ((e.getX() / scale) - (imgX * 2)), (int) ((e.getY() / scale) - (imgY * 2))));
                    if (e.isPopupTrigger()) {
                        jPopupMenu1.show(e.getComponent(),
                                e.getX(), e.getY());
                    }
                }

                private void findSelection(Point mouse) {
                    //
                    boolean f = false;
                    for (UBNTDevice device : devices) {
                        if (device.location.distance(mouse) <= 10 / scale) {
                            selectedDevice = devices.indexOf(device);
                            f = true;
                        }
                    }
                    if (!f) {
                        selectedDevice = -1;
                        jMenuItem6.setText("");
                    } else {
                        jMenuItem6.setText(devices.get(selectedDevice).name);
                    }
                    //
                    jMenuItem2.setEnabled(selectedDevice > -1);
                    jMenuItem3.setEnabled(selectedDevice > -1);
                    jMenuItem4.setEnabled(selectedDevice > -1);
                    jMenuItem5.setEnabled(selectedDevice > -1);
                }
            };

            addMouseMotionListener(ma);
            addMouseListener(ma);
            Platform.runLater(this::createScene);
        }

        public void createScene() {
            Group g = new Group();
            scene = new Scene(g, getWidth(), getHeight());
            setScene(scene);
            scene.setOnZoom(new EventHandler<ZoomEvent>() {
                @Override
                public void handle(ZoomEvent event) {
                    System.out.println("Zoom: " + event.getZoomFactor());
                    event.consume();
                }
            });
            scene.setOnMousePressed(new EventHandler<javafx.scene.input.MouseEvent>() {

                @Override
                public void handle(javafx.scene.input.MouseEvent t) {
                    //System.out.println("Clicked");
                    t.consume();
                }
            });
            scene.setOnScroll(new EventHandler<ScrollEvent>() {
                @Override
                public void handle(ScrollEvent event) {
                    System.out.println("Scroll: " + event.getDeltaY());
                    if (event.getDeltaY() > 0) {
                        System.out.println("X Out");
                        panel.setScale(scale - 0.1);
                    } else if (event.getDeltaY() < 0) {
                         System.out.println("X In");
                        panel.setScale(scale + 0.1);
                    }
                    event.consume();
                }
            });
        }

        @Override
        public void paint(Graphics g) {

            super.paint(g);
            Graphics2D g2 = (Graphics2D) g;

            if (image != null) {

                int w = getWidth();
                int h = getHeight();
                int imageWidth = image.getWidth();
                int imageHeight = image.getHeight();
                imgX = (w - scale * imageWidth) / 2;
                imgY = (h - scale * imageHeight) / 2;
                //System.out.println("Image at " + imgX + "," + imgY);
                AffineTransform at = AffineTransform.getTranslateInstance(imgX, imgY);
                at.scale(scale, scale);
                g2.drawRenderedImage(image, at);
                //Draw devices
                devices.stream().forEach((device) -> {
                    device.keyScale = keyScale;
                    device.scale = scale;
                    device.xOffset = imgX;
                    device.yOffset = imgY;
                    device.paint0(g);

                });
                devices.stream().forEach((device) -> {
                    device.paint1(g);
                });
                //Draw links
                for (Link l : links) {
                    int xa = (int) (l.a.location.x * l.a.scale + l.a.xOffset);
                    int xb = (int) (l.b.location.x * l.b.scale + l.b.xOffset);
                    int ya = (int) (l.a.location.y * l.a.scale + l.a.yOffset);
                    int yb = (int) (l.b.location.y * l.b.scale + l.b.yOffset);
                    if (l.wired) {
                        g.setColor(Color.RED);
                    } else {
                        g.setColor(Color.GREEN);
                    }
                    g.drawLine(xa, ya, xb, yb);
                }
            }
        }

        public int getWiredLinks(int index) {
            int i = 0;
            for (Link l : links) {
                if ((l.a == devices.get(index) || l.b == devices.get(index)) && l.wired) {
                    i++;
                }
            }
            return i;
        }

        public int getWirelessLinks(int index) {
            int i = 0;
            for (Link l : links) {
                if ((l.a == devices.get(index) || l.b == devices.get(index)) && !l.wired) {
                    i++;
                }
            }
            return i;
        }

        @Override
        public Dimension getPreferredSize() {
            if (image != null) {
                int w = (int) (scale * image.getWidth());
                int h = (int) (scale * image.getHeight());
                return new Dimension(w, h);
            } else {
                return new Dimension(600, 800);
            }
        }

        public void setScale(double s) {
            if (s > 0 && s < 100) {
                scale = s;
                revalidate();
                repaint();
            }
        }
    }

    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Window().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    private javax.swing.JButton jButton1;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton load;
    private javax.swing.JButton save;
    private javax.swing.JButton selectImage;
    private javax.swing.JComboBox ubnts;
    private javax.swing.JButton zin;
    private javax.swing.JButton zout;
    // End of variables declaration//GEN-END:variables
}
