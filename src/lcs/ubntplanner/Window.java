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
import javax.imageio.ImageIO;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JViewport;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Trevor Flynn
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
        UBNTSwitch ts_5 = new UBNTSwitch();
        ts_5.wiredConnections = 5;
        ts_5.name = "TS-5-POE";
        UBNTSwitch ts_8 = new UBNTSwitch();
        ts_8.wiredConnections = 8;
        ts_8.name = "TS-8-POE";
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
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

        jFileChooser1.setFileFilter(fileFilter);
        jFileChooser1.setFileSelectionMode(javax.swing.JFileChooser.FILES_AND_DIRECTORIES);

        jMenuItem6.setText("jMenuItem6");
        jMenuItem6.setEnabled(false);
        jPopupMenu1.add(jMenuItem6);

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 153, Short.MAX_VALUE)
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
                    .addComponent(load))
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

    public class UBNTPanel extends JPanel {

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
                            }
                            linkWireless = false;
                        } else {
                            if (devices.get(deviceA).wiredConnections > getWiredLinks(deviceA) && devices.get(deviceB).wiredConnections > getWiredLinks(deviceB)) {
                                Link l = new Link();
                                l.a = devices.get(deviceA);
                                l.b = devices.get(deviceB);
                                links.add(l);
                                System.out.println("Connected link");
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
                    if (!setScale) {
                        if (!e.isPopupTrigger()) {
                            System.out.println("Dragging");
                            mouseX = e.getX();
                            mouseY = e.getY();
                        } else {
                            showPopup(e);
                        }
                    } else {
                        a = e.getPoint();
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (!setScale) {
                        showPopup(e);
                    } else {
                        b = e.getPoint();
                        double distance = a.distance(b);
                        System.out.println("Distance: " + distance);
                        distance = distance / scale;
                        System.out.println("Normilized: " + distance);
                        double ans = Double.valueOf(JOptionPane.showInputDialog("Enter distance in feet", JOptionPane.INFORMATION_MESSAGE));
                        setScale = false;
                        keyScale = distance / ans;
                        System.out.println("1 ft = " + keyScale);
                    }
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
                        jMenuItem6.setText("N/A");
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
        }

        @Override
        public void paint(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                Graphics2D g2 = (Graphics2D) g;
                //g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                //       RenderingHints.VALUE_INTERPOLATION_BICUBIC);
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
                if (l.a == devices.get(index) || l.b == devices.get(index) && l.wired) {
                    i++;
                }
            }
            return i;
        }

        public int getWirelessLinks(int index) {
            int i = 0;
            for (Link l : links) {
                if (l.a == devices.get(index) || l.b == devices.get(index) && !l.wired) {
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
            scale = s;
            revalidate();      // update the scroll pane
            repaint();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Window().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
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
