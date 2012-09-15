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
 * DeviceInfo describes device functionality such supported image formats,
 * operations, events, and device properties.
 *
 * @version $Id: DeviceInfo.java,v 1.8 2001/04/12 23:13:00 dbrownell Exp $
 * @author David Brownell
 */
public class DeviceInfo extends Data
{
    // need some transport-neutral interface; this is USB-specific 

    int		standardVersion;
    int		vendorExtensionId;
    int		vendorExtensionVersion;
    String	vendorExtensionDesc;

    int		functionalMode; 		// may change; 
    int		operationsSupported [];		// 10.2
    int		eventsSupported [];		// 12.5
    int		propertiesSupported [];		// 13.3.5

    int		captureFormats [];		// 6
    int		imageFormats [];		// 6
    String	manufacturer;
    String	model;

    String	deviceVersion;
    String	serialNumber;

    // FIXME add formal vendor hooks, which we'd consult for string
    // mappings ... we don't have any here.

    // Command, Response, ObjectInfo, Event, and DevicePropDesc can
    // all be subclassed; but we won't have instances here.  And
    // there's also the vendor extension stuff here.


    // input -- we can't know buffer size yet
    DeviceInfo (NameFactory f)
	{ super (true, null, 0, f); }

    
    private boolean supports (int supported [], int code)
    {
	for (int i = 0; i < supported.length; i++) {
	    if (code == supported [i])
		return true;
	}
	return false;
    }


    /** Returns true iff the device supports this operation */
    public boolean supportsOperation (int opCode)
    {
	return supports (operationsSupported, opCode);
    }

    /** Returns true iff the device supports this event */
    public boolean supportsEvent (int eventCode)
    {
	return supports (eventsSupported, eventCode);
    }

    /** Returns true iff the device supports this property */
    public boolean supportsProperty (int propCode)
    {
	return supports (propertiesSupported, propCode);
    }

    /** Returns true iff the device supports this capture format */
    public boolean supportsCaptureFormat (int formatCode)
    {
	return supports (captureFormats, formatCode);
    }

    /** Returns true iff the device supports this image format */
    public boolean supportsImageFormat (int formatCode)
    {
	return supports (imageFormats, formatCode);
    }


    // fit names to standard length lines
    private int addString (PrintStream out, int last, String s)
    {
	last += s.length ();
	last++;
	if (last < 80) {
	    out.print (s);
	    out.print (" ");
	} else {
	    out.println ();
	    out.print ("\t");
	    out.print (s);
	    out.print (" ");
	    last = 8 + s.length () + 1;
	}
	return last;
    }

    void parse ()
    {
	 super.parse ();

	 standardVersion = nextU16 ();
	 vendorExtensionId = /* unsigned */ nextS32 ();
	 vendorExtensionVersion = nextU16 ();
	 vendorExtensionDesc = nextString ();

	 functionalMode = nextU16 ();
	 operationsSupported = nextU16Array ();
	 eventsSupported = nextU16Array ();
	 propertiesSupported = nextU16Array ();

	 captureFormats = nextU16Array ();
	 imageFormats = nextU16Array ();
	 manufacturer = nextString ();
	 model = nextString ();

	 deviceVersion = nextString ();
	 serialNumber = nextString ();
    }

    void lines (PrintStream out)
    {
	if (manufacturer != null)
	    out.println ("Manufacturer: " + manufacturer);
	if (model != null)
	    out.println ("Model: " + model);
	if (deviceVersion != null)
	    out.println ("Device Version: " + deviceVersion);
	if (serialNumber != null)
	    out.println ("Serial Number: " + serialNumber);

	if (functionalMode != 0) {
	    out.print ("Functional Mode: ");
	    out.println (funcMode (functionalMode));
	}

	if (vendorExtensionId != 0) {
	    out.print ("Extensions (");
	    out.print (Integer.toString (vendorExtensionId));
	    out.print (")");
	    if (vendorExtensionDesc != null) {
		out.print (": ");
		out.print (vendorExtensionDesc);
	    }
	    out.println ();

	    // summarize extension: ops, props, events
	}
    }

    public String toString ()
    {

    	if (operationsSupported == null) {
    		// System.err.println ("... device info uninitted");
    		return "... device info uninitted";
    	}
    	String result = "DeviceInfo:\n";
    	result += ("PTP Version: "
    			+ (standardVersion / 100)
    			+ "."
    			+ (standardVersion % 100));

    	// per chapter 10
    	result += ("\n\nOperations Supported:");
    	for (int i = 0; i < operationsSupported.length; i++) {
    		result += "\n\t" +factory.getOpcodeString (operationsSupported [i]);
    	}

    	// per chapter 11
    	result += ("\n\nEvents Supported:");
    	for (int i = 0; i < eventsSupported.length; i++) {
    		result += "\n\t" +factory.getEventString (eventsSupported [i]);
    	}

    	// per chapter 13
    	result += ("\n\nDevice Properties Supported:\n");
    	for (int i = 0; i < propertiesSupported.length; i++) {
    		result += "\t" +factory.getPropertyName (propertiesSupported [i]);
    	}

    	// per 6.2
    	result += ("\n\nCapture Formats Supported:\n");
    	for (int i = 0; i < captureFormats.length; i++) {
    		result += "\t" +factory.getFormatString (captureFormats [i]);
    	}

    	// per 6.2
    	result += ("\n\nImage Formats Supported:\n");
    	for (int i = 0; i < imageFormats.length; i++) {
    		result += "\t" + factory.getFormatString (imageFormats [i]);
    	}

    	if (vendorExtensionId != 0) {
    		result += ("\n\nVendor Extension, id ");
    		result += (Integer.toString (vendorExtensionId));
    		result += (", version ");
    		result += (standardVersion / 100);
    		result += (".");
    		result += (standardVersion % 100);

    		if (vendorExtensionDesc != null) {
    			result += "\nDescription: " +vendorExtensionDesc;
    		}
    	}
    	return result;
    }

    static String funcMode (int functionalMode)
    {
	switch (functionalMode) {
	    case 0:
		return "standard";
	    case 1:
		return "sleeping";
	    default:
		// FIXME add vendor hook
		StringBuffer	buf = new StringBuffer ();

		if ((functionalMode & 0x8000) == 0)
		    buf.append ("reserved 0x");
		else
		    buf.append ("vendor 0x");
		buf.append (Integer.toHexString (functionalMode & ~0x8000));
		return buf.toString ();
	}
    }
}
