/* Copyright 2010 by Stefano Fornari
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.ptplib.usbcamera.nikon;

import java.io.IOException;
import java.io.InputStream;

import android.util.Log;

import com.ptplib.usbcamera.PTPException;
import com.ptplib.usbcamera.PTPUnsupportedException;

/**
 * This class parses a stream of bytes as a sequence of events accordingly
 * to how Canon EOS returns events.
 *
 * The event information is returned in a standard PTP data packet as a number
 * of records followed by an empty record at the end of the packet. Each record
 * consists of multiple four-byte fields and always starts with record length
 * field. Further structure of the record depends on the device property code
 * which always goes in the third field. The empty record consists of the size
 * field and four byte empty field, which is always zero.
 *
 * @author stefano fornari
 */
public class NikonEventParser implements NikonEventConstants {

    /**
     * The stream data are read from
     */
    private InputStream is;

    /**
     * Creates a new parser to parse the given input stream
     * 
     * @param is
     */
    public NikonEventParser(InputStream is) {
        if (is == null) {
            throw new IllegalArgumentException("The input stream cannot be null");
        }
        
        this.is = is;
    }

    /**
     * Returns true is there are events in the stream (and the stream is still
     * open), false otherwise.
     *
     * @return true is there are events in the stream (and the stream is still
     * open), false otherwise.
     */
    public boolean hasEvents() {
        try {
//        	Log.d("EventParser", "   available: " +is.available());
            if (is.available() <= 0) {
                return false;
            }
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    /**
     * Returns the next event in the stream.
     *
     * @return the next event in the stream.
     * 
     * @throws PTPException in case of errors
     */
    public NikonEvent getNextEvent() throws PTPException {
        NikonEvent event = new NikonEvent();

        try {
            int len = getNextS32(); // len
//            Log.d("EventParser", "   Len: " +len);
            if (len < 0x8) {
                throw new PTPUnsupportedException("Unsupported event (size<8 ???)");
            }
            int code = getNextS32();
            event.setCode(code);
            Log.d("EventParser", "   Event len: " +len +", Code: 0x" +String.format("%04x", code) +" " +NikonEvent.getEventName(code));
            parseParameters(event, len-8);
//            Log.d("EventParser", "   Event: params " +event.getParamCount());
            for (int i = 1; i<= event.getParamCount(); i++)
            Log.d("EventParser", "          params " +i +": " +String.format("0x%04x  %d",event.getParam(i), event.getParam(i)));
        } catch (IOException e) {
        	Log.d ("EventParser", "   Error reading event stream");
            throw new PTPException("Error reading event stream", e);
        }

        return event;
    }


    // --------------------------------------------------------- Private methods

    private void parseParameters(NikonEvent event, int len)
    throws PTPException, IOException {
        int code = event.getCode();
        
        if (code == EosEventPropValueChanged) {
            parsePropValueChangedParameters(event);
        } else if (code == EosEventShutdownTimerUpdated) {
            //
            // No parameters
            //
        } else if (code == EosEventCameraStatusChanged) {
             event.setParam(1, getNextS32());
        } else if (code == EosEventObjectAddedEx) {
             parseEosEventObjectAddedEx(event);
        } else{
            is.skip(len);
            throw new PTPUnsupportedException("Unsupported event");
        }
    }

    private void parsePropValueChangedParameters(NikonEvent event)
    throws IOException {
        int property = getNextS32();
        event.setParam(1, property);  // property changed

        if ((property >= EosPropPictureStyleStandard) &&
            (property <= EosPropPictureStyleUserSet3)) {
            boolean monochrome = (property == EosPropPictureStyleMonochrome);
            int size = getNextS32();
            if (size > 0x1C) {
                //
                // It is a EosPropPictureStyleUserXXX, let's read the type (then
                // we do not use it)
                //
                monochrome = (getNextS32() == EosPropPictureStyleUserTypeMonochrome);
            }
            event.setParam(2, (monochrome) ? Boolean.TRUE : Boolean.FALSE);
            event.setParam(3, getNextS32()); // contrast
            event.setParam(4, getNextS32()); // sharpness
            if (monochrome) {
                getNextS32();
                getNextS32();
                event.setParam(5, getNextS32()); // filter effect
                event.setParam(6, getNextS32()); // toning effect
            } else {
                event.setParam(5, getNextS32()); // saturation
                event.setParam(6, getNextS32()); // color tone
                getNextS32();
                getNextS32();
            }
        } else {
            //
            // default
            //
            event.setParam(2, getNextS32());
        }
        
    }

    private void parseEosEventObjectAddedEx(NikonEvent event)
    throws IOException {
        event.setParam(1, getNextS32()  );  // object id
        event.setParam(2, getNextS32()  );  // storage id
        event.setParam(4, getNextS16()  );  // format
        is.skip(10);
        event.setParam(5, getNextS32()  );  // size 
        event.setParam(3, getNextS32()  );  // parent object id
        is.skip(4);  // unknown
        event.setParam(6, getNextString()); // file name
        is.skip(4);
    }

    /**
     * Reads and return the next signed 32 bit integer read from the input
     * stream.
     *
     * @return the next signed 32 bit integer in the stream
     *
     * @throws IOException in case of IO errors
     */
    private  final int getNextS32() throws IOException {
	int retval;

	retval  = (0xff & is.read()) ;
	retval |= (0xff & is.read()) << 8;
	retval |= (0xff & is.read()) << 16;
	retval |=         is.read()  << 24;

	return retval;
    }

    /**
     * Reads and return the next signed 16 bit integer read from the input
     * stream.
     *
     * @return the next signed 16 bit integer in the stream
     *
     * @throws IOException in case of IO errors
     */
    private final int getNextS16() throws IOException {
	int retval;

	retval  = (0xff & is.read()) ;
	retval |= (0xff & is.read()) << 8;

	return retval;
    }

    /**
     * Reads and return the next string read from the input stream. Strings are
     * zero (32 bit) terminated string
     *
     * @return the next string in the stream
     *
     * @throws IOException in case of IO errors
     */
    private final String getNextString() throws IOException {
        StringBuilder retval = new StringBuilder();

        char c = 0;
        while ((c = (char)is.read()) != 0) {
            retval.append(c);
        }

        //
        // At this point we read the string and one zero. We need to read the
        // remaining 3 zeros
        //
        is.skip(3);

        return retval.toString();
    }

}
