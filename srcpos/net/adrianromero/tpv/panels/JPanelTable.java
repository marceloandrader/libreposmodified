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

package net.adrianromero.tpv.panels;

import java.awt.*;
import javax.swing.*;
import net.adrianromero.tpv.forms.JPanelView;
import net.adrianromero.tpv.forms.AppView;
import net.adrianromero.tpv.forms.AppLocal;
import net.adrianromero.data.loader.Vectorer;
import net.adrianromero.data.loader.IRenderString;
import net.adrianromero.basic.BasicException;
import net.adrianromero.data.gui.JSaver;
import net.adrianromero.data.gui.MessageInf;
import net.adrianromero.data.gui.JCounter;
import net.adrianromero.data.gui.JLabelDirty;
import net.adrianromero.data.gui.JListNavigator;
import net.adrianromero.data.gui.JNavigator;
import net.adrianromero.data.gui.ListCellRendererBasic;
import net.adrianromero.data.loader.ComparatorCreator;
import net.adrianromero.data.user.BrowsableEditableData;
import net.adrianromero.data.user.DirtyManager;
import net.adrianromero.data.user.EditorRecord;
import net.adrianromero.data.user.ListProvider;
import net.adrianromero.data.user.SaveProvider;

public abstract class JPanelTable extends JPanel implements JPanelView {
    
    protected BrowsableEditableData m_bd;    
    protected DirtyManager m_Dirty;    
    protected AppView m_App;
    
    /** Creates new form JPanelTableEditor */
    public JPanelTable(AppView oApp) {
        m_App = oApp;
        m_Dirty = new DirtyManager();
        m_bd = null;
        initComponents();
    }
    
    private void initNavigation() {
        
        if (m_bd == null) {
            
            // init browsable editable data
            m_bd = new BrowsableEditableData(getListProvider(), getSaveProvider(), getEditor(), m_Dirty);

            // Add the filter panel
            Component c = getFilter();
            if (c != null) {
                add(c, BorderLayout.NORTH);
            }

            // Add the editor
            c = getEditor().getComponent();
            if (c != null) {
                m_jContEditor.add(c, BorderLayout.CENTER);            
            }

            // el panel este
            ListCellRenderer cr = getListCellRenderer();
            if (cr != null) {
                JListNavigator nl = new JListNavigator(m_bd);
                if (cr != null) nl.setCellRenderer(cr);
                m_jContEditor.add(nl, java.awt.BorderLayout.WEST);
            }

            // add toolbar extras
            c = getToolbarExtras();
            if (c != null) {
                m_jToolbar.add(c);
            }

            // La Toolbar
            m_jToolbar.add(new JLabelDirty(m_Dirty));
            m_jToolbar.add(new JCounter(m_bd));
            m_jToolbar.add(new JNavigator(m_bd, getVectorer(), getComparatorCreator()));
            m_jToolbar.add(new JSaver(m_bd));
        }
    }
    
    public Component getToolbarExtras() {
        return null;
    }

    public Component getFilter() {    
        return null;
    }
    
    public abstract EditorRecord getEditor();
    
    public abstract ListProvider getListProvider();
    
    public abstract SaveProvider getSaveProvider();
    
    public Vectorer getVectorer() {
        return null;
    }
    
    public ComparatorCreator getComparatorCreator() {
        return null;
    }
    
    public ListCellRenderer getListCellRenderer() {
        return null;
    }

    public JComponent getComponent() {
        return this;
    }

    public void activate() throws BasicException {
        initNavigation();
        m_bd.actionLoad();
    }    
    
    public boolean deactivate() {

        try {
            return m_bd.actionClosingForm(this);
        } catch (BasicException eD) {
            MessageInf msg = new MessageInf(MessageInf.SGN_NOTICE, AppLocal.getIntString("message.CannotMove"), eD);
            msg.show(this);
            return false;
        }
    }  
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel2 = new javax.swing.JPanel();
        m_jContEditor = new javax.swing.JPanel();
        m_jToolbar = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(5, 5, 5, 5)));
        jPanel2.setLayout(new java.awt.BorderLayout());

        m_jContEditor.setLayout(new java.awt.BorderLayout());

        m_jContEditor.add(m_jToolbar, java.awt.BorderLayout.NORTH);

        jPanel2.add(m_jContEditor, java.awt.BorderLayout.CENTER);

        add(jPanel2, java.awt.BorderLayout.CENTER);

    }
    // </editor-fold>//GEN-END:initComponents
  
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel m_jContEditor;
    private javax.swing.JPanel m_jToolbar;
    // End of variables declaration//GEN-END:variables
    
}
