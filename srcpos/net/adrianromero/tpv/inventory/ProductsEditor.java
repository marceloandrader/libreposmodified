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

package net.adrianromero.tpv.inventory;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.image.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.adrianromero.tpv.forms.AppView;
import net.adrianromero.tpv.forms.AppLocal;
import net.adrianromero.format.Formats;
import net.adrianromero.basic.BasicException;
import net.adrianromero.data.gui.ComboBoxValModel;
import net.adrianromero.data.gui.ListCellRendererBasic;
import net.adrianromero.data.loader.RenderStringBasic;
import net.adrianromero.data.loader.SentenceList;
import net.adrianromero.data.user.EditorRecord;
import net.adrianromero.data.user.DirtyManager;
import net.adrianromero.tpv.forms.SentenceContainer;
import net.adrianromero.tpv.ticket.TaxInfo;

public class ProductsEditor extends JPanel implements EditorRecord {
       
    private SentenceList m_sentcat;
    private ComboBoxValModel m_CategoryModel;

    private SentenceList m_senttax;
    private ComboBoxValModel m_TaxModel;  
    
    private ComboBoxValModel m_CodetypeModel;
    
    private Double m_dPriceSell;
    
    private SentenceContainer m_sentcoms;
    
    /** Creates new form JEditProduct */
    public ProductsEditor(AppView app, DirtyManager dirty) {
        initComponents();
             
        // El modelo de categorias
        m_sentcat = app.lookupDataLogic(SentenceContainer.class).getCategoriesList();
        m_CategoryModel = new ComboBoxValModel();
        
        // El modelo de impuestos
        m_senttax = app.lookupDataLogic(SentenceContainer.class).getTaxList();
        m_TaxModel = new ComboBoxValModel();
        
        m_CodetypeModel = new ComboBoxValModel();
        m_CodetypeModel.add(null);
        m_CodetypeModel.add(CodeType.EAN13);
        m_CodetypeModel.add(CodeType.CODE128);
        m_jCodetype.setModel(m_CodetypeModel);
        m_jCodetype.setVisible(false);
  
        m_sentcoms = app.lookupDataLogic(SentenceContainer.class);
               
        m_jRef.getDocument().addDocumentListener(dirty);
        m_jCode.getDocument().addDocumentListener(dirty);
        m_jName.getDocument().addDocumentListener(dirty);
        m_jComment.addActionListener(dirty);
        m_jScale.addActionListener(dirty);
        m_jCategory.addActionListener(dirty);
        m_jTax.addActionListener(dirty);
        m_jPriceBuy.getDocument().addDocumentListener(dirty);
        m_jPriceSell.getDocument().addDocumentListener(dirty);
        m_jImage.addPropertyChangeListener("image", dirty);
        m_jstockcost.getDocument().addDocumentListener(dirty);
        m_jstockvolume.getDocument().addDocumentListener(dirty);
        m_jInCatalog.addActionListener(dirty);
        m_jCatalogOrder.getDocument().addDocumentListener(dirty);

        // el informe de stock
        ReportManager rm = new ReportManager();
        m_jPriceBuy.getDocument().addDocumentListener(rm);
        m_jTax.addActionListener(rm);
        m_jstockcost.getDocument().addDocumentListener(rm);
        m_jstockvolume.getDocument().addDocumentListener(rm);
        
        m_jPriceSell.getDocument().addDocumentListener(new PriceManager());
        m_jPriceSellTax.getDocument().addDocumentListener(new PriceTaxManager());
        m_jmargin.getDocument().addDocumentListener(new MarginManager());
        
        writeValueEOF();
    }
    
    public void activate() throws BasicException {
        
        m_CategoryModel = new ComboBoxValModel(m_sentcat.list());
        m_jCategory.setModel(m_CategoryModel);

        m_TaxModel = new ComboBoxValModel(m_senttax.list());
        m_jTax.setModel(m_TaxModel);
    }
    
