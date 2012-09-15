// Copyright 2000 by David Brownell <dbrownell@users.sourceforge.net>
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
//

package com.ptplib.usbcamera;


/**
 * The optional middle phase of a PTP transaction involves sending
 * data to or from the responder.
 *
 * @version $Id: Data.java,v 1.4 2001/04/12 23:13:00 dbrownell Exp $
 * @author David Brownell
 */
public class Data extends Container
{
    private boolean	in;

    public Data (NameFactory f) { this (true, null, 0, f); }

    public Data (boolean isIn, byte buf [], NameFactory f)
	{ super (buf, f); in = isIn; }

    public Data (boolean isIn, byte buf [], int len, NameFactory f)
	{ super (buf, len, f); in = isIn; }

    boolean isIn ()
	{ return in; }

    public String getCodeName (int code)
    {
	return factory.getOpcodeString (code);
    }

    public String toString ()
    {
	StringBuffer	temp = new StringBuffer ();
	int		code = getCode ();

	temp.append ("{ ");
	temp.append (getBlockTypeName (getBlockType ()));
	if (in)
	    temp.append (" IN");
	else
	    temp.append (" OUT");
	temp.append ("; len ");
	temp.append (Integer.toString (getLength ()));
	temp.append ("; ");
	temp.append (factory.getOpcodeString (code));
	temp.append ("; xid ");
	temp.append (Integer.toString (getXID ()));
	temp.append ("}");
	return temp.toString ();
    }
}

