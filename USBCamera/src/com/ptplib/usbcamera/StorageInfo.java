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
 * StorageInfo provides information such as the type and capacity of
 * storage media, whether it's removable, and more.
 *
 * @version $Id: StorageInfo.java,v 1.5 2001/04/12 23:13:00 dbrownell Exp $
 * @author David Brownell
 */
public class StorageInfo extends Data
{
    int		storageType;
    int		filesystemType;
    int		accessCapability;
    long	maxCapacity;

    long	freeSpaceInBytes;
    int		freeSpaceInImages;
    String	storageDescription;
    String	volumeLabel;

    StorageInfo (NameFactory f) { super (f); }

    void parse ()
    {
	super.parse ();

	storageType = nextU16 ();
	filesystemType = nextU16 ();
	accessCapability = nextU16 ();
	maxCapacity = /* unsigned */ nextS64 ();
	freeSpaceInBytes = /* unsigned */ nextS64 ();
	freeSpaceInImages = /* unsigned */ nextS32 ();
	storageDescription = nextString ();
	volumeLabel = nextString ();
    }

    void line (PrintStream out)
    {
	String	temp;

	switch (storageType) {
	    case 0: temp = "undefined"; break;
	    case 1: temp = "Fixed ROM"; break;
	    case 2: temp = "Removable ROM"; break;
	    case 3: temp = "Fixed RAM"; break;
	    case 4: temp = "Removable RAM"; break;
	    default:
		temp = "Reserved-0x" + Integer.toHexString (storageType);
	}
	out.println ("Storage Type: " + temp);
    }

    void line (TextView tv)
    {
	String	temp;

	switch (storageType) {
	    case 0: temp = "undefined"; break;
	    case 1: temp = "Fixed ROM"; break;
	    case 2: temp = "Removable ROM"; break;
	    case 3: temp = "Fixed RAM"; break;
	    case 4: temp = "Removable RAM"; break;
	    default:
		temp = "Reserved-0x" + Integer.toHexString (storageType);
	}
	tv.append  ("Storage Type: " + temp +" \n");
    }
    
    void dump (PrintStream out)
    {
	String	temp;

	super.dump (out);
	out.println ("StorageInfo:");
	line (out);

	switch (filesystemType) {
	    case 0: temp = "undefined"; break;
	    case 1: temp = "flat"; break;
	    case 2: temp = "hierarchical"; break;
	    case 3: temp = "dcf"; break;
	    default:
		if ((filesystemType & 0x8000) != 0)
		    temp = "Reserved-0x";
		else
		    temp = "Vendor-0x";
		temp += Integer.toHexString (filesystemType);
	}
	out.println ("Filesystem Type: " + temp);

	// access: rw, ro, or ro "with object deletion"

	// CF card sizes are "marketing megabytes", not real ones
	if (maxCapacity != ~0)
	    out.println ("Capacity: "
		+ maxCapacity + " bytes ("
		+ ((maxCapacity + 500000)/1000000) + " MB)"
		);
	if (freeSpaceInBytes != ~0)
	    out.println ("Free space: "
		+ freeSpaceInBytes + " bytes ("
		+ ((freeSpaceInBytes + 500000)/1000000) + " MB)"
		);
	if (freeSpaceInImages != ~0)
	    out.println ("Free space in Images: " + freeSpaceInImages);

	if (storageDescription != null)
	    out.println ("Description: " + storageDescription);
	if (volumeLabel != null)
	    out.println ("Volume Label: " + volumeLabel);
    }
    
    
    void showInTextView (TextView tv)
    {
	String	temp;


	tv.setText ("StorageInfo:");
	tv.append  ("\n");
	line (tv);

	switch (filesystemType) {
	    case 0: temp = "undefined"; break;
	    case 1: temp = "flat"; break;
	    case 2: temp = "hierarchical"; break;
	    case 3: temp = "dcf"; break;
	    default:
		if ((filesystemType & 0x8000) != 0)
		    temp = "Reserved-0x";
		else
		    temp = "Vendor-0x";
		temp += Integer.toHexString (filesystemType);
	}
	tv.append  ("\n");
	tv.append ("Filesystem Type: " + temp);

	// access: rw, ro, or ro "with object deletion"

	// CF card sizes are "marketing megabytes", not real ones
	if (maxCapacity != ~0)
	{
		tv.append  ("\n");
		tv.append ("Capacity: "
		+ maxCapacity + " bytes ("
		+ ((maxCapacity + 500000)/1000000) + " MB)"
		);
	}
	if (freeSpaceInBytes != ~0)
	{
		tv.append  ("\n");
		tv.append ("Free space: "
		+ freeSpaceInBytes + " bytes ("
		+ ((freeSpaceInBytes + 500000)/1000000) + " MB)"
		);
	}
	if (freeSpaceInImages != ~0)
	{
		tv.append  ("\n");
		tv.append ("Free space in Images: " + freeSpaceInImages);
	}
	if (storageDescription != null)
	{
		tv.append  ("\n");
		tv.append ("Description: " + storageDescription);
	}
	if (volumeLabel != null)
	{
		tv.append  ("\n");
		tv.append ("Volume Label: " + volumeLabel);
	}
    }
}