    public void writeValueEOF() {
        
        // Los valores
        m_jTitle.setText(AppLocal.getIntString("label.recordeof"));
        m_jRef.setText(null);
        m_jCode.setText(null);
        m_jName.setText(null);
        m_jComment.setSelected(false);
        m_jScale.setSelected(false);
        m_CategoryModel.setSelectedKey(null);
        m_TaxModel.setSelectedKey(null);
        m_jPriceBuy.setText(null);
        m_bPriceSellLock = true;
        m_jPriceSell.setText(null);            
        m_bPriceSellLock = false;
        m_jImage.setImage(null);
        m_jstockcost.setText(null);
        m_jstockvolume.setText(null);
        m_jInCatalog.setSelected(false);
        m_jCatalogOrder.setText(null);
        
        // Los habilitados
        m_jRef.setEnabled(false);
        m_jCode.setEnabled(false);
        m_jName.setEnabled(false);
        m_jComment.setEnabled(false);
        m_jScale.setEnabled(false);
        m_jCategory.setEnabled(false);
        m_jTax.setEnabled(false);
        m_jPriceBuy.setEnabled(false);
        m_jPriceSell.setEnabled(false);
        m_jPriceSellTax.setEnabled(false);
        m_jmargin.setEnabled(false);
        m_jImage.setEnabled(false);
        m_jstockcost.setEnabled(false);
        m_jstockvolume.setEnabled(false);
        m_jInCatalog.setEnabled(false);
        m_jCatalogOrder.setEnabled(false);
        
//        m_dUnits = null;
        writeReport(null, 0.0);
    }
    public void writeValueInsert() {
        
        // Los valores
        m_jTitle.setText(AppLocal.getIntString("label.recordnew"));
        m_jRef.setText(null);
        m_jCode.setText(null);
        m_jName.setText(null);
        m_jComment.setSelected(false);
        m_jScale.setSelected(false);
        m_CategoryModel.setSelectedKey(null);
        m_TaxModel.setSelectedKey(null);
        m_jPriceBuy.setText(null);
        m_dPriceSell = null;
        m_bPriceSellLock = true;
        m_jPriceSell.setText(null);            
        m_bPriceSellLock = false;
        m_jImage.setImage(null);
        m_jstockcost.setText(null);
        m_jstockvolume.setText(null);
        m_jInCatalog.setSelected(false);
        m_jCatalogOrder.setText(null);
        
        // Los habilitados
        m_jRef.setEnabled(true);
        m_jCode.setEnabled(true);
        m_jName.setEnabled(true);
        m_jComment.setEnabled(true);
        m_jScale.setEnabled(true);
        m_jCategory.setEnabled(true);
        m_jTax.setEnabled(true);
        m_jPriceBuy.setEnabled(true);
        m_jPriceSell.setEnabled(true); 
        m_jPriceSellTax.setEnabled(true);
        m_jmargin.setEnabled(true);
        m_jImage.setEnabled(true);
        m_jstockcost.setEnabled(true);
        m_jstockvolume.setEnabled(true);
        m_jInCatalog.setEnabled(true); 
        m_jCatalogOrder.setEnabled(false);

//        m_dUnits = null;
        writeReport(null, 0.0);
   }
    public void writeValueDelete(Object value) {
        
        Object[] myprod = (Object[]) value;
        m_jTitle.setText(Formats.STRING.formatValue(myprod[0]) + " - " + Formats.STRING.formatValue(myprod[2]) + " " + AppLocal.getIntString("label.recorddeleted"));
        m_jRef.setText(Formats.STRING.formatValue(myprod[0]));
        m_jCode.setText(Formats.STRING.formatValue(myprod[1]));
        m_jName.setText(Formats.STRING.formatValue(myprod[2]));
        m_jComment.setSelected(((Boolean)myprod[3]).booleanValue());
        m_jScale.setSelected(((Boolean)myprod[4]).booleanValue());
        m_jPriceBuy.setText(Formats.CURRENCY.formatValue(myprod[5]));
        m_dPriceSell = (Double) myprod[6];
        m_bPriceSellLock = true;
        m_jPriceSell.setText(Formats.CURRENCY.formatValue(m_dPriceSell));            
        m_bPriceSellLock = false;
        m_CategoryModel.setSelectedKey(myprod[7]);
        m_TaxModel.setSelectedKey(myprod[8]);
        m_jImage.setImage((BufferedImage) myprod[9]);
        m_jstockcost.setText(Formats.CURRENCY.formatValue(myprod[10]));
        m_jstockvolume.setText(Formats.DOUBLE.formatValue(myprod[11]));
        m_jInCatalog.setSelected(((Boolean)myprod[12]).booleanValue());
        m_jCatalogOrder.setText(Formats.INT.formatValue(myprod[13]));
        
        // Los habilitados
        m_jRef.setEnabled(false);
        m_jCode.setEnabled(false);
        m_jName.setEnabled(false);
        m_jComment.setEnabled(false);
        m_jScale.setEnabled(false);
        m_jCategory.setEnabled(false);
        m_jTax.setEnabled(false);
        m_jPriceBuy.setEnabled(false);
        m_jPriceSell.setEnabled(false);
        m_jPriceSellTax.setEnabled(false);
        m_jmargin.setEnabled(false);
        m_jImage.setEnabled(false);
        m_jstockcost.setEnabled(false);
        m_jstockvolume.setEnabled(false);
        m_jInCatalog.setEnabled(false);
        m_jCatalogOrder.setEnabled(false);

        TaxInfo tax = (TaxInfo) m_TaxModel.getElementByKey(myprod[8]);
        double dTaxRate = (tax == null) ? 0.0 : tax.getRate();      
        writeReport((Double) myprod[5], dTaxRate);
    }    
    
