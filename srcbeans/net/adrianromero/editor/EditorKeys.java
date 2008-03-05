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

package net.adrianromero.editor;

public interface EditorKeys {
    
    public final static int MODE_STRING = 0;
    public final static int MODE_DOUBLE = 1;
    public final static int MODE_DOUBLE_POSITIVE = 2;
    public final static int MODE_INTEGER = 3;
    public final static int MODE_INTEGER_POSITIVE = 4;
    
    public void setActive(EditorComponent e, int imode);
    public void setInactive(EditorComponent e);
}
