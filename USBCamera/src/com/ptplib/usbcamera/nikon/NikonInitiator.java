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
package com.ptplib.usbcamera.nikon;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

////import ch.ntb.usb.*;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbRequest;
import android.util.Log;
import android.widget.TextView;


import com.ptplib.usbcamera.BaselineInitiator;
import com.ptplib.usbcamera.Command;
import com.ptplib.usbcamera.Container;
import com.ptplib.usbcamera.Data;
import com.ptplib.usbcamera.DevicePropDesc;
import com.ptplib.usbcamera.NameFactory;
import com.ptplib.usbcamera.PTPException;
import com.ptplib.usbcamera.PTPUnsupportedException;
import com.ptplib.usbcamera.Response;

/**
 * This supports all standardized PTP-over-USB operations, including
 * operations (and modes) that are optional for all responders.
 * Filtering operations invoked on this class may be done on the device,
 * or may be emulated on the client side.
 * At this time, not all standardized operations are supported.
 *
 * @version $Id: EosInitiator.java,v 1.9 2001/04/12 23:13:00 dbrownell Exp $
 * @author David Brownell
 */
public class NikonInitiator extends BaselineInitiator {

	public static final int NIKON_VID = 1200;
	
	public static boolean eventListenerRunning = false;
    /**
     * This is essentially a class driver, following Annex D of
     * the PTP specification.
     */
    public NikonInitiator(UsbDevice dev, UsbDeviceConnection connection) throws PTPException {
        super(dev, connection);
        /*
        if (thread != null) eventListenerRunning = false;
        thread = new Thread (this);
        thread.start();
        */
    }

    /**
     * Fills out the provided device property description.
     *
     * @param propcode code identifying the property of interest
     * @param desc description to be filled; it may be a subtype
     *	associated with with domain-specific methods
     * @return response code
     */
    public int getDevicePropDesc(int propcode, DevicePropDesc desc)
            throws PTPException {
        return transact1(Command.GetDevicePropDesc, desc, propcode).getCode();
    }




    /**
     * Starts the capture of one (or more) new
     * data objects, according to current device properties.
     * The capture will complete without issuing further commands.
     *
     * @see #initiateOpenCapture
     *
     * @param storageId Where to store the object(s), or zero to
     *	let the device choose.
     * @param formatCode Type of object(s) to capture, or zero to
     *	use the device default.
     *
     * @return status code indicating whether capture started;
     *	CaptureComplete events provide capture status, and
     *	ObjectAdded events provide per-object status.
     */
    public Response initiateCapture(int storageId, int formatCode)
            throws PTPException {
    	Response resp = null;
        //
        // Special initialization for EOS cameras
        //
        if (!info.supportsOperation(Command.InitiateCapture)) {
        	Log.d(TAG, "The camera does not support Nikibon capture");
            throw new PTPException("The camera does not support Nikon capture");
        }

        int ret;


        try { Thread.sleep(100);  } catch (InterruptedException e) {e.printStackTrace();}
        try { Thread.sleep(100);  } catch (InterruptedException e) {e.printStackTrace();}

        resp = transact0(Command.InitiateCapture, null);
        ret = resp.getCode();
        Log.d(TAG, "  NK_OC_Capture Response code: 0x" +Integer.toHexString(ret) +"  OK: " +(ret == Response.OK));
        if (ret != Response.OK) {
            String msg = "NK_OC_Capture  Capture failed to release: Unknown error "
                    + ret
                    + " , please report.";
            Log.d(TAG, msg);
            throw new PTPException(msg, ret);
        }

      try { Thread.sleep(100);  } catch (InterruptedException e) {e.printStackTrace();}

      
      return resp;
    }

    /**
     * Retrieves a chunk of the object identified by the given object id.
     *
     * @param oid object id
     * @param offset the offset to start from
     * @param size the number of bytes to transfer
     * @param data the Data object receiving the object
     *
     * @throws PTPException in case of errors
     */
    public void getPartialObject(int oid, int offset, int size, Data data)
    throws PTPException {
        Response ret =
            transact3(Command.EosGetPartialObject, data, oid, offset, size);

        if (ret.getCode() != Response.OK) {
            throw new PTPException("Error reading new object", ret.getCode());
        }
    }

