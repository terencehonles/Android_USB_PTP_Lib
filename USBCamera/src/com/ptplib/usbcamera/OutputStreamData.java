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
package com.ptplib.usbcamera;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Used with {@link BaselineInitiator#fillObject fillObject}, this writes
 * objects to a give output stream.
 *
 * @see BaselineInitiator#fillObject
 *
 * @author ste
 */
public class OutputStreamData extends Data {

    private OutputStream out;

    /**
     * Constructs a data object which fills the given underlying file.
     */
    public OutputStreamData(OutputStream o, NameFactory f) {
        super(f);
        out = o;
    }

    /**
     * Writes object data to the underlying output stream
     */
    public void write(byte buf[], int off, int len) throws IOException {
        out.write(buf, off, len);
    }

    /**
     * Closes the underlying output stream.
     */
    public void close() throws IOException {
        out.close();
    }

    @Override
    final void parse() {
    }
}
