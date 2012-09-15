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
package com.ptplib.usbcamera.eos;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

////import ch.ntb.usb.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbRequest;
import android.util.Log;
import android.widget.ImageView;


import com.ptplib.usbcamera.BaselineInitiator;
import com.ptplib.usbcamera.Command;
import com.ptplib.usbcamera.Container;
import com.ptplib.usbcamera.Data;
import com.ptplib.usbcamera.DevicePropDesc;
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
public class EosInitiator extends BaselineInitiator {

	public static final int CANON_VID = 1193;
	
	public static boolean eventListenerRunning = false;
    /**
     * This is essentially a class driver, following Annex D of
     * the PTP specification.
     */
    public EosInitiator(UsbDevice dev, UsbDeviceConnection connection) throws PTPException {
        super(dev, connection);
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
     * Checks if there is any event available. If there is any, an ArrayList
     * of Event object is returned.
     *
     * @return the list of available events
     *
     * @throws PTPException in case of errors
     */
    public List<EosEvent> checkEvents()
            throws PTPException {
    	int ret;
//        ret = transact1(Command.EosSetEventMode, null, 1).getCode(); // Prevents Releasing shutter
//        showResponseCode ("  EosSetEventMode 1 ", ret);
//        if (ret != Response.OK) {     	
//            throw new PTPException("Error reading events", ret);
//        }

        Data data = new Data(this);
        Response res = transact0(Command.EosGetEvent, data);
        showResponseCode ("  GetEvent: data length: " +data.getLength() +" resLength: " +res.getLength() +" EosGetEvent ", res.getCode());
        
        if (res.getCode() != Response.OK) {
//            throw new PTPException(String.format("Failed getting events from the camera (%1$04X)", res.getCode()));
        }

//        System.out.println("Event data:");
//        data.dump();

        //
        // We need to discard the initial 12 USB header bytes
        //
        byte[] buf = new byte[data.getLength() - 12];
        System.arraycopy(data.getData(), 12, buf, 0, buf.length);

        EosEventParser parser = new EosEventParser(new ByteArrayInputStream(buf));

        ArrayList<EosEvent> events = new ArrayList<EosEvent>();
        while (parser.hasEvents()) {
            try {
            	EosEvent event = parser.getNextEvent();
            	events.add(event);
 //                events.add(parser.getNextEvent());
            } catch (PTPUnsupportedException e) {
                //
                // TODO: log this information?
                //
                //System.err.println("Skipping unsupported event");
            }
        }

        return events;
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
        if (!info.supportsOperation(Command.EosRemoteRelease)) {
        	Log.d(TAG, "The camera does not support EOS capture");
            throw new PTPException("The camera does not support EOS capture");
        }

        int ret;
        ret = transact1(Command.EosSetRemoteMode, null, 1).getCode();
        showResponseCode ("  EosSetRemoteMode 1: ", ret);
        if (ret != Response.OK) {
            throw new PTPException("Unable to set remote mode", ret);
        }

        //
        // TODO: cover the case where initialization has already been done
        //
//        ret = transact1(Command.EosSetRemoteMode, null, 0).getCode();
//        showResponseCode ("  EosSetRemoteMode 0 :", ret);
//        if (ret != Response.OK) {
//            throw new PTPException("Unable to set remote mode", ret);
//        }

        try { Thread.sleep(100);  } catch (InterruptedException e) {e.printStackTrace();}
        checkEvents(); // Prevents  EosRemoteRelease!
        try { Thread.sleep(100);  } catch (InterruptedException e) {e.printStackTrace();}
        checkEvents(); // Prevents  EosRemoteRelease!
        try { Thread.sleep(100);  } catch (InterruptedException e) {e.printStackTrace();}
        checkEvents(); // Prevents  EosRemoteRelease!

        resp = transact0(Command.EosRemoteRelease, null);
        ret = resp.getCode();
        Log.d(TAG, "  EosRemoteRelease Response code: 0x" +Integer.toHexString(ret) +"  OK: " +(ret == Response.OK));
        if (ret != Response.OK) {
            String msg = "Canon EOS Capture failed to release: Unknown error "
                    + ret
                    + " , please report.";
            if (ret == 1) {
                msg = "Canon EOS Capture failed to release: Perhaps no focus?";
            } else if (ret == 7) {
                msg = "Canon EOS Capture failed to release: Perhaps no more memory on card?";
            }
            Log.d(TAG, msg);
            throw new PTPException(msg, ret);
        }
      ret = transact1(Command.EosSetRemoteMode, null, 0).getCode();
      showResponseCode ("  EosSetRemoteMode 0: ", ret);
      if (ret != Response.OK) {
          throw new PTPException("Unable to set remote mode", ret);
      }
      try { Thread.sleep(100);  } catch (InterruptedException e) {e.printStackTrace();}
      checkEvents(); // Prevents  EosRemoteRelease!
      try { Thread.sleep(100);  } catch (InterruptedException e) {e.printStackTrace();}
      checkEvents(); // Prevents  EosRemoteRelease!
      try { Thread.sleep(100);  } catch (InterruptedException e) {e.printStackTrace();}
      checkEvents(); // Prevents  EosRemoteRelease!

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
    
    public Response setDevicePropValueEx (int property, int value) throws PTPException{
        byte [] buff = new byte [0x18];
        Data data = new Data (false, buff, buff.length, this);
        data.offset = 0;
        data.putHeader(buff.length, Container.BLOCK_TYPE_DATA, Command.EosSetDevicePropValueEx, 0 /*XID, dummy, will be overwritten*/);
        data.put32 (0x0c); // Length: 12 bytes
        data.put32 (property);
        data.put32 (value);	
        return transact0(Command.EosSetDevicePropValueEx, data); 
    }
    
    public Response getDevicePropValueEx (int property) throws PTPException{
        byte [] buff = new byte [0x10];
        Data data = new Data (false, buff, buff.length, this);
        data.offset = 0;
        data.putHeader(buff.length, Container.BLOCK_TYPE_DATA, Command.EosRequestDevicePropValue, 0 /*XID, dummy, will be overwritten*/);
        data.put32 (property);
        return transact0(Command.EosRequestDevicePropValue, data); 
    }
    
    /* 
     *
     //Doesn't work
    public Response setDeviceProp (int property, int value) throws PTPException{
    	return transact3(Command.EosSetDevicePropValueEx,null,0x0c,property,value);

    }  
    */
    
    public Response getShutterSpeed () throws PTPException{
        return getDevicePropValueEx (Command.EOS_DPC_ShutterSpeed);

    }
    public Response setShutterSpeed (int speed) throws PTPException{
        return setDevicePropValueEx (Command.EOS_DPC_ShutterSpeed, speed);

    }  
    
    public Response setLiveView (boolean on) throws PTPException{
        if(on)
        {
        	//turn on
        	setDevicePropValueEx (Command.EOS_DPC_LiveView, 2);
        	return setDevicePropValueEx(0xD1B3, 2);
        }
        else
        {
        	//turn off
        	return setDevicePropValueEx (Command.EOS_DPC_LiveView, 0);
        }
    	

    } 
    
    public Response startBulbs () throws PTPException{
    
       setDevicePropValueEx (Command.EOS_DPC_ShutterSpeed, EosEventConstants.SHUTTER_SPEED_BULB);

      // transact3(Command.EosPCHDDCapacity, null,0xfffffff8,0x00001000,0x00000000); 
      // transact0(Command.EosSetUILock,null);  
       
       return transact0(Command.EosBulbStart,null);
    }

    public Response stopBulbs () throws PTPException{

    	
      //  transact3(Command.EosPCHDDCapacity, null,0xffffffff,0x00001000,0x00000000); 
      //  transact3(Command.EosPCHDDCapacity, null,0xfffffffc,0x00001000,0x00000000); 
      return  transact0(Command.EosBulbEnd,null); 
      //  return transact0(Command.EosResetUILock,null); 
    }
    
    public void GetDevicePropInfo()
    {
    	int command = 0x1014;
    	//GetDevicePropInfo PtpValues.StandardOperations.GET_DEVICE_PROP_DESC, self.sessionid, self.NewTransaction(), (propertyId,))
    }
    
    public Response MoveFocus(int step) throws PTPException{

        return  transact1(Command.EosDriveLens,null,step); 

    }
    
    public Response setExposure(int exposure) throws PTPException{
    	
    	return setDevicePropValueEx (Command.EOS_DPC_ExposureCompensation, exposure);
    	
    }
    
    public Response setISO(int value) throws PTPException{

        return  setDevicePropValueEx(Command.EOS_DPC_Iso ,value); 
    }
    
    public Response setAperture(int value) throws PTPException{

        return  setDevicePropValueEx(Command.EOS_DPC_Aperture ,value); 
    }
    
    
    public Response setPictureStyle(int value) throws PTPException{

        return  setDevicePropValueEx(Command.EOS_DPC_PictureStyle,value); 
    }
    
    public Response setWhiteBalance(int value) throws PTPException{

        return  setDevicePropValueEx(Command.EOS_DPC_WhiteBalance,value); 
    }
    
    public Response setDriveMode(int value) throws PTPException{

        return  setDevicePropValueEx(Command.EOS_DPC_DriveMode, value); 
    }
    
    public Response setMetering(int value) throws PTPException{

        return  setDevicePropValueEx(Command.EOS_DPC_ExpMeterringMode, value); 
    }
    
    public Response setImageQuality(int value) throws PTPException{

        return  setDevicePropValueEx(Command.EOS_DPC_ExpMeterringMode, value); 
    }
    
	public void setupLiveview() throws PTPException
	{
		
		Command command = new Command(Command.EOS_OC_SetPCConnectMode, session, 1);
		write(command.data, command.length, DEFAULT_TIMEOUT);				
		byte buf[] = read(DEFAULT_TIMEOUT);
		
		Response response = new Response (buf, inMaxPS, this);

		setDevicePropValueEx(Command.EOS_DPC_LiveView,2);
		setDevicePropValueEx(Command.EOS_DPC_LiveView,1);
		
		
	}
    
	public void getLiveView(final ImageView imageView)
	{
		Command command = new Command(Command.EOS_OC_GetLiveViewPicture, session,0x00100000);
		write(command.data, command.length, DEFAULT_TIMEOUT);				
		byte buf[] = read(DEFAULT_TIMEOUT);
		Data item = new Data(true, buf, this); 

		int totalLength = item.getLength();
		int left = totalLength - buf.length;

		int needToRead = (left/inMaxPS);
		
		if((left%inMaxPS) != 0)
			needToRead++;
		
		byte imageBuf[] = new byte[inMaxPS*(needToRead+1)];
		
		System.arraycopy(buf,0,imageBuf,0,512);
		
		for (int i=0; i<(needToRead); i++)
		{

			buf = read(DEFAULT_TIMEOUT);
			System.arraycopy(buf,0,imageBuf,512*(i+1),512);


		}
		Data completedData = new Data(true, imageBuf, this); 	
		
		final Bitmap bMap = BitmapFactory.decodeByteArray(completedData.data, 20, completedData.getLength()-20); 
		Bitmap scaled = Bitmap.createScaledBitmap(bMap, bMap.getWidth()/10, bMap.getHeight()/10, false);
		
		imageView.post(new Runnable(){

			@Override
			public void run() {
				imageView.setImageBitmap(bMap); 
				imageView.invalidate(); 
				
			}
			
		});
		/*
		tv6.setText(" Height:"+bMap.getHeight());
		tv6.append(" Width:"+bMap.getWidth());
		*/
		byte buf1[] = read(DEFAULT_TIMEOUT);

		Response response = new Response (buf1, inMaxPS, this);
		//tv6.append(response.toString());

		
	}
	/////////////////////////
	public void setFocusPos(int x, int y)
	{

		Command command = new Command(EosEventConstants.PTP_OC_CANON_EOS_ZoomPosition,session,x,y);
		
		write(command.data, command.length, DEFAULT_TIMEOUT);		
		
		byte buf[] = read(DEFAULT_TIMEOUT);

		//tv3.setText("Received:");
		Response response = new Response (buf, inMaxPS, this);
		//tv3.append(response.toString());

	}
	
	public void setZoom(int zoomLevel)
	{
		//zoomLevel = 5 or 10 or 1
		

		Command command = new Command(EosEventConstants.PTP_OC_CANON_EOS_Zoom,session,zoomLevel);
		
		write(command.data, command.length, DEFAULT_TIMEOUT);

		byte buf[] = read(DEFAULT_TIMEOUT);

		//tv3.setText("Received:");
		Response response = new Response (buf, inMaxPS, this);
		//tv3.append(response.toString());

	}
	
	public void doAutoFocus()
	{

		Command command = new Command(EosEventConstants.PTP_OC_CANON_EOS_DoAf, session);		
		write(command.data, command.length, DEFAULT_TIMEOUT);
		byte buf[] = read(DEFAULT_TIMEOUT);
		//tv3.setText("Received:");
		Response response = new Response (buf, inMaxPS, this);
		//tv3.append(response.toString());

	}
    
}
