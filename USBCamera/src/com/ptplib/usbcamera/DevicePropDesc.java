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

//Copyright 2000 by David Brownell <dbrownell@users.sourceforge.net>
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



import java.io.PrintStream;
import java.util.Vector;

import android.widget.TextView;


/**
 * DeviceProperty descriptions provide metadata (code, type, factory
 * defaults, is-it-writable, and perhaps value ranges or domains) and
 * current values of device properties.
 *
 * <p> The values exposed are typically <code>java.lang.Integer</code>,
 * <code>java.lang.Long</code>, "int" or "long" arrays,
 * or Strings, and must be cast to more specific types.
 * The 64 bit integral types are supported, but not the 128 bit ones;
 * unsigned 64 bit values may not print as intended.
 *
 * @version $Id: DevicePropDesc.java,v 1.7 2001/04/12 23:13:00 dbrownell Exp $
 * @author David Brownell
 */
public class DevicePropDesc extends Data
{
    int			propertyCode;
    int			dataType;
    boolean		writable;
    Object		factoryDefault;
    Object		currentValue;
    int			formType;
    Object		constraints;

    public DevicePropDesc (NameFactory f) { super (f); }

    public void parse ()
    {
	super.parse ();

	// per 13.3.3, tables 23, 24, 25
	propertyCode = nextU16 ();
	dataType = nextU16 ();
	writable = nextU8 () != 0;

	// FIXME use factories, as vendor hooks
	factoryDefault = DevicePropValue.get (dataType, this);
	currentValue = DevicePropValue.get (dataType, this);

	formType = nextU8 ();
	switch (formType) {
	    case 0:	// no more
		break;
	    case 1:	// range: min, max, step
		constraints = new Range (dataType, this);
		break;
	    case 2:	// enumeration: n, value1, ... valueN
		constraints = parseEnumeration ();
		break;
	    default:
		System.err.println ("ILLEGAL prop desc form, " + formType);
		formType = 0;
		break;
	}
    }
    public void showInTextView (TextView tv)
    {

	tv.setText(factory.getPropertyName (propertyCode));
	tv.append (" = ");
	tv.append (""+currentValue);
	if (!writable)
	    tv.append (", read-only");
	tv.append (", ");
	tv.append (DevicePropValue.getTypeName (dataType));
	switch (formType) {
	    case 0:
		break;
	    case 1: {
		Range	r = (Range) constraints;
		tv.append (" from ");
		tv.append (""+r.getMinimum ());
		tv.append (" to ");
		tv.append (""+r.getMaximum ());
		tv.append (" by ");
		tv.append (""+r.getIncrement ());
		};
		break;
	    case 2:  {
		Vector	v = (Vector) constraints;
		tv.append (" { ");
		for (int i = 0; i < v.size (); i++) {
		    if (i != 0)
			tv.append (", ");
		    tv.append (""+v.elementAt (i));
		}
		tv.append (" }");
		}
		break;
	    default:
		tv.append (" form ");
		tv.append (""+formType);
		tv.append (" (error)");
	}

	tv.append (", default ");
	tv.append  ("\n");
	tv.append ("Factory Default:"+factoryDefault);
    }
    
    
    void dump (PrintStream out)
    {
	super.dump (out);

	out.print (factory.getPropertyName (propertyCode));
	out.print (" = ");
	out.print (currentValue);
	if (!writable)
	    out.print (", read-only");
	out.print (", ");
	out.print (DevicePropValue.getTypeName (dataType));
	switch (formType) {
	    case 0:
		break;
	    case 1: {
		Range	r = (Range) constraints;
		out.print (" from ");
		out.print (r.getMinimum ());
		out.print (" to ");
		out.print (r.getMaximum ());
		out.print (" by ");
		out.print (r.getIncrement ());
		};
		break;
	    case 2:  {
		Vector	v = (Vector) constraints;
		out.print (" { ");
		for (int i = 0; i < v.size (); i++) {
		    if (i != 0)
			out.print (", ");
		    out.print (v.elementAt (i));
		}
		out.print (" }");
		}
		break;
	    default:
		out.print (" form ");
		out.print (formType);
		out.print (" (error)");
	}

	out.print (", default ");
	out.println (factoryDefault);
    }

    /** Returns true if the property is writable */
    public boolean isWritable ()
	{ return writable; }

    /** Returns the current value (treat as immutable!) */
    public Object getValue ()
	{ return currentValue; }

    /** Returns the factory default value (treat as immutable!) */
    public Object getDefault ()
	{ return factoryDefault; }


    // code values, per 13.3.5 table 26

    public static final int BatteryLevel = 0x5001;
    public static final int FunctionalMode = 0x5002;
    public static final int ImageSize = 0x5003;

    public static final int CompressionSetting = 0x5004;
    public static final int WhiteBalance = 0x5005;
    public static final int RGBGain = 0x5006;
    public static final int FStop = 0x5007;

    public static final int FocalLength = 0x5008;
    public static final int FocusDistance = 0x5009;
    public static final int FocusMode = 0x500a;
    public static final int ExposureMeteringMode = 0x500b;

