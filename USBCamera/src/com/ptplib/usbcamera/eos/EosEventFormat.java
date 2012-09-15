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
package com.ptplib.usbcamera.eos;

import java.lang.reflect.Field;

/**
 * This class formats an EosEvent to a string
 *
 * @author stefano fornari
 */
public class EosEventFormat implements EosEventConstants {
    public static String format(EosEvent e) {
        StringBuilder sb = new StringBuilder();

        int eventCode = e.getCode();

        sb.append(getEventName(eventCode));
        sb.append(" [ ");
        if (eventCode == EosEventPropValueChanged) {
            int propCode = e.getIntParam(1);
            sb.append(getPropertyName(propCode))
              .append(": ");

            if ((propCode >= EosPropPictureStyleStandard) &&
                (propCode <= EosPropPictureStyleUserSet3)) {
                sb.append("(Sharpness: ")
                  .append(e.getIntParam(4))
                  .append(", Contrast: ")
                  .append(e.getIntParam(3));
                if (((Boolean)e.getParam(2)).booleanValue()) {
                  sb.append(", Filter effect: ")
                    .append(getFilterEffect(e.getIntParam(5)))
                    .append(", Toning effect: ")
                    .append(getToningEffect(e.getIntParam(6)));
                } else {
                  sb.append(", Saturation: ")
                    .append(e.getIntParam(5))
                    .append(", Color tone: ")
                    .append(e.getIntParam(6));
                }
                sb.append(")");
            } else {
                if (e.getParamCount()>1) {
                    sb.append(e.getIntParam(2));
                }
            }
        } else if (eventCode == EosEventObjectAddedEx) {
            sb.append(formatEosEventObjectAddedEx(e));
        }
        sb.append(" ]");

        return sb.toString();
    }

    /**
     * Returns the printable name of the given event
     *
     * @param code event code
     *
     * @return the printable name of the given event
     */
    public static String getEventName(int code) {
        Field[] fields = EosEventConstants.class.getDeclaredFields();

        for (Field f: fields) {
            String name = f.getName();
            if (name.startsWith("EosEvent")) {
                try {
                    if (f.getInt(null) == code) {
                        return name;
                    }
                } catch (Exception e) {
                    //
                    // Nothing to do
                    //
                }
            }
        }
        return "Unknown";
    }

    /**
     * Returns the printable name of the given property
     *
     * @param code property code
     *
     * @return the printable name of the given property
     */
    public static String getPropertyName(int code) {
        return getCodeName("EosProp", code);
    }

    /**
     * Returns the printable name of the given image format
     *
     * @param code image format code
     *
     * @return the printable name of the given image format
     */
    public static String getImageFormatName(int code) {
        return getCodeName("ImageFormat", code);
    }

    /**
     * Returns the filter effect name given the code. Names are: <br>
     * 0:None, 1:Yellow, 2:Orange, 3:Red, 4:Green
     *
     * @param code the filter effect code (0-4)
     *
     * @return the filter effect name
     */
    public static String getFilterEffect(int code) {
        if ((code < 0) || (code > 4)) {
            throw new IllegalArgumentException("code must be in he range 0-4");
        }

        switch (code) {
            case 0: return "None";
            case 1: return "Yellow";
            case 2: return "Orange";
            case 3: return "Red";
            case 4: return "Green";
        }

        //
        // We should never get here
        //
        return "Unknown";
    }

    /**
     * Returns the toning effect name given the code. Names are: <br>
     * 0:None, 1:Sepia, 2:Blue, 3:Purple, 4:Green
     *
     * @param code the toning effect code (0-4)
     *
     * @return the toning effect name
     */
    public static String getToningEffect(int code) {
        if ((code < 0) || (code > 4)) {
            throw new IllegalArgumentException("code must be in he range 0-4");
        }

        switch (code) {
            case 0: return "None";
            case 1: return "Sepia";
            case 2: return "Blue";
            case 3: return "Purple";
            case 4: return "Green";
        }

        //
        // We should never get here
        //
        return "Unknown";
    }

    // --------------------------------------------------------- Private methods

    private static String formatEosEventObjectAddedEx(EosEvent event) {
        return String.format(
            "Filename: %s, Size(bytes): %d, ObjectID: 0x%08X, StorageID: 0x%08X, ParentID: 0x%08X, Format: %s",
            event.getStringParam(6),
            event.getIntParam(5),
            event.getIntParam(1),
            event.getIntParam(2),
            event.getIntParam(3),
            getImageFormatName(event.getIntParam(4))
        );
    }

    /**
     * Looks up and returns the name of the code give if there is a constant
     * field in EosEventConstants which starts with the given prefix
     *
     * @param prefix the field name prefix
     * @param code image format code
     *
     * @return the printable name of the given code
     */
    private static String getCodeName(String prefix, int code) {
        Field[] fields = EosEventConstants.class.getDeclaredFields();

        for (Field f: fields) {
            String name = f.getName();
            if (name.startsWith(prefix)) {
                try {
                    if (f.getInt(null) == code) {
                        return name.substring(prefix.length());
                    }
                } catch (Exception e) {
                    //
                    // Nothing to do
                    //
                }
            }
        }
        return "Unknown";
    }
            
}
