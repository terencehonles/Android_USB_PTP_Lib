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

import java.io.PrintStream;


/**
 * This class is used for PTP messages consisting of only a set of
 * thirty-two bit parameters, such as commands, responses, and events.
 *
 * @version $Id: ParamVector.java,v 1.4 2001/04/12 23:13:00 dbrownell Exp $
 * @author David Brownell
 */
public class ParamVector extends Container
{
    ParamVector (byte buf [], NameFactory f)
	{ super (buf, f); }

    ParamVector (byte buf [], int len, NameFactory f)
	{ super (buf, len, f); }

    /** Returns the first positional parameter. */
    public final int getParam1 ()
    	{ return getS32 (12); }

    /** Returns the second positional parameter. */
    public final int getParam2 ()
    	{ return getS32 (16); }

    /** Returns the third positional parameter. */
    public final int getParam3 ()
    	{ return getS32 (20); }

    /** Returns the number of parameters in this data block */
    public final int getNumParams ()
	{ return (length - MIN_LEN) / 4; }
	

    // no params
    static final int MIN_LEN = HDR_LEN;

    // allegedly some responses could have five params
    static final int MAX_LEN = 32;


    // NOTE:  params in the spec are numbered from one, not zero
    int getParam (int i)
	{ return getS32 (12 + (4 * i)); }
    
    void dump (PrintStream out)
    {
	out.print (this.toString ());
    }
}
