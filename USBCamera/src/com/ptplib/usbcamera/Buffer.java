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
 * A Buffer handles marshaling/unmarshaling rules applicable to PTP
 * over USB.  That's a fairly standard sort of little-endian
 * marshaling, Unicode enabled.
 * In this API, eight and sixteen bit values are passed in the least
 * significant bits of integers, and are sign extended if needed.
 * Unsigned thirty-two and sixty-four bit values are not currently
 * provided; they have the same bit patterns as signed values, but
 * applications must print them differently.
 * One hundred twenty-eight bit integers are not currently supported.
 * These buffers are not to be used concurrently.
 *
 * <p> Strings representing times (<em>YYYYMMDDThhmmss[.s]{,Z,{+,-}hhmm}</em>)
 * are not currently transformed to Java-oriented representations.
 *
 * @version $Id: Buffer.java,v 1.5 2001/04/12 23:13:00 dbrownell Exp $
 * @author David Brownell
 */
public class Buffer
{
    // package private
    public byte data [];
    public int  length;
    public int  offset;

    // package private
    Buffer (byte buf [])
    {
	this (buf, buf.length);
    }

    // package private
    Buffer (byte buf [], int len)
    {
	if (buf != null && (len < 0 || len > buf.length))
	    throw new IllegalArgumentException ();
	if (buf == null && len != 0)
	    throw new IllegalArgumentException ();
	data = buf;
	length = len;
	offset = 0;
    }

    public byte[] getData() {
        return data;
    }

    public int getLength() {
        return length;
    }

    /** Unmarshals a signed 8 bit integer from a fixed buffer offset. */
    protected final int getS8 (int index)
    {
    	return data [index];
    }

    /** Unmarshals an unsigned 8 bit integer from a fixed buffer offset. */
    protected final int getU8 (int index)
    {
    	return 0xff & data [index];
    }

    /** Marshals an 8 bit integer (signed or unsigned) */
    protected final void put8 (int value)
    {
	data [offset++] = (byte) value;
    }

    /** Unmarshals the next signed 8 bit integer */
    protected final int nextS8 ()
    {
    	return data [offset++];
    }

    /** Unmarshals the next unsigned 8 bit integer */
    protected final int nextU8 ()
    {
    	return 0xff & data [offset++];
    }

    /** Unmarshals an array of signed 8 bit integers */
    protected final int [] nextS8Array ()
    {
	int len = /* unsigned */ nextS32 ();
	int retval [] = new int [len];
	for (int i = 0; i < len; i++)
	    retval [i] = nextS8 ();
	return retval;
    }


    /** Unmarshals an array of 8 bit integers */
    protected final int [] nextU8Array ()
    {
	int len = /* unsigned */ nextS32 ();
	int retval [] = new int [len];
	for (int i = 0; i < len; i++)
	    retval [i] = nextU8 ();
	return retval;
    }


    /** Unmarshals a signed 16 bit integer from a fixed buffer offset. */
    protected final int getS16 (int index)
    {
	int retval;

	retval = 0xff & data [index++];
	retval |= data [index] << 8;
	return retval;
    }
    
    /** Unmarshals an unsigned 16 bit integer from a fixed buffer offset. */
    protected final int getU16 (int index)
    {
	int retval;

	retval = 0xff & data [index++];
	retval |= 0xff00 & (data [index] << 8);
	return retval;
    }
    
    /** Marshals a 16 bit integer (signed or unsigned) */
    protected final void put16 (int value)
    {
	data [offset++] = (byte) value;
	data [offset++] = (byte) (value >> 8);
    }

    /** Unmarshals the next signed 16 bit integer */
    protected final int nextS16 ()
    {
	int retval = getS16 (offset);
	offset += 2;
	return retval;
    }

    /** Unmarshals the next unsinged 16 bit integer */
    protected final int nextU16 ()
    {
	int retval = getU16 (offset);
	offset += 2;
	return retval;
    }