    public static final int FlashMode = 0x500c;
    public static final int ExposureTime = 0x500d;
    public static final int ExposureProgramMode = 0x500e;
    public static final int ExposureIndex = 0x500f;

    public static final int ExposureBiasCompensation = 0x5010;
    public static final int DateTime = 0x5011;
    public static final int CaptureDelay = 0x5012;
    public static final int StillCaptureMode = 0x5013;

    public static final int Contrast = 0x5014;
    public static final int Sharpness = 0x5015;
    public static final int DigitalZoom = 0x5016;
    public static final int EffectMode = 0x5017;

    public static final int BurstNumber = 0x5018;
    public static final int BurstInterval = 0x5019;
    public static final int TimelapseNumber = 0x501a;
    public static final int TimelapseInterval = 0x501b;

    public static final int FocusMeteringMode = 0x501c;
    public static final int UploadURL = 0x501d;
    public static final int Artist = 0x501e;
    public static final int CopyrightInfo = 0x501f;


    public String getCodeName (int code)
    {
	return factory.getPropertyName (code);
    }

    static class NameMap {
	int	value;
	String	name;
	NameMap (int v, String n) { value = v; name = n; }
    }

    static NameMap	names [] = {
	new NameMap (BatteryLevel, "BatteryLevel"),
	new NameMap (FunctionalMode, "FunctionalMode"),
	new NameMap (ImageSize, "ImageSize"),
	new NameMap (CompressionSetting, "CompressionSetting"),
	new NameMap (WhiteBalance, "WhiteBalance"),
	new NameMap (RGBGain, "RGBGain"),
	new NameMap (FStop, "FStop"),
	new NameMap (FocalLength, "FocalLength"),
	new NameMap (FocusDistance, "FocusDistance"),
	new NameMap (FocusMode, "FocusMode"),
	new NameMap (ExposureMeteringMode, "ExposureMeteringMode"),
	new NameMap (FlashMode, "FlashMode"),
	new NameMap (ExposureTime, "ExposureTime"),
	new NameMap (ExposureProgramMode, "ExposureProgramMode"),
	new NameMap (ExposureIndex, "ExposureIndex"),
	new NameMap (ExposureBiasCompensation, "ExposureBiasCompensation"),
	new NameMap (DateTime, "DateTime"),
	new NameMap (CaptureDelay, "CaptureDelay"),
	new NameMap (StillCaptureMode, "StillCaptureMode"),
	new NameMap (Contrast, "Contrast"),
	new NameMap (Sharpness, "Sharpness"),
	new NameMap (DigitalZoom, "DigitalZoom"),
	new NameMap (EffectMode, "EffectMode"),
	new NameMap (BurstNumber, "BurstNumber"),
	new NameMap (BurstInterval, "BurstInterval"),
	new NameMap (TimelapseNumber, "TimelapseNumber"),
	new NameMap (TimelapseInterval, "TimelapseInterval"),
	new NameMap (FocusMeteringMode, "FocusMeteringMode"),
	new NameMap (UploadURL, "UploadURL"),
	new NameMap (Artist, "Artist"),
	new NameMap (CopyrightInfo, "CopyrightInfo"),
    };

    static String _getPropertyName (int code)
    {
	for (int i = 0; i < names.length; i++)
	    if (names [i].value == code)
		return names [i].name;
	return Container.getCodeString (code);
    }

    /**
     * Maps standard property names to property codes.
     * Case is ignored in these comparisons.
     * @param name string identifying that property.
     * @return device property code, or -1
     */
    public static int getPropertyCode (String name)
    {
	for (int i = 0; i < names.length; i++)
	    if (names [i].name.equalsIgnoreCase (name))
		return names [i].value;

	// FIXME: delegate to superclass
	return Integer.parseInt (name, 16);
    }


    /**
     * This class describes value ranges by minima, maxima,
     * and permissible increments.
     */
    public static final class Range
    {
	private Object	min, max, step;

	Range (int dataType, DevicePropDesc desc)
	{
	    min = DevicePropValue.get (dataType, desc);
	    max = DevicePropValue.get (dataType, desc);
	    step = DevicePropValue.get (dataType, desc);
	}

	/** Returns the maximum value of this range */
	public Object getMaximum () { return max; }

	/** Returns the minimum value of this range */
	public Object getMinimum () { return min; }

	/** Returns the increment of values in this range */
	public Object getIncrement () { return step; }
    }

    /** Returns any range constraints for this property's value, or null */
    public Range getRange ()
    {
	if (formType == 1)
	    return (Range) constraints;
	return null;
    }


    private Vector parseEnumeration ()
    {
	int	len = nextU16 ();
	Vector	retval = new Vector (len);

	while (len-- > 0)
	    retval.addElement (DevicePropValue.get (dataType, this));
	return retval;
    }


    /** Returns any enumerated options for this property's value, or null */
    public Vector getEnumeration ()
    {
	if (formType == 2)
	    return (Vector) constraints;
	return null;
    }
}