    public void transferComplete(int oid)
    throws PTPException {
        Response ret =
            transact1(Command.EosTransferComplete, null, oid);

        if (ret.getCode() != Response.OK) {
            throw new PTPException("Error reading new object", ret.getCode());
        }
    }
    //Methods below Added, getDevicePropDesc should work too
    public Response setDevicePropValueNikon (int property, int value) throws PTPException{
       
        byte [] buff = new byte [0x14];
        Data data = new Data (false, buff, buff.length, this);
        data.offset = 0;
        data.putHeader(buff.length, Container.BLOCK_TYPE_DATA, Command.SetDevicePropValue, 0 );
        data.put32 (value);	
        Command command = new Command(Command.SetDevicePropValue, session, property);
        writeExtraData(command, data, DEFAULT_TIMEOUT); 
        
        
        byte buf[] = new byte[inMaxPS];
        int len = mConnection.bulkTransfer(epIn, buf ,inMaxPS , DEFAULT_TIMEOUT);
        Response response = new Response(buf, len, this);
        return response;
    }
    
    public int getDevicePropValue(int propcode, DevicePropDesc desc)
    		throws PTPException {
    	return transact1(Command.GetDevicePropValue, desc, propcode).getCode();
	}
    
    
   
    
    
    //To do 22 Aug Ashraf
	public DevicePropDesc getPropValue (int value) throws PTPException
	{

		Command command = new Command(Command.GetDevicePropDesc, session, value);
		mConnection.bulkTransfer(epOut, command.data , command.length , DEFAULT_TIMEOUT);
		
		byte buf[] = new byte[inMaxPS];	
		int lengthOfBytes = mConnection.bulkTransfer(epIn, buf , inMaxPS , DEFAULT_TIMEOUT);	
	
		DevicePropDesc	info = new DevicePropDesc (this);
		info.data = buf;
		info.length = info.getLength();
		info.parse();

		Response response1 = new Response (buf, inMaxPS, this);

		buf = new byte[inMaxPS];	
		lengthOfBytes = mConnection.bulkTransfer(epIn, buf , inMaxPS , DEFAULT_TIMEOUT);
		
		Response response2 = new Response (buf, inMaxPS, this);

		
		return info;

	}
    
    
    public Response MoveFocus(int step) throws PTPException{

        return  null;//transact1(Command.EosDriveLens,null,step); 

    }
    
    public Response setExposure(int exposure) throws PTPException{
    	
    	return setDevicePropValueNikon(NikonEventConstants.PTP_DPC_ExposureBiasCompensation, exposure);
    	
    }
    
    public Response setISO(int value) throws PTPException{
    	//return null;
        return  setDevicePropValueNikon(NikonEventConstants.PTP_DPC_ExposureIndex ,value); 
    }
    
    public Response setAperture(int value) throws PTPException{

        return  setDevicePropValueNikon(NikonEventConstants.PTP_DPC_FNumber ,value); 
    }
    
    
    public Response setPictureStyle(int value) throws PTPException{

        return null; //return  setDevicePropValueNikon(Command.EOS_DPC_PictureStyle,value); 
    }
    
    public Response setWhiteBalance(int value) throws PTPException{

        return  setDevicePropValueNikon(NikonEventConstants.PTP_DPC_WhiteBalance,value); 
    }
    
    public Response setDriveMode(int value) throws PTPException{

        return null;//return  setDevicePropValueNikon(Command.EOS_DPC_DriveMode, value); 
    }
    
    public Response setMetering(int value) throws PTPException{

        return  null;//setDevicePropValueNikon(NikonEventConstants.PTP_DPC_FocusMeteringMode, value); 
    }
    
    public Response setImageQuality(int value) throws PTPException{

        return  null;//setDevicePropValueNikon(Command.EOS_DPC_ExpMeterringMode, value); 
    }
    
    public Response setShutterSpeed (int speed) throws PTPException{
        return setDevicePropValueNikon (NikonEventConstants.PTP_DPC_ExposureTime, speed);

    } 
    
	public Response setPropValue(int property,String value)
	{
		int lengthString = value.length()+1;
		byte [] buff = new byte [0x12 +lengthString+lengthString+1];
		 Data data = new Data (false, buff, buff.length, this);
		 data.offset = 0;
		 data.putHeader(buff.length, Container.BLOCK_TYPE_DATA, 
		 Command.SetDevicePropValue, 0 );

		 data.putString(value);
		Command command = new Command(Command.SetDevicePropValue, session, 
		property);
		 writeExtraData(command, data, DEFAULT_TIMEOUT);
		 
		// read response
		 byte buf[] = new byte[inMaxPS];
		 int len = mConnection.bulkTransfer(epIn, buf ,inMaxPS , 
				 DEFAULT_TIMEOUT);
		 Response response = new Response(buf, len, this);
		 return response;


	}
    
}