    public void writeValueEdit(Object value) {
        
        Object[] myprod = (Object[]) value;
        m_jTitle.setText(Formats.STRING.formatValue(myprod[0]) + " - " + Formats.STRING.formatValue(myprod[2]));
        m_jRef.setText(Formats.STRING.formatValue(myprod[0]));
        m_jCode.setText(Formats.STRING.formatValue(myprod[1]));
        m_jName.setText(Formats.STRING.formatValue(myprod[2]));
        m_jComment.setSelected(((Boolean)myprod[3]).booleanValue());
        m_jScale.setSelected(((Boolean)myprod[4]).booleanValue());
        m_jPriceBuy.setText(Formats.CURRENCY.formatValue(myprod[5]));
        m_dPriceSell = (Double) myprod[6];
        m_bPriceSellLock = true;
        m_jPriceSell.setText(Formats.CURRENCY.formatValue(m_dPriceSell));            
        m_bPriceSellLock = false;
        m_CategoryModel.setSelectedKey(myprod[7]);
        m_TaxModel.setSelectedKey(myprod[8]);
        m_jImage.setImage((BufferedImage) myprod[9]);
        m_jstockcost.setText(Formats.CURRENCY.formatValue(myprod[10]));
        m_jstockvolume.setText(Formats.DOUBLE.formatValue(myprod[11]));
        m_jInCatalog.setSelected(((Boolean)myprod[12]).booleanValue());
        m_jCatalogOrder.setText(Formats.INT.formatValue(myprod[13]));
        
        // Los habilitados
        m_jRef.setEnabled(false);
        m_jCode.setEnabled(true);
        m_jName.setEnabled(true);
        m_jComment.setEnabled(true);
        m_jScale.setEnabled(true);
        m_jCategory.setEnabled(true);
        m_jTax.setEnabled(true);
        m_jPriceBuy.setEnabled(true);
        m_jPriceSell.setEnabled(true); 
        m_jPriceSellTax.setEnabled(true);
        m_jmargin.setEnabled(true);
        m_jImage.setEnabled(true);
        m_jstockcost.setEnabled(true);
        m_jstockvolume.setEnabled(true);
        m_jInCatalog.setEnabled(true);
        m_jCatalogOrder.setEnabled(m_jInCatalog.isSelected());   
        
        TaxInfo tax = (TaxInfo) m_TaxModel.getElementByKey(myprod[8]);
        double dTaxRate = (tax == null) ? 0.0 : tax.getRate();   
        writeReport((Double) myprod[5], dTaxRate);
    }

