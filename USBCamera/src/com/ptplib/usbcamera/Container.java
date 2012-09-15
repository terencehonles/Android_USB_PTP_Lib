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
 * PTP Command, Data, Response, and Event blocks use a "Generic Container
 * Structure" as a packet header.
 *
 * <p> Note that since the string values to which various codes map
 * have been interned, you may safely rely on "==" and "!=" when you
 * make comparisons against constant values.
 *
 * @version $Id: Container.java,v 1.8 2001/04/12 23:13:00 dbrownell Exp $
 * @author David Brownell
 */
abstract public class Container extends Buffer
{
	public static final int BLOCK_TYPE_COMMAND 	= 1;
	public static final int BLOCK_TYPE_DATA 	= 2;
	public static final int BLOCK_TYPE_RESPONSE = 3;
	public static final int BLOCK_TYPE_EVENT 	= 4;
	
    protected NameFactory factory;

    // get/put object handles (not 0, ~0) using session context
    // Session		session;

    // fixed header layout, per annex D
    // NOTE: session id (9.3.2) is implicit: no multisession over USB
    //	@ 0, u32 length
    //	@ 4, u16 buffer type
    //	@ 6, u16 code
    //	@ 8, u32 xid
    // TOTAL:  12 bytes
    static final int	HDR_LEN = 12;

    public Container (byte buf [], NameFactory f)
	{ super (buf, buf.length); factory = f; }

    public Container (byte buf [], int len, NameFactory f)
	{ super (buf, len); factory = f; }
    
    // package private
    public void putHeader (int len, int type, int code, int xid)
    {
	if (offset != 0)
	    throw new IllegalStateException ();
	put32 (len);
	put16 (type);
	put16 (code);
	put32 (xid);
    }

    
    /**
     * Provides a printable representation of data from the block
     * header, including block type, code, and transaction ID.
     */
    public String toString ()
    {
	StringBuffer	temp = new StringBuffer ();
	String		type = getBlockTypeName (getBlockType ());
	int		code = getCode ();

	temp.append ("{ ");
	temp.append (type);
	temp.append ("; len ");
	temp.append (Integer.toString (getLength ()));
	temp.append ("; ");
	temp.append (getCodeName (code));
	temp.append ("; xid ");
	temp.append (Integer.toString (getXID ()));

	// inelegant, but ...
	if (this instanceof ParamVector) {
	    ParamVector	vec = (ParamVector) this;
	    int		nparams = vec.getNumParams ();

	    if (nparams > 0) {
		temp.append ("; ");
		for (int i = 0; i < nparams; i++) {
		    if (i != 0)
			temp.append (" ");
		    temp.append ("0x");
		    temp.append (Integer.toHexString (vec.getParam (i)));
		}
	    }
	}

	temp.append (" }");
	return temp.toString ();
    }

    void dump (PrintStream out)
    {
	 out.println (toString ());
    }

    
    void parse ()
    {
	offset = HDR_LEN;
    }

    /** Returns the overall length of this data block, including header. */
    public int getLength ()
    	{ return /* unsigned */ getS32 (0); }

    /**
     * Returns the overall type of this data block as a coded integer.
     */
    public final int getBlockType ()
    	{ return getU16 (4); }

    /**
     * Returns an interned string, normally "command", "data", "response",
     * or "event", corresponding to the coded type.  Unrecognized or
     * undefined values are returned as interned Integer.toHexString values.
     */
    public static final String getBlockTypeName (int type)
    {
	switch (type) {
	    case 1:	return "command";
	    case 2:	return "data";
	    case 3:	return "response";
	    case 4:	return "event";
	    default:	return Integer.toHexString (type).intern ();
	}
    }

    /**
     * Returns the operation, response, or event code of this block.
     */
    public final int getCode ()
    {
	return getU16 (6);
    }

    /**
     * Returns an interned string identifying the type of code field,
     * such as "OperationCode", "ResponseCode", "ObjectFormatCode",
     * "EventCode", or "DevicePropsCode".  Unrecognized or undefined
     * values are returned as interned Integer.toHexString values.
     */
    public static final String getCodeType (int code)
    {
	switch (code >> 12) {
	    case 1:	return "OperationCode";
	    case 2:	return "ResponseCode";
	    case 3:	return "ObjectFormatCode";
	    case 4:	return "EventCode";
	    case 5:	return "DevicePropCode";
	    case 8 + 1:	return "Vendor-OpCode";
	    case 8 + 2:	return "Vendor-ResponseCode";
	    case 8 + 3:	return "Vendor-FormatCode";
	    case 8 + 4:	return "Vendor-EventCode";
	    case 8 + 5:	return "Vendor-PropCode";
	    default:	return Integer.toHexString (code >> 12).intern ();
	}
    }

    /**
     * Subclasses override this to map PTP codes to their names; the
     * results are always interned strings, so that they can be efficiently
     * compared ("=", "!=") against constants.  Such per-instance methods
     * permit type-specific subclasses (and vendor extensions) to name their
     * code values, invoking superclass methods to name all other codes.
     */
    public String getCodeName (int code)
    {
	return getCodeString (code);
    }

    /**
     * Returns an interned string with name of this container's code.
     */
    public final String getCodeString ()
    {
	return getCodeName (getCode ()).intern ();
    }

    /**
     * Returns an interned string with the hexadecimal value of
     * the specified container code.
     */
    public static String getCodeString (int code)
    {
	return Integer.toHexString (code).intern ();
    }

    /**
     * Returns the ID of the transaction this block is associated with.
     */
    public final int getXID ()
    	{ return getS32 (8); }
}
