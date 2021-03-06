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

package net.adrianromero.tpv.reports;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

import net.adrianromero.tpv.forms.JPanelView;
import net.adrianromero.tpv.forms.AppView;
import net.adrianromero.tpv.forms.AppLocal;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.engine.design.*;
import net.adrianromero.basic.BasicException;
import net.adrianromero.data.gui.MessageInf;
import net.adrianromero.data.loader.BaseSentence;
import net.adrianromero.data.user.EditorCreator;
import net.sf.jasperreports.view.JRViewer;

public abstract class JPanelReport extends JPanel implements JPanelView  {
    
    private JRViewerMod reportviewer = null;
    // private JRViewer reportviewer = null;
    
    private JasperReport jr = null;
    
    private EditorCreator editor;
    
    protected AppView m_App;

    /** Creates new form JPanelReport */
    public JPanelReport(AppView oApp) {
        m_App = oApp;      
        
        initComponents();
        
        editor = createEditorCreator();
        if (editor instanceof JPanel) {
            jPanelHeader.add((JPanel) editor, BorderLayout.CENTER);
        }
        
        try {            
            reportviewer = new JRViewerMod();            
            // reportviewer = new JRViewer(null);          
            
            add(reportviewer, BorderLayout.CENTER);
        } catch (JRException e) {
        }        
        
        try {     
            
            InputStream in = getClass().getResourceAsStream(getReport() + ".ser");
            if (in == null) {      
                // Leo el diseno y lo compilo
                JasperDesign jd = JRXmlLoader.load(getClass().getResourceAsStream(getReport() + ".xml"));            
                jr = JasperCompileManager.compileReport(jd);    
            } else {
                // Leo el informe ya compilado
                ObjectInputStream oin = new ObjectInputStream(in);
                jr = (JasperReport) oin.readObject();
                oin.close();
            }
        } catch (Exception e) {
            MessageInf msg = new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.cannotloadreport"), e);
            msg.show(this);
            jr = null;
        }        
    }
    
    protected abstract String getReport();
    protected abstract String getResourceBundle();
    protected abstract BaseSentence getSentence();
    protected abstract ReportFields getReportFields();
    protected EditorCreator createEditorCreator() {
        return null;
    }
    
    private void launchreport() {     
        
        m_App.waitCursorBegin();
        
        if (jr != null) {
            try {     
                
                // Archivo de recursos
                String res = getResourceBundle();  
                
                // Parametros y los datos
                Object params = (editor == null) ? null : editor.createValue();                
                JRDataSource data = new JRDataSourceBasic(getSentence(), getReportFields(), params);
                
                // Construyo el mapa de los parametros.
                Map reportparams = new HashMap();
                reportparams.put("ARG", params);
                if (res != null) {
                    reportparams.put("REPORT_RESOURCE_BUNDLE", ResourceBundle.getBundle(res));
                }
                
                JasperPrint jp = JasperFillManager.fillReport(jr, reportparams, data);    
            
                reportviewer.loadJasperPrint(jp);               
//                this.remove(reportviewer);
//                reportviewer = new JRViewer(jp);            
//                add(reportviewer, BorderLayout.CENTER);
                
            } catch (MissingResourceException e) {    
                MessageInf msg = new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.cannotloadresourcedata"), e);
                msg.show(this);
            } catch (JRException e) {
                MessageInf msg = new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.cannotfillreport"), e);
                msg.show(this);
            } catch (BasicException e) {
                MessageInf msg = new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.cannotloadreportdata"), e);
                msg.show(this);
            }
        }
        
        m_App.waitCursorEnd();
    }

    public JComponent getComponent() {
        return this;
    }
    
    public void activate() throws BasicException {
    }    
    
    public boolean deactivate() {
        try {     
            reportviewer.loadJasperPrint(null);
        } catch (JRException e) {
        }
        return true;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanelHeader = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(5, 5, 5, 5)));
        jPanelHeader.setLayout(new java.awt.BorderLayout());

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/adrianromero/images/launch.png")));
        jButton1.setText(AppLocal.getIntString("Button.ExecuteReport"));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel1.add(jButton1);

        jPanelHeader.add(jPanel1, java.awt.BorderLayout.SOUTH);

        add(jPanelHeader, java.awt.BorderLayout.NORTH);

    }
    // </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        launchreport();
    }//GEN-LAST:event_jButton1ActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelHeader;
    // End of variables declaration//GEN-END:variables
    
}
