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

package net.adrianromero.tpv.panelsales.restaurant;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.Component;
import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;

import java.io.Serializable;
import net.adrianromero.format.Formats;

public class JCalendarItemRenderer extends javax.swing.JPanel implements ListCellRenderer, Serializable {

    protected static Border noFocusBorder;
    
    private boolean m_bDone = false;
    
    /** Creates new form JCalendarItemRenderer */
    public JCalendarItemRenderer() {
        
        super();
        if (noFocusBorder == null) {
            noFocusBorder = new EmptyBorder(1, 1, 1, 1);
        }
        
        initComponents();
        
        m_jTime.setFont(new Font("SansSerif", Font.BOLD, 11)); // HORA
        m_jTitle.setFont(new Font("SansSerif", Font.BOLD, 11)); // TITULO
        m_jDescription.setFont(new Font("SansSerif", Font.ITALIC, 11)); // TEXTO EXPLICATIVO

        setOpaque(true);
        setBorder(noFocusBorder);
    }
    
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        
        setComponentOrientation(list.getComponentOrientation());
        
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            m_jTime.setForeground(list.getSelectionForeground());
            m_jTitle.setForeground(list.getSelectionForeground());
            m_jDescription.setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            m_jTime.setForeground(Color.BLUE);
            m_jTitle.setForeground(list.getForeground());
            m_jDescription.setForeground(list.getForeground());
        }

        if (value == null) {
            m_jTime.setText("");
            m_jTitle.setText("");
            m_jChairs.setText("");
            m_bDone = false;
            m_jDescription.setText("");
        } else {
            Object[] avalue = (Object []) value;
            m_jTime.setText(Formats.TIME.formatValue(avalue[2]));
            m_jTitle.setText(Formats.STRING.formatValue(avalue[3]));
            m_jChairs.setText(Formats.INT.formatValue(avalue[4]));
            m_bDone = ((Boolean) avalue[5]).booleanValue();
            m_jDescription.setText(Formats.STRING.formatValue(avalue[6]));
        }
            
//        if (value instanceof Icon) {
//            setIcon((Icon)value);
//            setText("");
//        } else {
//            setIcon(null);
//            setText((value == null) ? "" : value.toString());
//        }

        setEnabled(list.isEnabled());
        setFont(list.getFont());
        setBorder((cellHasFocus) ? UIManager.getBorder("List.focusCellHighlightBorder") : noFocusBorder);

        return this;
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (m_bDone) {
            Insets in = getInsets();
            g.drawLine(in.left, 10, getWidth() - in.right, 10);        
        }
    }
    
    /**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a> 
     * for more information.
     *
     * @since 1.5
     * @return <code>true</code> if the background is completely opaque
     *         and differs from the JList's background;
     *         <code>false</code> otherwise
     */
//    public boolean isOpaque() { 
//        
//        Color back = getBackground();
//        Component p = getParent(); 
//        if (p != null) { 
//            p = p.getParent(); 
//        }
//        // p should now be the JList. 
//        boolean colorMatch = (back != null) && (p != null) && 
//            back.equals(p.getBackground()) && 
//			p.isOpaque();
//        return !colorMatch && super.isOpaque(); 
//    }
    
   /**
    * Overridden for performance reasons.
    */
//    public void validate() {}
//    public void invalidate() {}
//    public void repaint() {}
//    public void revalidate() {}
//    public void repaint(long tm, int x, int y, int width, int height) {}
//    public void repaint(Rectangle r) {}
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {}
    public void firePropertyChange(String propertyName, byte oldValue, byte newValue) {}
    public void firePropertyChange(String propertyName, char oldValue, char newValue) {}
    public void firePropertyChange(String propertyName, short oldValue, short newValue) {}
    public void firePropertyChange(String propertyName, int oldValue, int newValue) {}
    public void firePropertyChange(String propertyName, long oldValue, long newValue) {}
    public void firePropertyChange(String propertyName, float oldValue, float newValue) {}
    public void firePropertyChange(String propertyName, double oldValue, double newValue) {}
    public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {}

    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        m_jDescription = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        m_jTime = new javax.swing.JLabel();
        m_jTitle = new javax.swing.JLabel();
        m_jChairs = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        m_jDescription.setText("<html>Y aqu\u00ed se escribe el texto de lo que se puede y no se puede hacer para controlar lo que mostramos en esta cacharra.");
        m_jDescription.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        m_jDescription.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(5, 20, 5, 20)));
        add(m_jDescription, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel1.setOpaque(false);
        m_jTime.setForeground(new java.awt.Color(0, 0, 255));
        m_jTime.setText("10:20");
        jPanel1.add(m_jTime, java.awt.BorderLayout.WEST);

        m_jTitle.setText(" Esto es una prueba de como puede ser");
        m_jTitle.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(0, 5, 0, 0)));
        jPanel1.add(m_jTitle, java.awt.BorderLayout.CENTER);

        m_jChairs.setText("5");
        jPanel1.add(m_jChairs, java.awt.BorderLayout.EAST);

        add(jPanel1, java.awt.BorderLayout.NORTH);

    }
    // </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JPanel jPanel1;
    javax.swing.JLabel m_jChairs;
    javax.swing.JLabel m_jDescription;
    javax.swing.JLabel m_jTime;
    javax.swing.JLabel m_jTitle;
    // End of variables declaration//GEN-END:variables
    
}