    /** Unmarshals an array of signed 16 bit integers */
    protected final int [] nextS16Array ()
    {
	int len = /* unsigned */ nextS32 ();
	int retval [] = new int [len];
	for (int i = 0; i < len; i++)
	    retval [i] = nextS16 ();
	return retval;
    }

    /** Unmarshals an array of unsigned 16 bit integers */
    protected final int [] nextU16Array ()
    {
	int len = /* unsigned */ nextS32 ();
	int retval [] = new int [len];
	for (int i = 0; i < len; i++)
	    retval [i] = nextU16 ();
	return retval;
    }


    /** Unmarshals a signed 32 bit integer from a fixed buffer offset. */
    protected final int getS32 (int index)
    {
	int retval;

	retval  = (0xff & data [index++]) ;
	retval |= (0xff & data [index++]) << 8;
	retval |= (0xff & data [index++]) << 16;
	retval |=         data [index  ]  << 24;

	return retval;
    }

    /** Marshals a 32 bit integer (signed or unsigned) */
    public final void put32 (int value)
    {
	data [offset++] = (byte) value;
	data [offset++] = (byte) (value >> 8);
	data [offset++] = (byte) (value >> 16);
	data [offset++] = (byte) (value >> 24);
    }

    /** Unmarshals the next signed 32 bit integer */
    protected final int nextS32 ()
    {
	int retval = getS32 (offset);
	offset += 4;
	return retval;
    }

    /** Unmarshals an array of signed 32 bit integers. */
    protected final int [] nextS32Array ()
    {
	int len = /* unsigned */ nextS32 ();
	int retval [] = new int [len];
	for (int i = 0; i < len; i++) {
	    retval [i] = nextS32 ();
	}
	return retval;
    }



    /** Unmarshals a signed 64 bit integer from a fixed buffer offset */
    protected final long getS64 (int index)
    {
	long retval = 0xffffffff & getS32 (index);

	retval |= (getS32 (index + 4) << 32);
	return retval;
    }

    /** Marshals a 64 bit integer (signed or unsigned) */
    protected final void put64 (long value)
    {
	put32 ((int) value);
	put32 ((int) (value >> 32));
    }

    /** Unmarshals the next signed 64 bit integer */
    protected final long nextS64 ()
    {
	long retval = getS64 (offset);
	offset += 8;
	return retval;
    }

    /** Unmarshals an array of signed 64 bit integers */
    protected final long [] nextS64Array ()
    {
	int len = /* unsigned */ nextS32 ();
	long retval [] = new long [len];
	for (int i = 0; i < len; i++)
	    retval [i] = nextS64 ();
	return retval;
    }

    // Java doesn't yet support 128 bit integers,
    // needed to support primitives like these:

    // getU128
    // putU128
    // nextU128
    // nextU128Array

    // getS128
    // putS128
    // nextS128
    // nextS128Array


    /** Unmarshals a string (or null) from a fixed buffer offset. */
    protected final String getString (int index)
    {
	int	savedOffset = offset;
	String	retval;

	offset = index;
	retval = nextString ();
	offset = savedOffset;
	return retval;
    }

    /** Marshals a string, of length at most 254 characters, or null. */
    public void putString (String s)
    {
	if (s == null) {
	    put8 (0);
	    return;
	}

	int len = s.length ();

	if (len > 254)
	    throw new IllegalArgumentException ();
	put8 (len + 1);
	for (int i = 0; i < len; i++)
	    put16 ((int) s.charAt (i));
	put16 (0);
    }

    /** Unmarshals the next string (or null). */
    protected String nextString ()
    {
	int		len = nextU8 ();
	StringBuffer	str;

	if (len == 0)
	    return null;

	str = new StringBuffer (len);
	for (int i = 0; i < len; i++)
	    str.append ((char) nextU16 ());
	// drop terminal null
	str.setLength (len - 1);
	return str.toString ();
    }

    public void dump() {
        System.out.println(data);
        for (int i=0; i < data.length; ++i) {
            if ((i%8) == 0) {
                System.out.println();
            }
            System.out.print(String.format("%1$02X ", data[i]));
        }
    }
}