    public Object createValue() throws BasicException {
        
        Object[] myprod = new Object[14];
        myprod[0] = m_jRef.getText();
        myprod[1] = m_jCode.getText();
        myprod[2] = m_jName.getText();
        myprod[3] = new Boolean(m_jComment.isSelected());
        myprod[4] = new Boolean(m_jScale.isSelected());
        myprod[5] = Formats.CURRENCY.parseValue(m_jPriceBuy.getText());
        myprod[6] = m_dPriceSell; // Formats.CURRENCY.parseValue(m_jPriceSell.getText());
        myprod[7] = m_CategoryModel.getSelectedKey();
        myprod[8] = m_TaxModel.getSelectedKey();
        myprod[9] = m_jImage.getImage();
//        myprod[10] = Formats.DOUBLE.parseValue(m_jstocksecurity.getText());
//        myprod[11] = Formats.DOUBLE.parseValue(m_jstockmaximum.getText());
        myprod[10] = Formats.CURRENCY.parseValue(m_jstockcost.getText());
        myprod[11] = Formats.DOUBLE.parseValue(m_jstockvolume.getText());
        myprod[12] = new Boolean(m_jInCatalog.isSelected());       
        myprod[13] = Formats.INT.parseValue(m_jCatalogOrder.getText());;       
        return myprod;
    }    
    
    public Component getComponent() {
        return this;
    }

    private void writeReport() {

        Double dPriceBuy = readCurrency(m_jPriceBuy.getText());
        TaxInfo tax = (TaxInfo) m_TaxModel.getSelectedItem();
        double dTaxRate = (tax == null) ? 0.0 : tax.getRate();  
        writeReport(dPriceBuy, dTaxRate);
    }
    
    private void writePriceTax() {
        
        Double dPriceSellTax = readCurrency(m_jPriceSellTax.getText());  
        TaxInfo tax = (TaxInfo) m_TaxModel.getSelectedItem();
        double dTaxRate = (tax == null) ? 0.0 : tax.getRate();  
        if (dPriceSellTax == null) {
            m_dPriceSell = null;
        } else {
            m_dPriceSell = new Double(dPriceSellTax.doubleValue() / (1.0 + dTaxRate));
        }

        m_bPriceSellLock = true;
        m_jPriceSell.setText(Formats.CURRENCY.formatValue(m_dPriceSell));            
        m_bPriceSellLock = false;

        m_bPriceSellTaxWriteLock = true;
        writeReport();
        m_bPriceSellTaxWriteLock = false;
    }
    
    private void writeMargin() {
        
        Double dPriceBuy = readCurrency(m_jPriceBuy.getText());
        Double dMargin = readPercent(m_jmargin.getText());  
        if (dMargin == null || dPriceBuy == null) {
            m_dPriceSell = dPriceBuy;
        } else {
            m_dPriceSell = new Double(dPriceBuy.doubleValue() * (1.0 + dMargin.doubleValue()));
        }

        m_bPriceSellLock = true;
        m_jPriceSell.setText(Formats.CURRENCY.formatValue(m_dPriceSell));            
        m_bPriceSellLock = false;

        m_bMarginWriteLock = true;
        writeReport();
        m_bMarginWriteLock = false;
    }
    
    private boolean m_bPriceSellLock = false;
    private boolean m_bMarginLock = false;
    private boolean m_bPriceSellTaxLock = false;
    
