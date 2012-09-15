// Copyright 2000 by David Brownell <dbrownell@users.sourceforge.net>
// Copyright 2010 by Stefano Fornari
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
 * Events are sent spontaneously from responders to initiators.
 * Event codes, described in chapter 12 of the PTP specification,
 * identify what happened.  Some events have a parameter, and
 * additional data (for vendor extensions) may be available
 * using the class control request "Get Extended Event Data".
 *
 * @author David Brownell
 * @author Stefano Fonari
 */
public class Event extends ParamVector {

    public Event(byte buf[], NameFactory f) {
        super(buf, buf.length, f);
    }
    /** EventCode: */
    public static final int Undefined = 0x4000;
    /** EventCode: */
    public static final int CancelTransaction = 0x4001;
    /** EventCode: */
    public static final int ObjectAdded = 0x4002;
    /** EventCode: */
    public static final int ObjectRemoved = 0x4003;
    /** EventCode: */
    public static final int StoreAdded = 0x4004;
    /** EventCode: */
    public static final int StoreRemoved = 0x4005;
    /** EventCode: */
    public static final int DevicePropChanged = 0x4006;
    /** EventCode: */
    public static final int ObjectInfoChanged = 0x4007;
    /** EventCode: */
    public static final int DeviceInfoChanged = 0x4008;
    /** EventCode: */
    public static final int RequestObjectTransfer = 0x4009;
    /** EventCode: */
    public static final int StoreFull = 0x400a;
    /** EventCode: */
    public static final int DeviceReset = 0x400b;
    /** EventCode: */
    public static final int StorageInfoChanged = 0x400c;
    /** EventCode: */
    public static final int CaptureComplete = 0x400d;
    /** EventCode: a status event was dropped (missed an interrupt) */
    public static final int UnreportedStatus = 0x400e;

    public String getCodeName(int code) {
        return factory.getEventString(code);
    }

    static public String _getEventString(int code) {
        switch (code) {
            case Undefined:
                return "Undefined";
            case CancelTransaction:
                return "CancelTransaction";
            case ObjectAdded:
                return "ObjectAdded";
            case ObjectRemoved:
                return "ObjectRemoved";

            case StoreAdded:
                return "StoreAdded";
            case StoreRemoved:
                return "StoreRemoved";
            case DevicePropChanged:
                return "DevicePropChanged";
            case ObjectInfoChanged:
                return "ObjectInfoChanged";

            case DeviceInfoChanged:
                return "DeviceInfoChanged";
            case RequestObjectTransfer:
                return "RequestObjectTransfer";
            case StoreFull:
                return "StoreFull";
            case DeviceReset:
                return "DeviceReset";

            case StorageInfoChanged:
                return "StorageInfoChanged";
            case CaptureComplete:
                return "CaptureComplete";
            case UnreportedStatus:
                return "UnreportedStatus";


        }
        return getCodeString(code);
    }
}
