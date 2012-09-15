// Copyright 2001 by David Brownell <dbrownell@users.sourceforge.net>
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
 * Supports use of objects using vendor extension codes.
 * The base class produces names only for standard PTP 
 * operations, responses, properties, events, and formats.
 *
 * @version $Id: NameFactory.java,v 1.1 2001/04/12 23:13:00 dbrownell Exp $
 */
public class NameFactory
{
    // package private
    protected NameFactory () { }

    // package private
    protected NameFactory updateFactory (int vendorExtensionId)
    {
	switch (vendorExtensionId) {
	    case 0:	return this;
	    case 1: 	return new KodakExtension ();
	}
	if (BaselineInitiator.DEBUG)
	    System.err.println ("Don't know extension " + vendorExtensionId);
	return this;
    }

/*
    static String vendorToString (int vendorExtensionId)
    {
	switch (vendorExtensionId) {
	    // from PIMA website
	    case 1: return "Eastman Kodak Company";
	    case 2: return "Seiko Epson";
	    case 3: return "Agilent Technologies, Inc.";
	    case 4: return "Polaroid Corporation";
	    case 5: return "Agfa-Gevaert";
	    case 6: return "Microsoft Corporation";
	    default:
		return "0x" + Integer.toHexString (vendorExtensionId);
	}
    }
*/

    /**
     * Maps command codes to string names.
     * @param code device command code
     * @return interned string identifying that command.
     */
    // bits 14:12 = 001
    public String getOpcodeString (int code)
	{ return Command._getOpcodeString (code); }

    /**
     * Maps response codes to string names.
     * @param code response code
     * @return interned string identifying that response.
     */
    // bits 14:12 = 010
    public String getResponseString (int code)
	{ return Response._getResponseString (code); }

    /**
     * Maps object format codes to string names.
     * @param code device format code
     * @return interned string identifying that format.
     */
    // bits 14:12 = 011
    public String getFormatString (int code)
	{ return ObjectInfo._getFormatString (code); }

    /**
     * Maps event codes to string names.
     * @param code device event code
     * @return interned string identifying that event.
     */
    // bits 14:12 = 100
    public String getEventString (int code)
	{ return Event._getEventString (code); }
    
    /**
     * Maps property codes to string names.
     * @param code device property code
     * @return interned string identifying that property.
     */
    // bits 14:12 = 101
    public String getPropertyName (int code)
	{ return DevicePropDesc._getPropertyName (code); }

    
    // FIXME:  hooks for vendor-specific filesystem types.
}
