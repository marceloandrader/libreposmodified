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

package net.adrianromero.tpv.printer.javapos;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JComponent;
import jpos.CashDrawer;
import jpos.JposException;
import jpos.POSPrinter;
import jpos.POSPrinterConst;
import net.adrianromero.data.loader.ImageUtils;
import net.adrianromero.tpv.printer.DevicePrinter;
import net.adrianromero.tpv.printer.TicketPrinterException;

public class DevicePrinterJavaPOS  implements DevicePrinter {
    
    private static final String JPOS_SIZE0 = "\u001b|1C";
    private static final String JPOS_SIZE1 = "\u001b|2C";
    private static final String JPOS_SIZE2 = "\u001b|3C";
    private static final String JPOS_SIZE3 = "\u001b|4C";
    private static final String JPOS_LF = "\n";
    private static final String JPOS_BOLD = "\u001b|bC";
    private static final String JPOS_UNDERLINE = "\u001b|uC";
    private static final String JPOS_CUT = "\u001b|100fP";
    
    private String m_sName;
    
    private POSPrinter m_printer;
    private CashDrawer m_drawer;
    
    private StringBuffer m_sline;
    private boolean m_bTransaction = false;

    /** Creates a new instance of DevicePrinterJavaPOS */
    public DevicePrinterJavaPOS(String sDevicePrinterName, String sDeviceDrawerName) throws TicketPrinterException {
        m_sName = sDevicePrinterName; 
        
        m_printer = new POSPrinter();
        m_drawer = new CashDrawer();
        try {       
            m_printer.open(sDevicePrinterName);
            m_printer.claim(10000);
            m_printer.setDeviceEnabled(true);
            m_printer.setMapMode(POSPrinterConst.PTR_MM_METRIC);  // unit = 1/100 mm - i.e. 1 cm = 10 mm = 10 * 100 units
           
            m_drawer.open(sDevicePrinterName);
            m_drawer.claim(10000);
            m_drawer.setDeviceEnabled(true);
        } catch (JposException e) {
            throw new TicketPrinterException(e.getMessage(), e);
        }
    }
   
    public String getPrinterName() {
        return m_sName;
    }
    public String getPrinterDescription() {
        return null;
    }   
    public JComponent getPrinterComponent() {
        return null;
    }
    public void reset() {
    }
    
    public void printImage(BufferedImage image) {
        try {
            if (m_printer.getCapRecBitmap()) { // si podemos imprimir bitmaps.
                startTransaction();
                
                File f = File.createTempFile("jposimg", ".png");
                OutputStream out = new FileOutputStream(f);
                out.write(ImageUtils.writeImage(image));
                out.close();
                
                m_printer.printBitmap(POSPrinterConst.PTR_S_RECEIPT, f.getAbsolutePath(), POSPrinterConst.PTR_BM_ASIS, POSPrinterConst.PTR_BM_CENTER);
            }
        } catch (IOException eIO) {
        } catch (JposException e) {
        }
    }
    
    public void printBarCode(String sType, String sCode) {
        try {
            if (m_printer.getCapRecBarCode()) { // si podemos imprimir codigos de barras
                startTransaction();
                //   print a Code 3 of 9 barcode with the data "123456789012" encoded
                //   the 10 * 100, 60 * 100 parameters below specify the barcode's height and width
                //   in the metric map mode (1cm tall, 6cm wide)
                m_printer.printBarCode(POSPrinterConst.PTR_S_RECEIPT, sCode, POSPrinterConst.PTR_BCS_EAN13, 10 * 100, 60 * 100, POSPrinterConst.PTR_BC_CENTER, POSPrinterConst.PTR_BC_TEXT_BELOW);
            }
        } catch (JposException e) {
        }
    }
    
    public void beginLine(int iTextSize) {
        m_sline = new StringBuffer();
        if (iTextSize == DevicePrinter.SIZE_0) {
            m_sline.append(JPOS_SIZE0);
        } else if (iTextSize == DevicePrinter.SIZE_1) {
            m_sline.append(JPOS_SIZE1);
        } else if (iTextSize == DevicePrinter.SIZE_2) {
            m_sline.append(JPOS_SIZE2);
        } else if (iTextSize == DevicePrinter.SIZE_3) {
            m_sline.append(JPOS_SIZE3);
        } else {
            m_sline.append(JPOS_SIZE0);
        }
    }
    
    public void printText(int iStyle, String sText) {
        
        if ((iStyle & DevicePrinter.STYLE_BOLD) != 0) {
            m_sline.append(JPOS_BOLD);
        }
        if ((iStyle & DevicePrinter.STYLE_UNDERLINE) != 0) {
            m_sline.append(JPOS_UNDERLINE);
        }
        m_sline.append(sText);
    }
    
    public void endLine() {
        
        m_sline.append(JPOS_LF);
        try {
            startTransaction();
            m_printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, m_sline.toString());
        } catch (JposException e) {
        }
        m_sline = null;
    }
    
    public void printCutPartial() {
        try {
            startTransaction();
            m_printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, JPOS_CUT);
            
            // fin de transaccion
            m_printer.transactionPrint(POSPrinterConst.PTR_S_RECEIPT, POSPrinterConst.PTR_TP_NORMAL);
            m_bTransaction = false;
        } catch (JposException e) {
        }
    }
    
    private void startTransaction() throws JposException {
        if (!m_bTransaction) {
            m_printer.transactionPrint(POSPrinterConst.PTR_S_RECEIPT, POSPrinterConst.PTR_TP_TRANSACTION);
            m_bTransaction = true;
        }
    }
     
    public void openDrawer() {
        try {
            m_drawer.openDrawer();
        } catch (JposException e) {
        }
    }
    
    public void finalize() {
        
        try {       
            m_printer.setDeviceEnabled(false);
            m_printer.release();
            m_printer.close();
            m_drawer.setDeviceEnabled(false);
            m_drawer.release();
            m_drawer.close();
        } catch (JposException e) {
        }
    }    
}
