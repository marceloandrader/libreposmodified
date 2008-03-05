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

package net.adrianromero.tpv.catalog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import net.adrianromero.beans.JFlowPanel;

public class JCatalogTab extends javax.swing.JPanel {
    
    private JFlowPanel flowpanel;
    
    /** Creates new form JCategoryProducts */
    public JCatalogTab() {
        initComponents();

        flowpanel = new JFlowPanel();
        JScrollPane scroll = new JScrollPane(flowpanel);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(35, 35));
        
        add(scroll, BorderLayout.CENTER);
    }
    
    public void setEnabled(boolean value) {
        flowpanel.setEnabled(value);
        super.setEnabled(value);
    }
    
    public void addButton(Image img, String name, ActionListener al) {
        JButton btn = new JButton();
        btn.setText(name);
        btn.setIcon(new ImageIcon(img));
        btn.setFocusPainted(false);
        btn.setFocusable(false);
        btn.setRequestFocusEnabled(false);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn.setMargin(new Insets(2, 2, 2, 2));
        btn.setMaximumSize(new Dimension(70, 60));
        btn.setPreferredSize(new Dimension(70, 60));
        btn.setMinimumSize(new Dimension(70, 60));
        btn.addActionListener(al);
        flowpanel.add(btn);        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());

        setPreferredSize(new java.awt.Dimension(0, 233));
    }
    // </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
}
