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

import java.awt.event.ActionListener;
import java.util.List;
import net.adrianromero.basic.BasicException;
import net.adrianromero.data.gui.ComboBoxValModel;
import net.adrianromero.data.loader.QBFCompareEnum;
import net.adrianromero.data.loader.SentenceList;
import net.adrianromero.data.user.EditorCreator;
import net.adrianromero.tpv.forms.AppLocal;
import net.adrianromero.tpv.forms.AppView;
import net.adrianromero.tpv.forms.SentenceContainer;

public class JParamsLocation extends javax.swing.JPanel implements EditorCreator {
    
    private SentenceList m_sentlocations;
    private ComboBoxValModel m_LocationsModel;    
    
    /** Creates new form JParamsLocation */
    public JParamsLocation(AppView app) {
        initComponents();
        
        // El modelo de locales
        m_sentlocations = app.lookupDataLogic(SentenceContainer.class).getLocationsList();
        m_LocationsModel = new ComboBoxValModel();        
    }
    
    public void activate() throws BasicException {
        List a = m_sentlocations.list();
        addFirst(a);
        m_LocationsModel = new ComboBoxValModel(a);
        m_LocationsModel.setSelectedFirst();
        m_jLocation.setModel(m_LocationsModel); // refresh model   
    }
    
    protected void addFirst(List a) {
        // do nothing
    }
    
    public void addActionListener(ActionListener l) {
        m_jLocation.addActionListener(l);
    }
    
    public void removeActionListener(ActionListener l) {
        m_jLocation.removeActionListener(l);
    }
    
    public Object createValue() throws BasicException {
        
        return new Object[] {
            m_LocationsModel.getSelectedKey() == null ? QBFCompareEnum.COMP_NONE : QBFCompareEnum.COMP_EQUALS, m_LocationsModel.getSelectedKey()
        };
    }    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        m_jLocation = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();

        setLayout(null);

        setBorder(javax.swing.BorderFactory.createTitledBorder(AppLocal.getIntString("label.bywarehouse")));
        setPreferredSize(new java.awt.Dimension(400, 60));
        add(m_jLocation);
        m_jLocation.setBounds(150, 20, 220, 20);

        jLabel8.setText(AppLocal.getIntString("label.warehouse"));
        add(jLabel8);
        jLabel8.setBounds(20, 20, 110, 14);

    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel8;
    private javax.swing.JComboBox m_jLocation;
    // End of variables declaration//GEN-END:variables
    
}