    private boolean m_bMarginWriteLock = false;
    private boolean m_bPriceSellTaxWriteLock = false;

    
    // private void writeReport(Double dPriceBuy, double dTaxRate, Double dStockSecurity, Double dStockCost, Double dStockVolume) {
    private void writeReport(Double dPriceBuy, double dTaxRate) {
               
        if (!m_bPriceSellTaxWriteLock) { 
            m_bPriceSellTaxLock = true;
            if (m_dPriceSell == null) {
                m_jPriceSellTax.setText(null);
            } else {
                m_jPriceSellTax.setText(Formats.CURRENCY.formatValue(new Double(m_dPriceSell.doubleValue() * (1.0 + dTaxRate))));
            }      
            m_bPriceSellTaxLock = false;
        }
        if (!m_bMarginWriteLock) {
            m_bMarginLock = true;
            if (dPriceBuy == null || m_dPriceSell == null) {
                m_jmargin.setText(null);
            } else {
                m_jmargin.setText(Formats.PERCENT.formatValue(new Double(m_dPriceSell.doubleValue() / dPriceBuy.doubleValue() - 1.0)));
            }
            m_bMarginLock = false;
        }
    }
    
    private class ReportManager implements DocumentListener, ActionListener {
        public void changedUpdate(DocumentEvent e) {
            writeReport();
        }
        public void insertUpdate(DocumentEvent e) {
            writeReport();
        }    
        public void removeUpdate(DocumentEvent e) {
            writeReport();
        }         
        public void actionPerformed(ActionEvent e) {
            writeReport();
        }
    }
    
    private class PriceManager implements DocumentListener  {
        public void changedUpdate(DocumentEvent e) {
            if (!m_bPriceSellLock) { // veo si puedo tocar m_jPriceSell
                m_dPriceSell = readCurrency(m_jPriceSell.getText());
                writeReport();
            }
        }
        public void insertUpdate(DocumentEvent e) {
            if (!m_bPriceSellLock) { // veo si puedo tocar m_jPriceSell
                m_dPriceSell = readCurrency(m_jPriceSell.getText());
                writeReport();
            }
        }    
        public void removeUpdate(DocumentEvent e) {
            if (!m_bPriceSellLock) { // veo si puedo tocar m_jPriceSell
                m_dPriceSell = readCurrency(m_jPriceSell.getText());
                writeReport();
            }
        }         
    }
    
    private class PriceTaxManager implements DocumentListener {
        public void changedUpdate(DocumentEvent e) {
            if (!m_bPriceSellTaxLock) {
                writePriceTax();
            }
        }
        public void insertUpdate(DocumentEvent e) {
            if (!m_bPriceSellTaxLock) {
                writePriceTax();
            }
        }    
        public void removeUpdate(DocumentEvent e) {
            if (!m_bPriceSellTaxLock) {
                writePriceTax();
            }
        }         
    }
    
    private class MarginManager implements DocumentListener  {
        public void changedUpdate(DocumentEvent e) {
            if (!m_bMarginLock) {
                writeMargin();
            }
        }
        public void insertUpdate(DocumentEvent e) {
            if (!m_bMarginLock) {
                writeMargin();
            }
        }    
        public void removeUpdate(DocumentEvent e) {
            if (!m_bMarginLock) {
                writeMargin();
            }
        }         
    }
    
    private final static Double readCurrency(String sValue) {
        try {
            return (Double) Formats.CURRENCY.parseValue(sValue);
        } catch (BasicException e) {
            return null;
        }
    }
    
    private final static Double readDouble(String sValue) {
        try {
            return (Double) Formats.DOUBLE.parseValue(sValue);
        } catch (BasicException e) {
            return null;
        }
    }
    
    private final static Double readPercent(String sValue) {
        try {
            return (Double) Formats.PERCENT.parseValue(sValue);
        } catch (BasicException e) {
            return null;
        }
    }

