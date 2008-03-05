//    Librepos is a point of sales application designed for touch screens.
//    Copyright (C) 2005 Adrian Romero Corchado.
//    http://sourceforge.net/projects/librepos
//
//    This program is free software; you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation; either version 2 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program; if not, write to the Free Software
//    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

package net.adrianromero.tpv.config;

import java.awt.Component;
import net.adrianromero.tpv.forms.AppConfig;
import net.adrianromero.tpv.forms.AppLocal;
import net.adrianromero.tpv.util.AltEncrypter;
import net.adrianromero.tpv.util.DirectoryEvent;

public class JPanelConfigDatabase extends javax.swing.JPanel implements PanelConfig {
    
    /** Creates new form JPanelConfigDatabase */
    public JPanelConfigDatabase() {
        
        initComponents();
        jbtnDbDriverLib.addActionListener(new DirectoryEvent(jtxtDbDriverLib));
    }
    
    public Component getConfigComponent() {
        return this;
    }
   
    public void loadProperties(AppConfig config) {
        
        jtxtDbDriverLib.setText(config.getProperty("db.driverlib"));
        jtxtDbDriver.setText(config.getProperty("db.driver"));
        jtxtDbURL.setText(config.getProperty("db.URL"));
        
        String sDBUser = config.getProperty("db.user");
        String sDBPassword = config.getProperty("db.password");        
        if (sDBUser != null && sDBPassword != null && sDBPassword.startsWith("crypt:")) {
            // La clave esta encriptada.
            AltEncrypter cypher = new AltEncrypter("cypherkey" + sDBUser);
            sDBPassword = cypher.decrypt(sDBPassword.substring(6));
        }        
        jtxtDbUser.setText(sDBUser);
        jtxtDbPassword.setText(sDBPassword);     
    }
   
    public void saveProperties(AppConfig config) {
        
        config.setProperty("db.driverlib", jtxtDbDriverLib.getText());
        config.setProperty("db.driver", jtxtDbDriver.getText());
        config.setProperty("db.URL", jtxtDbURL.getText());
        config.setProperty("db.user", jtxtDbUser.getText());
        AltEncrypter cypher = new AltEncrypter("cypherkey" + jtxtDbUser.getText());       
        config.setProperty("db.password", "crypt:" + cypher.encrypt(new String(jtxtDbPassword.getPassword())));
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jtxtDbPassword = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jtxtDbDriverLib = new javax.swing.JTextField();
        jbtnDbDriverLib = new javax.swing.JButton();
        jtxtDbDriver = new javax.swing.JTextField();
        jtxtDbURL = new javax.swing.JTextField();
        jtxtDbUser = new javax.swing.JTextField();

        setLayout(null);

        setPreferredSize(new java.awt.Dimension(680, 190));
        add(jtxtDbPassword);
        jtxtDbPassword.setBounds(150, 160, 180, 20);

        jLabel4.setText(AppLocal.getIntString("Label.DbPassword"));
        add(jLabel4);
        jLabel4.setBounds(20, 160, 130, 14);

        jLabel3.setText(AppLocal.getIntString("Label.DbUser"));
        add(jLabel3);
        jLabel3.setBounds(20, 130, 130, 14);

        jLabel2.setText(AppLocal.getIntString("Label.DbURL"));
        add(jLabel2);
        jLabel2.setBounds(20, 100, 130, 14);

        jLabel1.setText(AppLocal.getIntString("Label.DbDriver"));
        add(jLabel1);
        jLabel1.setBounds(20, 70, 130, 14);

        jLabel17.setText(AppLocal.getIntString("label.dbdriverlib"));
        add(jLabel17);
        jLabel17.setBounds(20, 40, 130, 14);

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel8.setText(AppLocal.getIntString("Label.Database"));
        jLabel8.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        add(jLabel8);
        jLabel8.setBounds(20, 10, 660, 17);

        add(jtxtDbDriverLib);
        jtxtDbDriverLib.setBounds(150, 40, 340, 20);

        jbtnDbDriverLib.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/adrianromero/images/fileopen.png")));
        add(jbtnDbDriverLib);
        jbtnDbDriverLib.setBounds(500, 40, 30, 25);

        add(jtxtDbDriver);
        jtxtDbDriver.setBounds(150, 70, 180, 20);

        add(jtxtDbURL);
        jtxtDbURL.setBounds(150, 100, 340, 20);

        add(jtxtDbUser);
        jtxtDbUser.setBounds(150, 130, 180, 20);

    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JButton jbtnDbDriverLib;
    private javax.swing.JTextField jtxtDbDriver;
    private javax.swing.JTextField jtxtDbDriverLib;
    private javax.swing.JPasswordField jtxtDbPassword;
    private javax.swing.JTextField jtxtDbURL;
    private javax.swing.JTextField jtxtDbUser;
    // End of variables declaration//GEN-END:variables
    
}
