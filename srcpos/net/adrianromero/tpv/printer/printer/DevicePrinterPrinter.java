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

package net.adrianromero.tpv.printer.printer;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterJob;
import javax.swing.JComponent;
import net.adrianromero.tpv.printer.DevicePrinter;
import net.adrianromero.tpv.printer.ticket.BasicTicket;

public class DevicePrinterPrinter implements DevicePrinter {
    
    private String m_sName;
    private BasicTicket m_ticketcurrent;
    
    /** Creates a new instance of DevicePrinterPrinter */
    public DevicePrinterPrinter() {
        m_sName = "Printer"; // "AppLocal.getIntString("Printer.Screen");
        m_ticketcurrent = null;
    }
    
    
    private void ensureTicket() {
        if (m_ticketcurrent == null) {
            m_ticketcurrent = new BasicTicket();
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
        m_ticketcurrent = null;
    }
    
    // INTERFAZ PRINTER 2
    public void printImage(BufferedImage image) {
        ensureTicket();
        m_ticketcurrent.printImage(image);
    }
    public void printBarCode(String sType, String sCode) {
        ensureTicket();
        m_ticketcurrent.printBarCode(sType, sCode);
    }
    public void beginLine(int iTextSize) {
        ensureTicket();
        m_ticketcurrent.beginLine(iTextSize);
    }
    public void printText(int iStyle, String sText) {
        m_ticketcurrent.printText(iStyle, sText);
    }
    public void endLine() {
        m_ticketcurrent.endLine();
    }
    public void printCutPartial() {
        ensureTicket();
        
        // Imprimimos
        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable(new PrintableTicket(m_ticketcurrent));
        printJob.setJobName("Librepos document");
        if (printJob.printDialog()) {
            try {
                printJob.print();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        m_ticketcurrent = null;
    }
    
    public void openDrawer() {
        // Una simulacion
        Toolkit.getDefaultToolkit().beep();
    }
    
}
