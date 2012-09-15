/* Copyright 2010 by Stefano Fornari
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
*/

package com.ptplib.usbcamera.eos;

import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author ste
 */
public class EosEvent implements EosEventConstants {

    /**
     * Event code
     */
    private int code;

    /**
     * Params
     */
    private List params;

    /**
     * Creates a new parser with the given data
     *
     * @param data the events data - NOT NULL
     */
    public EosEvent() {
        params = new ArrayList();
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    /**
     * @param i the parameter index
     * @param param the param to set
     */
    public void setParam(int i, Object value) {
        if (i<0) {
            throw new IllegalArgumentException("param index cannot be < 0");
        }
        if (params.size() <= i) {
            ArrayList newParams = new ArrayList(i);
            newParams.addAll(params);
            params = newParams;
            for (int j=params.size(); j<i; ++j) {
                params.add(null);
            }
        }
        params.set(i-1, value);
    }

    public void setParam(int i, int value) {
        setParam(i, new Integer(value));
    }

    public Object getParam(int i) {
        if ((i < 1) || (i > getParamCount())) {
            throw new IllegalArgumentException(
                "index " + i + " out of range (0-" + getParamCount() + ")"
            );
        }
        return params.get(i-1);
    }

    public int getIntParam(int i) {
        return ((Integer)getParam(i)).intValue();
    }

    public String getStringParam(int i) {
        return (String)getParam(i);
    }

    /**
     *
     * @return the number of parameters in this event
     */
    public int getParamCount() {
        return params.size();
    }

    public static String getEventName (int code){
    	switch (code) {
    	case  EosEventRequestGetEvent         : return "EosEventRequestGetEvent";
    	case  EosEventObjectAddedEx           : return "EosEventObjectAddedEx";
    	case  EosEventObjectRemoved           : return "EosEventObjectRemoved";
    	case  EosEventRequestGetObjectInfoEx  : return "EosEventRequestGetObjectInfoEx";
    	case  EosEventStorageStatusChanged    : return "EosEventStorageStatusChanged";
    	case  EosEventStorageInfoChanged      : return "EosEventStorageInfoChanged";
    	case  EosEventRequestObjectTransfer   : return "EosEventRequestObjectTransfer";
    	case  EosEventObjectInfoChangedEx     : return "EosEventObjectInfoChangedEx";
    	case  EosEventObjectContentChanged    : return "EosEventObjectContentChanged";
    	case  EosEventPropValueChanged        : return "EosEventPropValueChanged";
    	case  EosEventAvailListChanged        : return "EosEventAvailListChanged";
    	case  EosEventCameraStatusChanged     : return "EosEventCameraStatusChanged";
    	case  EosEventWillSoonShutdown        : return "EosEventWillSoonShutdown";
    	case  EosEventShutdownTimerUpdated    : return "EosEventShutdownTimerUpdated";
    	case  EosEventRequestCancelTransfer   : return "EosEventRequestCancelTransfer";
    	case  EosEventRequestObjectTransferDT : return "EosEventRequestObjectTransferDT";
    	case  EosEventRequestCancelTransferDT : return "EosEventRequestCancelTransferDT";
    	case  EosEventStoreAdded              : return "EosEventStoreAdded";
    	case  EosEventStoreRemoved            : return "EosEventStoreRemoved";
    	case  EosEventBulbExposureTime        : return "EosEventBulbExposureTime";
    	case  EosEventRecordingTime           : return "EosEventRecordingTime";
    	case  EosEventAfResult                : return "EosEventRequestObjectTransferTS";
    	case  EosEventRequestObjectTransferTS : return "EosEventAfResult";
    	}
	return "0x" +Integer.toHexString(code);
    }

}
