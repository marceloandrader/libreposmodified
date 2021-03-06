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

package net.adrianromero.sql;

import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import javax.swing.JFrame;
import net.adrianromero.basic.BasicException;
import net.adrianromero.data.gui.JMessageDialog;
import net.adrianromero.data.gui.MessageInf;
import net.adrianromero.data.loader.Session;
import net.adrianromero.tpv.data.DataLogic;
import net.adrianromero.tpv.forms.*;
import net.adrianromero.tpv.printer.DeviceTicket;
import net.adrianromero.tpv.scale.DeviceScale;
import net.adrianromero.tpv.scanpal2.DeviceScanner;

public class JFrmSQL extends javax.swing.JFrame implements AppView {
    
    private AppViewConnection m_appcnt;        
    private JPanelSQL sql;
    
    /** Creates new form JFrmSQL */
    public JFrmSQL() {
    }
    
    private boolean start() {        
        
        initComponents();
        
        try {
            m_appcnt = new AppViewConnection();
        } catch (BasicException e) {
            JMessageDialog.showMessage(this, new MessageInf(MessageInf.SGN_DANGER, e.getMessage(), e));
            return false;
        }        

        addWindowListener(new MyFrameListener()); 
        
        sql = new JPanelSQL(this);       
        getContentPane().add(sql, BorderLayout.CENTER);
        
        try {
            sql.activate(); 
        } catch (BasicException e) { // never thrown ;-)
        }
        
        setVisible(true);
        
        return true;
    }
    
    private class MyFrameListener extends WindowAdapter{
        
        public void windowClosing(WindowEvent evt) {
            sql.deactivate();
            m_appcnt.disconnect();
            dispose();
        }
        public void windowClosed(WindowEvent evt) {
            System.exit(0);
        }
    }
    
    public DeviceScale getDeviceScale() {
        return null;
    }
    
    public DeviceScanner getDeviceScanner() {
        return null;
    }
    
    public DeviceTicket getDeviceTicket() {
        return null;
    }
    
    public String getHost() {
        return m_appcnt.getHost();
    }
    public String getActiveCashIndex() {
        return null;
    }
    public Date getActiveCashDateStart() {
        return null;
    }
    public Date getActiveCashDateEnd(){
        return null;
    }
    public String getInventoryLocation() {
        return null;
    }
    public void setActiveCash(String iIndex, Date dStart, Date dEnd) {     
    }

    public Session getSession() {
        return m_appcnt.getSession();
    }
//    public DataSource getDataSource() {
//        return m_appcnt.getDataSource();
//    }
    
    public String getProperty(String sKey) {
        return m_appcnt.getProperty(sKey);
    }

    public <T extends DataLogic> T lookupDataLogic(Class<T> clazz) {
        return null;
    }
    
    public JFrame getFrame() {
        return this;
    }    
    public void waitCursorBegin() {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }
    public void waitCursorEnd() {
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-706)/2, (screenSize.height-369)/2, 706, 369);
    }
    // </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               
//                try {
//                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//                    // UIManager.setLookAndFeel("com.shfarr.ui.plaf.fh.FhLookAndFeel");
//                    // UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
//                } catch(Exception ex) {
//                }
                if (!new JFrmSQL().start()) {
                    // No se ha iniciado correctamente, entonces nos vamos disgustados.
                    System.exit(1);
                }  
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
}
