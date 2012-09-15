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

import android.widget.TextView;


/**
 * DeviceProperty values wrap various types of integers and
 * arrays of integers, and strings.  It's sort of like a
 * CORBA "Any", pairing a typecode and value, except that
 * the typecode is known in advance so it's never marshaled.
 *
 * @see DevicePropDesc
 *
 * @version $Id: DevicePropValue.java,v 1.4 2001/04/12 23:13:00 dbrownell Exp $
 * @author David Brownell
 */
public class DevicePropValue extends Data
{
    int			typecode;
    Object		value;

    DevicePropValue (int tc, NameFactory f)
	{ super (f); typecode = tc; }

    private DevicePropValue (int tc, Object obj, NameFactory f)
    {
	super (f);
	typecode = tc;
	value = obj;

	// FIXME:  marshal value into the buffer.
    }

    public Object getValue ()
	{ return value; }
    
    public int getTypeCode ()
	{ return typecode; }

    void parse ()
    {
	value = get (typecode, this);
    }

    void dump (PrintStream out)
    {
	out.print ("Type: ");
	out.print (getTypeName (typecode));
	out.print (", Value: ");
	out.println (value.toString ());
    }

    void showInTextView (TextView tv)
    {
    tv.setText ("Type: ");
    tv.append (getTypeName (typecode));
	tv.append (", Value: ");
	tv.append  ("\n");
	tv.append (value.toString ());
    }
    
    public String getCodeName (int code)
    {
	return getTypeName (code);
    }

    static Object get (int code, Buffer buf)
    {
	Object		value;

	switch (code) {
	    case s8:
		return new Integer (buf.nextS8 ());
	    case u8:
		return new Integer (buf.nextU8 ());
	    case s16:
		return new Integer (buf.nextS16 ());
	    case u16:
		return new Integer (buf.nextU16 ());
	    case s32:
		return new Integer (buf.nextS32 ());
	    case u32:
		return new Long (0x0ffFFffFFL & buf.nextS32 ());
	    case s64:
		return new Long (buf.nextS64 ());
	    case u64:
		// FIXME: unsigned masquerading as signed ...
		return new Long (buf.nextS64 ());

	    // case s128: case u128:

	    case s8array:
		return buf.nextS8Array ();
	    case u8array:
		return buf.nextU8Array ();
	    case s16array:
		return buf.nextS16Array ();
	    case u16array:
		return buf.nextU16Array ();
	    case u32array:
		// FIXME: unsigned masquerading as signed ...
	    case s32array:
		return buf.nextS32Array ();
	    case u64array:
		// FIXME: unsigned masquerading as signed ...
	    case s64array:
		return buf.nextS64Array ();
	    // case s128array: case u128array:

	    case string:
		return buf.nextString ();
	}
	throw new IllegalArgumentException ();
    }

    // code values, per 5.3 table 3

    public static final int s8 = 0x0001;
    /** Unsigned eight bit integer */
    public static final int u8 = 0x0002;
    public static final int s16 = 0x0003;
    /** Unsigned sixteen bit integer */
    public static final int u16 = 0x0004;
    public static final int s32 = 0x0005;
    /** Unsigned thirty two bit integer */
    public static final int u32 = 0x0006;
    public static final int s64 = 0x0007;
    /** Unsigned sixty four bit integer */
    public static final int u64 = 0x0008;
    public static final int s128 = 0x0009;
    /** Unsigned one hundred twenty eight bit integer */
    public static final int u128 = 0x000a;
    public static final int s8array = 0x4001;
    /** Array of unsigned eight bit integers */
    public static final int u8array = 0x4002;
    public static final int s16array = 0x4003;
    /** Array of unsigned sixteen bit integers */
    public static final int u16array = 0x4004;
    public static final int s32array = 0x4005;
    /** Array of unsigned thirty two bit integers */
    public static final int u32array = 0x4006;
    public static final int s64array = 0x4007;
    /** Array of unsigned sixty four bit integers */
    public static final int u64array = 0x4008;
    public static final int s128array = 0x4009;
    /** Array of unsigned one hundred twenty eight bit integers */
    public static final int u128array = 0x400a;
    /** Unicode string */
    public static final int string = 0xffff;

    /**
     * Maps datatype codes to string names.
     * @param code datatype code
     * @return interned string identifying that datatype.
     */
    public static String getTypeName (int code)
    {
	switch (code) {
	    case s8:		return "s8";
	    case u8:		return "u8";
	    case s16:		return "s16";
	    case u16:		return "u16";
	    case s32:		return "s32";
	    case u32:		return "u32";
	    case s64:		return "s64";
	    case u64:		return "u64";
	    case s128:		return "s128";
	    case u128:		return "u128";
	    case s8array:	return "s8array";
	    case u8array:	return "u8array";
	    case s16array:	return "s16array";
	    case u16array:	return "u16array";
	    case s32array:	return "s32array";
	    case u32array:	return "u32array";
	    case s64array:	return "s64array";
	    case u64array:	return "u64array";
	    case s128array:	return "s128array";
	    case u128array:	return "u128array";
	    case string:	return "string";
	}
	return Container.getCodeString (code);
    }
}
