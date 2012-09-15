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
 * The final phase of a PTP transaction sends a command's response from
 * the responder to the initiator.  These include response codes, and are
 * described in chapter 11 of the PTP specification.
 *
 * @version $Id: Response.java,v 1.2 2001/04/12 23:13:00 dbrownell Exp $
 * @author David Brownell
 */
public class Response extends ParamVector
{
    Response (byte buf [], NameFactory f)
	{ super (buf, f); }

    public Response (byte buf [], int len, NameFactory f)
	{ super (buf, len, f); }


    /** ResponseCode: */
    public static final int Undefined = 0x2000;
    /** ResponseCode: */
    public static final int OK = 0x2001;
    /** ResponseCode: */
    public static final int GeneralError = 0x2002;
    /** ResponseCode: */
    public static final int SessionNotOpen = 0x2003;

    /** ResponseCode: */
    public static final int InvalidTransactionID = 0x2004;
    /** ResponseCode: */
    public static final int OperationNotSupported = 0x2005;
    /** ResponseCode: */
    public static final int ParameterNotSupported = 0x2006;
    /** ResponseCode: */
    public static final int IncompleteTransfer = 0x2007;

    /** ResponseCode: */
    public static final int InvalidStorageID = 0x2008;
    /** ResponseCode: */
    public static final int InvalidObjectHandle = 0x2009;
    /** ResponseCode: */
    public static final int DevicePropNotSupported = 0x200a;
    /** ResponseCode: */
    public static final int InvalidObjectFormatCode = 0x200b;

    /** ResponseCode: */
    public static final int StoreFull = 0x200c;
    /** ResponseCode: */
    public static final int ObjectWriteProtected = 0x200d;
    /** ResponseCode: */
    public static final int StoreReadOnly = 0x200e;
    /** ResponseCode: */
    public static final int AccessDenied = 0x200f;


    /** ResponseCode: */
    public static final int NoThumbnailPresent = 0x2010;
    /** ResponseCode: */
    public static final int SelfTestFailed = 0x2011;
    /** ResponseCode: */
    public static final int PartialDeletion = 0x2012;
    /** ResponseCode: */
    public static final int StoreNotAvailable = 0x2013;

    /** ResponseCode: */
    public static final int SpecificationByFormatUnsupported = 0x2014;
    /** ResponseCode: */
    public static final int NoValidObjectInfo = 0x2015;
    /** ResponseCode: */
    public static final int InvalidCodeFormat = 0x2016;
    /** ResponseCode: */
    public static final int UnknownVendorCode = 0x2017;

    /** ResponseCode: */
    public static final int CaptureAlreadyTerminated = 0x2018;
    /** ResponseCode: */
    public static final int DeviceBusy = 0x2019;
    /** ResponseCode: */
    public static final int InvalidParentObject = 0x201a;
    /** ResponseCode: */
    public static final int InvalidDevicePropFormat = 0x201b;

    /** ResponseCode: */
    public static final int InvalidDevicePropValue = 0x201c;
    /** ResponseCode: */
    public static final int InvalidParameter = 0x201d;
    /** ResponseCode: */
    public static final int SessionAlreadyOpen = 0x201e;
    /** ResponseCode: */
    public static final int TransactionCanceled = 0x201f;

    /** ResponseCode: */
    public static final int SpecificationOfDestinationUnsupported = 0x2020;


    public String getCodeName (int code)
    {
	return factory.getResponseString (code);
    }

    public static String _getResponseString (int code)
    {
	switch (code) {
	    case Undefined:		return "Undefined";
	    case OK:			return "OK";
	    case GeneralError:		return "GeneralError";
	    case SessionNotOpen:	return "SessionNotOpen";

	    case InvalidTransactionID:	return "InvalidTransactionID";
	    case OperationNotSupported:	return "OperationNotSupported";
	    case ParameterNotSupported:	return "ParameterNotSupported";
	    case IncompleteTransfer:	return "IncompleteTransfer";

	    case InvalidStorageID:	return "InvalidStorageID";
	    case InvalidObjectHandle:	return "InvalidObjectHandle";
	    case DevicePropNotSupported: return "DevicePropNotSupported";
	    case InvalidObjectFormatCode: return "InvalidObjectFormatCode";

	    case StoreFull:		return "StoreFull";
	    case ObjectWriteProtected:	return "ObjectWriteProtected";
	    case StoreReadOnly:		return "StoreReadOnly";
	    case AccessDenied:		return "AccessDenied";

	    case NoThumbnailPresent:	return "NoThumbnailPresent";
	    case SelfTestFailed:	return "SelfTestFailed";
	    case PartialDeletion:	return "PartialDeletion";
	    case StoreNotAvailable:	return "StoreNotAvailable";

	    case SpecificationByFormatUnsupported:
				    return "SpecificationByFormatUnsupported";
	    case NoValidObjectInfo:	return "NoValidObjectInfo";
	    case InvalidCodeFormat:	return "InvalidCodeFormat";
	    case UnknownVendorCode:	return "UnknownVendorCode";

	    case CaptureAlreadyTerminated: return "CaptureAlreadyTerminated";
	    case DeviceBusy:		return "DeviceBusy";
	    case InvalidParentObject:	return "InvalidParentObject";
	    case InvalidDevicePropFormat: return "InvalidDevicePropFormat";

	    case InvalidDevicePropValue: return "InvalidDevicePropValue";
	    case InvalidParameter: 	return "InvalidParameter";
	    case SessionAlreadyOpen:	return "SessionAlreadyOpen";
	    case TransactionCanceled:	return "TransactionCanceled";

	    case SpecificationOfDestinationUnsupported:
			return "SpecificationOfDestinationUnsupported";
	}
	return ("0x" + Integer.toHexString (code)).intern ();
    }
}
