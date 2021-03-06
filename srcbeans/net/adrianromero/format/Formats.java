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

package net.adrianromero.format;

import java.text.*;
import java.util.Date;
import net.adrianromero.basic.BasicException;

public abstract class Formats {
    
    public final static Formats NULL = new FormatsNULL();
    public final static Formats INT = new FormatsINT();
    public final static Formats STRING = new FormatsSTRING();
    public final static Formats DOUBLE = new FormatsDOUBLE();
    public final static Formats CURRENCY = new FormatsCURRENCY();
    public final static Formats PERCENT = new FormatsPERCENT();
    public final static Formats BOOLEAN = new FormatsBOOLEAN();
    public final static Formats TIMESTAMP = new FormatsTIMESTAMP();
    public final static Formats DATE = new FormatsDATE();
    public final static Formats TIME = new FormatsTIME();
    
    private static NumberFormat m_integerformat = NumberFormat.getIntegerInstance();
    private static NumberFormat m_doubleformat = NumberFormat.getNumberInstance();
    private static NumberFormat m_currencyformat = NumberFormat.getCurrencyInstance();
    private static NumberFormat m_percentformat = new DecimalFormat("#,##0.##%");
    
    private static DateFormat m_dateformat = DateFormat.getDateInstance();
    private static DateFormat m_timeformat = DateFormat.getTimeInstance();
    private static DateFormat m_datetimeformat = DateFormat.getDateTimeInstance();
   
    
    /** Creates a new instance of Formats */
    protected Formats() {
    }
    
    public String formatValue(Object value) {
        if (value == null) {
            return "";
        } else {
            return formatValueInt(value);
        }
    }
    public Object parseValue(String value) throws BasicException {
        if (value == null || "".equals(value)) {
            return null;
        } else {
            try {
                return parseValueInt(value);
            } catch (ParseException e) {
                throw new BasicException(e.getMessage(), e);
            }
        }  
    }
            
    
    protected abstract String formatValueInt(Object value);
    protected abstract Object parseValueInt(String value) throws ParseException;
    public abstract int getAlignment();

    private static final class FormatsNULL extends Formats {       
        protected String formatValueInt(Object value) {
            return null;
        }       
        protected Object parseValueInt(String value) throws ParseException {
            return null;
        }
        public int getAlignment() {
            return javax.swing.SwingConstants.LEFT;
        }
    }
    private static final class FormatsINT extends Formats {       
        protected String formatValueInt(Object value) {
            return m_integerformat.format(((Number) value).longValue());
        }   
        protected Object parseValueInt(String value) throws ParseException {
            return new Integer(m_integerformat.parse(value).intValue());
        }
        public int getAlignment() {
            return javax.swing.SwingConstants.RIGHT;
        }
    }
    private static final class FormatsSTRING extends Formats {       
        protected String formatValueInt(Object value) {
            return (String) value;
        }   
        protected Object parseValueInt(String value) throws ParseException {
            return value;
        }
        public int getAlignment() {
            return javax.swing.SwingConstants.LEFT;
        }
    }    
    private static final class FormatsDOUBLE extends Formats {       
        protected String formatValueInt(Object value) {
            return m_doubleformat.format(((Number) value).doubleValue());
        }   
        protected Object parseValueInt(String value) throws ParseException {
            return new Double(m_doubleformat.parse(value).doubleValue());
        }
        public int getAlignment() {
            return javax.swing.SwingConstants.RIGHT;
        }
    }    
    private static final class FormatsPERCENT extends Formats {       
        protected String formatValueInt(Object value) {
            //return NumberFormat.getPercentInstance().format(((Number) value).doubleValue());
            return m_percentformat.format(((Number) value).doubleValue());
        }   
        protected Object parseValueInt(String value) throws ParseException {
            try {
                //return new Double(NumberFormat.getPercentInstance().parse(value).doubleValue());
                return new Double(m_percentformat.parse(value).doubleValue());
            } catch (ParseException e) {
                // Segunda oportunidad como numero normalito
                return new Double(m_doubleformat.parse(value).doubleValue() / 100);
            }
        }
        public int getAlignment() {
            return javax.swing.SwingConstants.RIGHT;
        }
    }  
    private static final class FormatsCURRENCY extends Formats {       
        protected String formatValueInt(Object value) {
            return m_currencyformat.format(((Number) value).doubleValue());
        }   
        protected Object parseValueInt(String value) throws ParseException {
            try {
                return new Double(m_currencyformat.parse(value).doubleValue());
            } catch (ParseException e) {
                // Segunda oportunidad como numero normalito
                return new Double(m_doubleformat.parse(value).doubleValue());
            }
        }
        public int getAlignment() {
            return javax.swing.SwingConstants.RIGHT;
        }
    }  
    private static final class FormatsBOOLEAN extends Formats {       
        protected String formatValueInt(Object value) {
            return ((Boolean) value).toString();
        }   
        protected Object parseValueInt(String value) throws ParseException {
            return Boolean.valueOf(value);
        }
        public int getAlignment() {
            return javax.swing.SwingConstants.CENTER;
        }
    }    
    private static final class FormatsTIMESTAMP extends Formats {       
        protected String formatValueInt(Object value) {
            return m_datetimeformat.format((Date) value);
        }   
        protected Object parseValueInt(String value) throws ParseException {
            try {
                return m_datetimeformat.parse(value);
            } catch (ParseException e) {
                // segunda oportunidad como fecha normalita
                return m_dateformat.parse(value);
            }
        }
        public int getAlignment() {
            return javax.swing.SwingConstants.CENTER;
        }
    }
    private static final class FormatsDATE extends Formats {       
        protected String formatValueInt(Object value) {
            return m_dateformat.format((Date) value);
        }   
        protected Object parseValueInt(String value) throws ParseException {
            return m_dateformat.parse(value);
        }
        public int getAlignment() {
            return javax.swing.SwingConstants.CENTER;
        }
    }  
    private static final class FormatsTIME extends Formats {       
        protected String formatValueInt(Object value) {
            return m_timeformat.format((Date) value);
        }   
        protected Object parseValueInt(String value) throws ParseException {
            return m_timeformat.parse(value);
        }
        public int getAlignment() {
            return javax.swing.SwingConstants.CENTER;
        }
    }      
}
