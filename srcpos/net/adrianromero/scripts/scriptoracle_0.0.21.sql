--    Librepos is a point of sales application designed for touch screens.
--    Copyright (C) 2005 Adrian Romero Corchado.
--    http://sourceforge.net/projects/librepos
--
--    This program is free software; you can redistribute it and/or modify
--    it under the terms of the GNU General Public License as published by
--    the Free Software Foundation; either version 2 of the License, or
--    (at your option) any later version.
--
--    This program is distributed in the hope that it will be useful,
--    but WITHOUT ANY WARRANTY; without even the implied warranty of
--    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
--    GNU General Public License for more details.
--
--    You should have received a copy of the GNU General Public License
--    along with this program; if not, write to the Free Software
--    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

-- Librepos Database upgrade script for Oracle from version 0.0.21 to 0.0.22
-- v0.0.22

UPDATE LIBREPOS SET VERSION = '0.0.22';

ALTER TABLE TICKETS ADD COLUMN STATUS INTEGER DEFAULT 0 NOT NULL;
ALTER TABLE PRODUCTS ADD COLUMN CODETYPE(100) VARCHAR;
