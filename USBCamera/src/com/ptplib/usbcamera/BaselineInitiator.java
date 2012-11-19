// Copyright 2000 by David Brownell <dbrownell@users.sourceforge.net>
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed epIn the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
//
package com.ptplib.usbcamera;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import com.ptplib.usbcamera.eos.EosEventConstants;


import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbRequest;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This initiates interactions with USB devices, supporting only
 * mandatory PTP-over-USB operations; both
 * "push" and "pull" modes are supported.  Note that there are some
 * operations that are mandatory for "push" responders and not "pull"
 * ones, and vice versa.  A subclass adds additional standardized
 * operations, which some PTP devices won't support.  All low
 * level interactions with the device are done by this class,
 * including especially error recovery.
 *
 * <p> The basic sequence of operations for any PTP or ISO 15470
 * initiator (client) is:  acquire the device; wrap it with this
 * driver class (or a subclass); issue operations;
 * close device.  PTP has the notion
 * of a (single) session with the device, and until you have an open
 * session you may only invoke {@link #getDeviceInfo} and
 * {@link #openSession} operations.  Moreover, devices may be used
 * both for reading images (as from a camera) and writing them
 * (as to a digital picture frame), depending on mode support.
 *
 * <p> Note that many of the IOExceptions thrown here are actually
 * going to be <code>usb.core.PTPException</code> values.  That may
 * help your application level recovery processing.  You should
 * assume that when any IOException is thrown, your current session
 * has been terminated.
 *
 * @see Initiator
 *
 * @version $Id: BaselineInitiator.java,v 1.17 2001/05/30 19:33:43 dbrownell Exp $
 * @author David Brownell
 *
 * This class has been reworked by ste epIn order to make it compatible with
 * usbjava2. Also, this is more a derivative work than just an adaptation of the
 * original version. It has to serve the purposes of usbjava2 and cameracontrol.
 */
public class BaselineInitiator extends NameFactory implements Runnable {

    ///////////////////////////////////////////////////////////////////
    // USB Class-specific control requests; from Annex D.5.2
    private static final byte CLASS_CANCEL_REQ        = (byte) 0x64;
    private static final byte CLASS_GET_EVENT_DATA    = (byte) 0x65;
    private static final byte CLASS_DEVICE_RESET      = (byte) 0x66;
    private static final byte CLASS_GET_DEVICE_STATUS = (byte) 0x67;
    protected static final int  DEFAULT_TIMEOUT 		  = 1000; // ms

    final static boolean DEBUG = false;
    final static boolean TRACE = false;
	public static final String TAG = "BaselineInitiator";
    
    protected UsbDevice                 device;
    protected UsbInterface 				intf;
    protected UsbEndpoint  				epIn;
    protected int                    	inMaxPS;
    protected UsbEndpoint  				epOut;
    protected UsbEndpoint  				epEv;
    protected Session                	session;
    protected DeviceInfo             	info;
    public    UsbDeviceConnection 		mConnection = null; // must be initialized first! 
    	// mUsbManager = (UsbManager)getSystemService(Context.USB_SERVICE);

    
    
    /**
     * Constructs a class driver object, if the device supports
     * operations according to Annex D of the PTP specification.
     *
     * @param device the first PTP interface will be used
     * @exception IllegalArgumentException if the device has no
     *	Digital Still Imaging Class or PTP interfaces
     */
    public BaselineInitiator(UsbDevice dev, UsbDeviceConnection connection) throws PTPException {
        if (connection == null) {
            throw new PTPException ("Connection = null");//IllegalArgumentException();
        }
    	this.mConnection = connection;
//        try {
            if (dev == null) {
                throw new PTPException ("dev = null");//IllegalArgumentException();
            }
            session = new Session();
            this.device = dev;
            intf = findUsbInterface (dev);

//            UsbInterface usbInterface = intf.getUsbInterface();

            if (intf == null) {
            //if (usbInterface == null) {
                throw new PTPException("No PTP interfaces associated to the device");
            }

    		for (int i = 0; i < intf.getEndpointCount(); i++) {
    			UsbEndpoint ep = intf.getEndpoint(i);
    			if (ep.getType() == UsbConstants.USB_ENDPOINT_XFER_BULK) {
    				if (ep.getDirection() == UsbConstants.USB_DIR_OUT) {
    					epOut = ep;
    				} else {
    					epIn = ep;
    				}
    			}
    			if (ep.getType() == UsbConstants.USB_ENDPOINT_XFER_INT){
    				epEv = ep;
    			}
    		}
            endpointSanityCheck();
            inMaxPS = epOut.getMaxPacketSize();

            //UsbDevice usbDevice = dev.getUsbDevice();
//            UsbConfigDescriptor[] descriptors = usbDevice.getConfig();
//
//            if ((descriptors == null) || (descriptors.length < 1)) {
//                throw new PTPException("UsbDevice with no descriptors!");
//            }

            // we want exclusive access to this interface.
            //TODO implement: UsbDeviceConnection.claimInterface (intf, true);
//            dev.open(
//                descriptors[0].getConfigurationValue(),
//                intf.getInterface(),
//                intf.getAlternateSetting()
//            );

            // clear epOut any previous state
            reset();
            if (getClearStatus() != Response.OK
                    && getDeviceStatus(null) != Response.OK) {
                throw new PTPException("can't init");
            }

            Log.d(TAG, "trying getDeviceInfoUncached");
            // get info to sanity check later requests
            info = getDeviceInfoUncached(); 

            // set up to use vendor extensions, if any
            if (info.vendorExtensionId != 0) {
                info.factory = updateFactory(info.vendorExtensionId);
            }
            session.setFactory((NameFactory) this);
//        } catch (USBBusyException e) {
//            throw new PTPBusyException();
//        } catch (USBException e) {
//            throw new PTPException(
//                "Error initializing the communication with the camera (" +
//                e.getMessage()
//                + ")" , e);
//        }
    }

    
	// searches for an interface on the given USB device, returns only class 6  // From androiddevelopers ADB-Test
	private UsbInterface findUsbInterface(UsbDevice device) {
		//Log.d (TAG, "findAdbInterface " + device.getDeviceName());
		int count = device.getInterfaceCount();
		for (int i = 0; i < count; i++) {
			UsbInterface intf = device.getInterface(i);
			Log.d (TAG, "Interface " +i + " Class " +intf.getInterfaceClass() +" Prot " +intf.getInterfaceProtocol());
			if (intf.getInterfaceClass() == 6
					//255 && intf.getInterfaceSubclass() == 66 && intf.getInterfaceProtocol() == 1
					) {
				return intf;
			}
		}
		return null;
	}
    
    
    /**
     * @return the device
     */
    public UsbDevice getDevice() {
        return device;
    }

        /**
     * Returns the last cached copy of the device info, or returns
     * a newly cached copy.
     * @see #getDeviceInfoUncached
     */
    public DeviceInfo getDeviceInfo() throws PTPException {
        if (info == null) {
            return getDeviceInfoUncached();
        }
        return info;
    }

    /**
     * Sends a USB level CLASS_DEVICE_RESET control message.
     * All PTP-over-USB devices support this operation.
     * This is documented to clear stalls and camera-specific suspends,
     * flush buffers, and close the current session.
     *
     * <p> <em>TO BE DETERMINED:</em> How does this differ from a bulk
     * protocol {@link Initiator#resetDevice ResetDevice} command?  That
     * command is documented as very similar to this class operation.
     * Ideally, only this control request will ever be used, since it
     * works even when the bulk channels are halted.
     */
    public void reset() throws PTPException 
    {
//        try {
/*
 * JAVA: public int controlMsg(int requestType,
                      int request,
                      int value,
                      int index,
                      byte[] data,
                      int size,
                      int timeout,
                      boolean reopenOnTimeout)
               throws USBException
               
Android: UsbDeviceConnection controlTransfer (int requestType, int request, int value, int index, byte[] buffer, int length, int timeout)                   	
 */		
    	if (mConnection == null) throw new PTPException("No Connection");
    	
    	mConnection.controlTransfer(
                (int) ( UsbConstants.USB_DIR_OUT      |
                		UsbConstants.USB_TYPE_CLASS        /* |
                        UsbConstants.RECIPIENT_INTERFACE */),
                CLASS_DEVICE_RESET,
                0,
                0,
                new byte[0],
                0,
                DEFAULT_TIMEOUT //,
                //false
            );

            session.close();
//        } catch (USBException e) {
//            throw new PTPException(
//                "Error initializing the communication with the camera (" +
//                e.getMessage()
//                + ")" , e);
//        }
    }

    /**
     * Issues an OpenSession command to the device; may be used
     * with all responders.  PTP-over-USB doesn't seem to support
     * multisession operations; you must close a session before
     * opening a new one.
     */
    public void openSession() throws PTPException {
        Command command;
        Response response;

        synchronized (session) {
            command = new Command(Command.OpenSession, session,
                    session.getNextSessionID());
            response = transactUnsync(command, null);
            switch (response.getCode()) {
                case Response.OK:
                    session.open();
                    Thread thread = new Thread (this);
                    thread.start();
                    return;
                default:
                    throw new PTPException(response.toString());
            }
        }
    }

    /**
     * Issues a CloseSession command to the device; may be used
     * with all responders.
     */
    public void closeSession() throws PTPException {
        Response response;

        synchronized (session) {
            // checks for session already open
            response = transact0(Command.CloseSession, null);
            switch (response.getCode()) {
                case Response.SessionNotOpen:
                    if (DEBUG) {
                        System.err.println("close unopen session?");
                    }
                // FALLTHROUGH
                case Response.OK:
                    session.close();
                    return;
                default:
                    throw new PTPException(response.toString());
            }
        }
    }

    /**
     * Closes the session (if active) and releases the device.
     *
     * @throws PTPException
     */
    public void close() throws PTPException {
        if (isSessionActive()) {
            try {
                closeSession();
            } catch (PTPException ignore) {
                //
                // Is we cannot close the session, there is nothing we can do
                //
            }
        }

        try {
        	if (mConnection != null && intf != null) mConnection.releaseInterface(intf);
            if (mConnection != null) mConnection.close();
        	device = null;
            info = null;
        } catch (Exception ignore) {
            throw new PTPException("Unable to close the USB device");
        }
    }

	public Response showResponse (Response response) {
		Log.d(TAG, "  Type: " + response.getBlockTypeName(response.getBlockType()) +" (Code: " +response.getBlockType() +")\n");   // getU16 (4)
		Log.d(TAG, "  Name: " + response.getCodeName(response.getCode())+ ", code: 0x" +Integer.toHexString(response.getCode()) +"\n"); //getU16 (6)
		//			log ("  CodeString:" + response.getCodeString()+ "\n");
		Log.d(TAG, "  Length: " + response.getLength()+ " bytes\n");  //getS32 (0)
		Log.d(TAG, "  String: " + response.toString());
		return response;
	}

	public void showResponseCode (String comment, int code){
		Log.d(TAG, comment +" Response: " +Response._getResponseString (code) +",  code: 0x" +Integer.toHexString(code));
	}
    
    /**
     * @return true if the current session is active, false otherwise
     */
    public boolean isSessionActive() {
        synchronized (session) {
            return session.isActive();
        }
    }
    
    ///////////////////////////////////////////////////////////////////
    // mandatory for all responders:  generating events
    /**
     * Makes the invoking Thread read and report events reported
     * by the PTP responder, until the Initiator is closed.
     */
    @Override
    public void run() {
    	
    }

    // ------------------------------------------------------- Protected methods

    ///////////////////////////////////////////////////////////////////
    /**
     * Performs a PTP transaction, passing zero command parameters.
     * @param code the command code
     * @param data data to be sent or received; or null
     * @return response; check for code Response.OK before using
     *	any positional response parameters
     */
    protected Response transact0(int code, Data data)
    throws PTPException {
        synchronized (session) {
            Command command = new Command(code, session);
            return transactUnsync(command, data);
        }
    }

    /**
     * Performs a PTP transaction, passing one command parameter.
     * @param code the command code
     * @param data data to be sent or received; or null
     * @param p1 the first positional parameter
     * @return response; check for code Response.OK before using
     *	any positional response parameters
     */
    protected Response transact1(int code, Data data, int p1)
    throws PTPException {
        synchronized (session) {
            Command command = new Command(code, session, p1);
            return transactUnsync(command, data);
        }
    }

    /**
     * Performs a PTP transaction, passing two command parameters.
     * @param code the command code
     * @param data data to be sent or received; or null
     * @param p1 the first positional parameter
     * @param p2 the second positional parameter
     * @return response; check for code Response.OK before using
     *	any positional response parameters
     */
    protected Response transact2(int code, Data data, int p1, int p2)
            throws PTPException {
        synchronized (session) {
            Command command = new Command(code, session, p1, p2);
            return transactUnsync(command, data);
        }
    }

    /**
     * Performs a PTP transaction, passing three command parameters.
     * @param code the command code
     * @param data data to be sent or received; or null
     * @param p1 the first positional parameter
     * @param p2 the second positional parameter
     * @param p3 the third positional parameter
     * @return response; check for code Response.OK before using
     *	any positional response parameters
     */
    protected Response transact3(int code, Data data, int p1, int p2, int p3)
            throws PTPException {
        synchronized (session) {
            Command command = new Command(code, session, p1, p2, p3);
            return transactUnsync(command, data);
        }
    }

    // --------------------------------------------------------- Private methods

        // like getDeviceStatus(),
    // but clears stalled endpoints before returning
    // (except when exceptions are thrown)
    // returns -1 if device wouldn't return OK status
    int getClearStatus() throws PTPException {
        Buffer buf = new Buffer(null, 0);
        int retval = getDeviceStatus(buf);

        // any halted endpoints to clear?  (always both)
        if (buf.length != 4) {
            while ((buf.offset + 4) <= buf.length) {
                int ep = buf.nextS32();
                if (epIn.getAddress() == ep) {
                    if (TRACE) {
                        System.err.println("clearHalt epIn");
                    }
                    clearHalt(epIn);
                } else if (epOut.getAddress() == ep) {
                    if (TRACE) {
                        System.err.println("clearHalt epOut");
                    }
                    clearHalt(epOut);
                } else {
                    if (DEBUG || TRACE) {
                        System.err.println("?? halted EP: " + ep);
                    }
                }
            }

            // device must say it's ready
            int status = Response.Undefined;

            for (int i = 0; i < 10; i++) {
                try {
                    status = getDeviceStatus(null);
                } catch (PTPException x) {
                    if (DEBUG) {
                        x.printStackTrace();
                    }
                }
                if (status == Response.OK) {
                    break;
                }
                if (TRACE) {
                    System.err.println("sleep; status = "
                            + getResponseString(status));
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException x) {
                }
            }
            if (status != Response.OK) {
                retval = -1;
            }
        } else {
            if (TRACE) {
                System.err.println("no endpoints halted");
            }
        }
        return retval;
    }

    // returns Response.OK, Response.DeviceBusy, etc
    // per fig D.6, response may hold stalled endpoint numbers
    private int getDeviceStatus(Buffer buf)
    throws PTPException {
//        try {
    	if (mConnection == null) throw new PTPException("No Connection");
    	
            byte[] data = new byte[33];
            
        	mConnection.controlTransfer(
//            device.controlMsg(
                (int) (UsbConstants.USB_DIR_IN        |
                		UsbConstants.USB_TYPE_CLASS    /*     |
                		UsbConstants.RECIPIENT_INTERFACE*/),
                CLASS_GET_DEVICE_STATUS,
                0,
                0,
                data,
                data.length, // force short reads
                DEFAULT_TIMEOUT //,
                //false
            );

            if (buf == null) {
                buf = new Buffer(data);
            } else {
                buf.data = data;
            }
            buf.offset = 4;
            buf.length = buf.getU16(0);
            if (buf.length != buf.data.length) {
                //throw new PTPException("DeviceStatus error, Buffer length wrong!");
            }

            return buf.getU16(2);
//        }  catch (USBException e) {
//            throw new PTPException(
//                "Error initializing the communication with the camera (" +
//                e.getMessage()
//                + ")" , e);
//        }
    }

    // add event listener
    // rm event listener
    ///////////////////////////////////////////////////////////////////
    // mandatory for all responders
    /**
     * Issues a GetDeviceInfo command to the device; may be used
     * with all responders.  This is the only generic PTP command
     * that may be issued both inside or outside of a session.
     */
    private DeviceInfo getDeviceInfoUncached()
    throws PTPException {
        DeviceInfo data = new DeviceInfo(this);
        Response response;

        synchronized (session) {
//            Log.d(TAG, "getDeviceInfoUncached, sessionID " +session.getSessionId());
            Command command;
            command = new Command(Command.GetDeviceInfo, session);
//            Log.d(TAG, "Command: " +(command.getCode()) +" session: " + session.getSessionId());
            response = transactUnsync(command, data);
//            Log.d(TAG, "getDeviceInfoUncached finished, " +Response._getResponseString(response.getCode()) +" responsecode: " +response.getCode()); 
            
        }

        switch (response.getCode()) {
            case Response.OK:
                info = data;
                return data;
            default:
                throw new PTPException(response.toString());
        }
    }

    ///////////////////////////////////////////////////////////////////
    // INVARIANTS:
    // - caller is synchronized on session
    // - on return, device is always epIn idle/"command ready" state
    // - on return, session was only closed by CloseSession
    // - on PTPException, device (and session!) has been reset
    public Response transactUnsync(Command command, Data data)
    throws PTPException {
        if (!"command".equals(command.getBlockTypeName(command.getBlockType()))) {
            throw new IllegalArgumentException(command.toString());
        }
       // Log.d(TAG, command.toString() + "   Data: " +data.toString());

        // sanity checking
        int opcode = command.getCode();

        if (session.isActive()) {
            if (Command.OpenSession == opcode) {
                throw new IllegalStateException("session already open");
            }
        } else {
            if (Command.GetDeviceInfo != opcode
                    && Command.OpenSession != opcode) {
                throw new IllegalStateException("no session");
            }
        }

        // this would be UnsupportedOperationException ...
        // except that it's not available on jdk 1.1
        if (info != null && !info.supportsOperation(opcode)) {
            throw new UnsupportedOperationException(command.getCodeName(opcode));
        }

        // ok, then we'll really talk to the device
        Response response;
        boolean abort = true;

//        try {
//            OutputStream stream = device.getOutputStream(epOut);

            // issue command
            // rejected commands will stall both EPs
            if (TRACE) {
                System.err.println(command.toString());
            }
            int lenC = mConnection.bulkTransfer(epOut, command.data , command.length , DEFAULT_TIMEOUT);
//			Log.d(TAG, "Command bytes sent " +lenC);
//            stream.write(command.data, 0, command.length);

            // may need to terminate request with zero length packet
            if ((command.length % epOut.getMaxPacketSize()) == 0) {
				lenC = mConnection.bulkTransfer(epOut, command.data, 0, DEFAULT_TIMEOUT);
//				Log.d(TAG, "0 sent bytes:" +lenC);
                //stream.write(command.data, 0, 0);
            }

            // data exchanged?
            // errors or cancel (another thread) will stall both EPs
            if (data != null) {

                // write data?
                if (!data.isIn()) {
//                	Log.d(TAG, "Start Write Data");

                    data.offset = 0;
                    data.putHeader(data.getLength(), 2 /*Data*/, opcode,
                            command.getXID());

                    if (TRACE) {
                        System.err.println(data.toString());
                    }

                    // Special handling for the read-from-N-mbytes-file case
                    //TODO yet to be implemented
//                    if (data instanceof FileSendData) {
//                        FileSendData fd = (FileSendData) data;
//                        int len = fd.data.length - fd.offset;
//                        int temp;
//
//                        // fill up the rest of the first buffer
//                        len = fd.read(fd.data, fd.offset, len);
//                        if (len < 0) {
//                            throw new PTPException("eh? " + len);
//                        }
//                        len += fd.offset;
//
//                        for (;;) {
//                            // write data or terminating packet
//                        	mConnection.bulkTransfer(epOut, data , command.length , DEFAULT_TIMEOUT);
//                            //stream.write(fd.data, 0, len);
//                            if (len != fd.data.length) {
//                                break;
//                            }
//
//                            len = fd.read(fd.data, 0, fd.data.length);
//                            if (len < 0) {
//                                throw new PTPException("short: " + len);
//                            }
//                        }
//
//                    } else {
                        // write data and maybe terminating packet
                    byte[] bytes = data.getData();//new byte [data.length];
//    				Log.d(TAG, "send Data");
                    int len = mConnection.bulkTransfer(epOut, bytes , bytes.length, DEFAULT_TIMEOUT);
//    				Log.d(TAG, "bytes sent " +len);
                    if (len < 0) {
                    	throw new PTPException("short: " + len);
                    }

                        //stream.write(data.data, 0, data.length);
                        if ((data.length % epOut.getMaxPacketSize()) == 0) {
//                        	Log.d(TAG, "send 0 Data");
                        	mConnection.bulkTransfer(epOut, bytes , 0, DEFAULT_TIMEOUT);
                            //stream.write(data.data, 0, 0);
//                        }
                    }

                    // read data?
                } else {
// Log.d(TAG, "Start Read Data");
					byte readBuffer[] = new byte[inMaxPS];
					int readLen = 0;
					readLen = mConnection.bulkTransfer(epIn, readBuffer, inMaxPS,
							DEFAULT_TIMEOUT);
					data.data = readBuffer;
					data.length = readLen;
					if (!"data".equals(data.getBlockTypeName(data.getBlockType()))
							|| data.getCode() != command.getCode()
							|| data.getXID() != command.getXID()) {
						throw new PTPException("protocol err 1, " + data);
					}
					
					int totalLen = data.getLength();
					if (totalLen > readLen) {
						ByteArrayOutputStream dataStream = new ByteArrayOutputStream(
								totalLen);
					
						dataStream.write(readBuffer, 0, readLen);
						
						int remaining = totalLen - readLen;
						while (remaining > 0) {
							int toRead = (remaining > inMaxPS )? inMaxPS : remaining;
							readLen = mConnection.bulkTransfer(epIn, readBuffer, toRead,
									DEFAULT_TIMEOUT);
							dataStream.write(readBuffer, 0, readLen);
							remaining -= readLen;
						}
						
						data.data = dataStream.toByteArray();
						data.length = data.length;
					}
                    data.parse();
                }
            }

            // (short) read the response
            // this won't stall anything
            byte buf[] = new byte[inMaxPS];
//            Log.d(TAG, "read response");
            int len = mConnection.bulkTransfer(epIn, buf ,inMaxPS , DEFAULT_TIMEOUT);//device.getInputStream(epIn).read(buf);
//            Log.d(TAG, "received data bytes: " +len);
            
            // ZLP terminated previous data?
            if (len == 0) {
                len = mConnection.bulkTransfer(epIn, buf ,inMaxPS , DEFAULT_TIMEOUT);// device.getInputStream(epIn).read(buf);
//                Log.d(TAG, "received data bytes: " +len);
            }

            response = new Response(buf, len, this);
            if (TRACE) {
                System.err.println(response.toString());
            }

            abort = false;
            return response;

            //TODO implement stall detection
//       } catch (USBException e) {
//            if (DEBUG) {
//                e.printStackTrace();
//            }
//
//            // PTP devices will stall bulk EPs on error ... recover.
//            if (e.isStalled()) {
//                int status = -1;
//
//                try {
//                    // NOTE:  this is the request's response code!  It can't
//                    // be gotten otherwise; despite current specs, this is a
//                    // "control-and-bulk" protocol, NOT "bulk-only"; or more
//                    // structurally, the protocol handles certain operations
//                    // concurrently.
//                    status = getClearStatus();
//                } catch (PTPException x) {
//                    if (DEBUG) {
//                        x.printStackTrace();
//                    }
//                }
//
//                // something's very broken
//                if (status == Response.OK || status == -1) {
//                    throw new PTPException(e.getMessage(), e);
//                }
//
//                // treat status code as the device's response
//                response = new Response(new byte[Response.HDR_LEN], this);
//                response.putHeader(Response.HDR_LEN, 3 /*response*/,
//                        status, command.getXID());
//                if (TRACE) {
//                    System.err.println("STALLED: " + response.toString());
//                }
//
//                abort = false;
//                return response;
//            }
//            throw new PTPException(e.getMessage(), e);
//
//        } catch (IOException e) {
//            throw new PTPException(e.getMessage(), e);
//
//        } finally {
//            if (abort) {
//                // not an error we know how to recover;
//                // bye bye session!
//                reset();
//            }
//        }
    }

    private void endpointSanityCheck() throws PTPException {
        if (epIn == null) {
            throw new PTPException("No input end-point found!");
        }

        if (epOut == null) {
            throw new PTPException("No output end-point found!");
        }

        if (epEv == null) {
            throw new PTPException("No input interrupt end-point found!");
        }
        if (DEBUG){
    		Log.d(TAG, "Get: "+device.getInterfaceCount()+" Other: "+device.getDeviceName());
    		Log.d(TAG, "\nClass: "+intf.getInterfaceClass()+","+intf.getInterfaceSubclass()+","+intf.getInterfaceProtocol()
    			      + "\nIendpoints: "+epIn.getMaxPacketSize()+ " Type "+ epIn.getType() + " Dir "+epIn.getDirection()); //512 2 USB_ENDPOINT_XFER_BULK USB_DIR_IN 
    		Log.d(TAG, "\nOendpoints: "+epOut.getMaxPacketSize()+ " Type "+ epOut.getType() + " Dir "+epOut.getDirection()); //512 2 USB_ENDPOINT_XFER_BULK USB_DIR_OUT 
    		Log.d(TAG, "\nEendpoints: "+epEv.getMaxPacketSize()+ " Type "+ epEv.getType() + " Dir "+epEv.getDirection()); //8,3 USB_ENDPOINT_XFER_INT USB_DIR_IN  
        	
        }
    }

    private void clearHalt(UsbEndpoint e) {
        //
        // TODO: implement clearHalt of an endpoint
        //
    }
    //Ash code
    
	public void writeExtraData(Command command, Data data, int timeout)
	{
		int lenC = mConnection.bulkTransfer(epOut, command.data , command.length , timeout);
		
        if ((command.length % epOut.getMaxPacketSize()) == 0) {
			lenC = mConnection.bulkTransfer(epOut, command.data, 0, timeout);
        }
		////////////////////////////////////	
		int opcode = command.getCode();
		data.offset = 0;
        data.putHeader(data.getLength(), 2 , opcode, command.getXID());
        byte[] bytes = data.getData();
        

        mConnection.bulkTransfer(epOut, data.getData() , data.length , timeout);
        
        if ((data.length % epOut.getMaxPacketSize()) == 0) {
        	mConnection.bulkTransfer(epOut, bytes , 0, timeout);

        }
	
	}
    
    
	/*************************************************************************************
	 *                                                                                   *
	 *	  Methods to be overridden in camera-specific instances of baselineInititator    *
	 *                                                                                   *
	 *************************************************************************************/

	// this is an abstract method to be declared in
	public Response initiateCapture(int storageId, int formatCode)
			throws PTPException {
		return null;
	}
	public Response startBulb () throws PTPException{
		return null;
	}

	public Response stopBulb () throws PTPException{
		return null;
	}
	public Response setShutterSpeed (int speed) throws PTPException{
		return null;
	}  

	public Response setExposure(int exposure) throws PTPException{    	
		return null;
	}

	public Response setISO(int value) throws PTPException{
		return null;
	}

	public Response setAperture(int value) throws PTPException{
		return null;
	}

	public Response setImageQuality(int value) throws PTPException{
		return null;
	}

	// Floating point adapters
	public Response setShutterSpeed (double timeSeconds) throws PTPException{
		return null;
	}

	public Response setAperture(double apertureValue) throws PTPException{
		return null;
	}

	// Sets ISO to 50, 100, 200... or nearest value
	public Response setISO(double isoValue) throws PTPException{
		return null;
	}

	public Response setExposure(double exposureValue) throws PTPException{  
		Log.d(TAG, "Not overriden!!!");
		return null;
	}

	/**  Selects image Quality from "S" to "RAW" in 4 steps
	 * 
	 */
	public Response setImageQuality (String quality) throws PTPException{
		return null;
	}

	////////////////////////Ash
	public Response setDevicePropValueEx (int x, int y) throws PTPException{
		return null;
	}
	
	public Response MoveFocus (int x) throws PTPException{
		return null;
	}
	public Response setPictureStyle (int x) throws PTPException{
		return null;
	}
	public Response setWhiteBalance (int x) throws PTPException{
		return null;
	}
	
	public Response setMetering (int x) throws PTPException{
		return null;
	}
	public Response setDriveMode (int x) throws PTPException{
		return null;
	}
	
	public DevicePropDesc getPropValue (int value) throws PTPException{
		return null;
	}
	
	public void setupLiveview () throws PTPException{
		
	}
	
	public void getLiveView(ImageView x){
		
	}
	
	
	public byte[] read(int timeout)
	{
		Log.d(TAG,"Reading data");
		byte data[] = new byte[inMaxPS];	
		//int lengthOfBytes = mConnection.bulkTransfer(epIn, data , inMaxPS , timeout);
		
		int retries=10;
		int tmp=-1;
		for(int i=0;i<retries;retries--){
			tmp= mConnection.bulkTransfer(epIn, data , inMaxPS , timeout);
			if(tmp<0)
				Log.e(TAG,"Reading failed, retry");
			else
				break;
		}
		
		
		return data;
		
	}
	public void write(byte[] data, int length, int timeout)
	{
		Log.d(TAG,"Sending command");
		mConnection.bulkTransfer(epOut, data , length , timeout);
		
	}
	
	/////////////////////////
	public void setFocusPos(int x, int y)
	{


	}
	
	public void setZoom(int zoomLevel)
	{


	}
	
	public void doAutoFocus()
	{


	}
	
}
