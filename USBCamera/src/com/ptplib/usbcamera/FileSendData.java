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

import java.io.InputStream;
import java.io.IOException;
import java.net.URLConnection;


/**
 * Used with {@link BaselineInitiator#sendObject sendObject}, this can read
 * objects from files using a relatively small amount of in-memory buffering.
 * That reduces system resource requirements for working with large files
 * such as uncompressed TIF images supported by higher end imaging devices.
 *
 * @see BaselineInitiator#sendObject
 *
 * @version $Id: FileSendData.java,v 1.3 2001/04/12 23:13:00 dbrownell Exp $
 * @author David Brownell
 */
public class FileSendData extends Data
{
    private InputStream		in;
    private int			filesize;

    // FIXME:  shouldn't be "File" streams

    /**
     * Constructs a data object which fills the given underlying file.
     */
    public FileSendData (URLConnection data, NameFactory f)
    throws IOException
    {
	super (false, new byte [128 * 1024], f);
	in = data.getInputStream ();
	filesize = data.getContentLength ();
    }

    public int getLength ()
    {
	return HDR_LEN + filesize;
    }

    /**
     * Reads object data from the underlying file.
     */
    public int read (byte buf [], int off, int len)
    throws IOException
    {
	return in.read (buf, off, len);
    }

    /**
     * Closes the underlying file.
     */
    public void close ()
    throws IOException
    {
	in.close ();
    }
}

