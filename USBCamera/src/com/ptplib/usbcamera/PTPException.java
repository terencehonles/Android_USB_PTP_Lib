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

public class PTPException extends Exception {

    private int errorCode;

    public PTPException() {
        this("");
    }

    public PTPException(int errorCode) {
        this("", null, errorCode);
    }

    public PTPException(String string) {
        this(string, null);
    }

    public PTPException(String string, int errorCode) {
        this(string, null, errorCode);
    }

    public PTPException(String string, Throwable t) {
        this(string, t, Response.Undefined);
    }

    public  PTPException(String string, Throwable t, int errorCode) {
        super(string, t);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