    private static class MyListData extends javax.swing.AbstractListModel {
        
        private java.util.List m_data;
        
        public MyListData(java.util.List data) {
            m_data = data;
        }
        
        public Object getElementAt(int index) {
            return m_data.get(index);
        }
        
        public int getSize() {
            return m_data.size();
        } 
    } 
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        m_jRef = new javax.swing.JTextField();
        m_jName = new javax.swing.JTextField();
        m_jTitle = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        m_jCode = new javax.swing.JTextField();
        m_jImage = new net.adrianromero.data.gui.JImageEditor();
        jLabel3 = new javax.swing.JLabel();
        m_jPriceBuy = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        m_jPriceSell = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        m_jCategory = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        m_jTax = new javax.swing.JComboBox();
        m_jmargin = new javax.swing.JTextField();
        m_jPriceSellTax = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        m_jCodetype = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        m_jstockcost = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        m_jstockvolume = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        m_jComment = new javax.swing.JCheckBox();
        m_jScale = new javax.swing.JCheckBox();
        m_jInCatalog = new javax.swing.JCheckBox();
        jLabel18 = new javax.swing.JLabel();
        m_jCatalogOrder = new javax.swing.JTextField();

        setLayout(null);

        jLabel1.setText(AppLocal.getIntString("label.prodref"));
        add(jLabel1);
        jLabel1.setBounds(10, 50, 80, 15);

        jLabel2.setText(AppLocal.getIntString("label.prodname"));
        add(jLabel2);
        jLabel2.setBounds(180, 50, 70, 15);

        add(m_jRef);
        m_jRef.setBounds(90, 50, 70, 19);

        add(m_jName);
        m_jName.setBounds(250, 50, 220, 19);

        m_jTitle.setFont(new java.awt.Font("SansSerif", 3, 18));
        add(m_jTitle);
        m_jTitle.setBounds(10, 10, 320, 30);

        jPanel1.setLayout(null);

        jLabel6.setText(AppLocal.getIntString("label.prodbarcode"));
        jPanel1.add(jLabel6);
        jLabel6.setBounds(10, 20, 150, 15);

        jPanel1.add(m_jCode);
        m_jCode.setBounds(160, 20, 170, 19);

        m_jImage.setMaxDimensions(new java.awt.Dimension(64, 32));
        jPanel1.add(m_jImage);
        m_jImage.setBounds(340, 20, 200, 180);

        jLabel3.setText(AppLocal.getIntString("label.prodpricebuy"));
        jPanel1.add(jLabel3);
        jLabel3.setBounds(10, 50, 150, 15);

        m_jPriceBuy.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jPanel1.add(m_jPriceBuy);
        m_jPriceBuy.setBounds(160, 50, 80, 19);

        jLabel4.setText(AppLocal.getIntString("label.prodpricesell"));
        jPanel1.add(jLabel4);
        jLabel4.setBounds(10, 80, 150, 15);

        m_jPriceSell.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jPanel1.add(m_jPriceSell);
        m_jPriceSell.setBounds(160, 80, 80, 19);

        jLabel5.setText(AppLocal.getIntString("label.prodcategory"));
        jPanel1.add(jLabel5);
        jLabel5.setBounds(10, 170, 150, 15);

        jPanel1.add(m_jCategory);
        m_jCategory.setBounds(160, 170, 170, 20);

        jLabel7.setText(AppLocal.getIntString("label.prodtax"));
        jPanel1.add(jLabel7);
        jLabel7.setBounds(10, 140, 150, 15);

        jPanel1.add(m_jTax);
        m_jTax.setBounds(160, 140, 170, 20);

        m_jmargin.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jPanel1.add(m_jmargin);
        m_jmargin.setBounds(250, 80, 80, 19);

        m_jPriceSellTax.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jPanel1.add(m_jPriceSellTax);
        m_jPriceSellTax.setBounds(160, 110, 80, 19);

        jLabel16.setText(AppLocal.getIntString("label.prodpriceselltax"));
        jPanel1.add(jLabel16);
        jLabel16.setBounds(10, 110, 150, 15);

        jPanel1.add(m_jCodetype);
        m_jCodetype.setBounds(300, 0, 80, 24);

        jTabbedPane1.addTab(AppLocal.getIntString("label.prodgeneral"), jPanel1);

        jPanel2.setLayout(null);

        jLabel9.setText(AppLocal.getIntString("label.prodstockcost"));
        jPanel2.add(jLabel9);
        jLabel9.setBounds(10, 10, 150, 15);

        m_jstockcost.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jPanel2.add(m_jstockcost);
        m_jstockcost.setBounds(160, 10, 80, 19);

        jLabel10.setText(AppLocal.getIntString("label.prodstockvol"));
        jPanel2.add(jLabel10);
        jLabel10.setBounds(10, 40, 150, 15);

        m_jstockvolume.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jPanel2.add(m_jstockvolume);
        m_jstockvolume.setBounds(160, 40, 80, 19);

        jTabbedPane1.addTab(AppLocal.getIntString("label.prodstock"), jPanel2);

        jPanel4.setLayout(null);

        m_jComment.setText(AppLocal.getIntString("label.prodaux"));
        jPanel4.add(m_jComment);
        m_jComment.setBounds(20, 80, 140, 23);

        m_jScale.setText(AppLocal.getIntString("label.prodscale"));
        jPanel4.add(m_jScale);
        m_jScale.setBounds(20, 110, 140, 23);

        m_jInCatalog.setText(AppLocal.getIntString("label.prodincatalog"));
        m_jInCatalog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jInCatalogActionPerformed(evt);
            }
        });

        jPanel4.add(m_jInCatalog);
        m_jInCatalog.setBounds(20, 20, 180, 23);

        jLabel18.setText(AppLocal.getIntString("label.prodorder"));
        jPanel4.add(jLabel18);
        jLabel18.setBounds(50, 50, 60, 15);

        m_jCatalogOrder.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jPanel4.add(m_jCatalogOrder);
        m_jCatalogOrder.setBounds(110, 50, 80, 19);

        jTabbedPane1.addTab(AppLocal.getIntString("label.prodproperties"), jPanel4);

        add(jTabbedPane1);
        jTabbedPane1.setBounds(10, 90, 560, 250);

    }// </editor-fold>//GEN-END:initComponents

    private void m_jInCatalogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jInCatalogActionPerformed
 
        if (m_jInCatalog.isSelected()) {
            m_jCatalogOrder.setEnabled(true);   
        } else {
            m_jCatalogOrder.setEnabled(false);   
            m_jCatalogOrder.setText(null);   
        }

    }//GEN-LAST:event_m_jInCatalogActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField m_jCatalogOrder;
    private javax.swing.JComboBox m_jCategory;
    private javax.swing.JTextField m_jCode;
    private javax.swing.JComboBox m_jCodetype;
    private javax.swing.JCheckBox m_jComment;
    private net.adrianromero.data.gui.JImageEditor m_jImage;
    private javax.swing.JCheckBox m_jInCatalog;
    private javax.swing.JTextField m_jName;
    private javax.swing.JTextField m_jPriceBuy;
    private javax.swing.JTextField m_jPriceSell;
    private javax.swing.JTextField m_jPriceSellTax;
    private javax.swing.JTextField m_jRef;
    private javax.swing.JCheckBox m_jScale;
    private javax.swing.JComboBox m_jTax;
    private javax.swing.JLabel m_jTitle;
    private javax.swing.JTextField m_jmargin;
    private javax.swing.JTextField m_jstockcost;
    private javax.swing.JTextField m_jstockvolume;
    // End of variables declaration//GEN-END:variables
    
}
