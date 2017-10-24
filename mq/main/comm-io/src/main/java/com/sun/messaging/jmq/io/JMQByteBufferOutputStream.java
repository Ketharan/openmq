/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2000-2017 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

/*
 * @(#)JMQByteBufferOutputStream.java	1.3 06/27/07
 */ 

package com.sun.messaging.jmq.io;

import java.io.OutputStream;
import java.io.IOException;
import java.io.EOFException;
import java.nio.ByteBuffer;
import java.nio.BufferOverflowException;

/**
 * This class implements an output stream in which the data is 
 * written into a ByteBuffer. The buffer is provided by the 
 * caller, and it does NOT grow.
 * The data can be retrieved using <code>getByteBuffer()</code>
 *
 */
public class JMQByteBufferOutputStream extends OutputStream {

    /** 
     * The buffer where data is stored. 
     */
    protected ByteBuffer buf = null;

    /**
     * Flag indicating whether the stream has been closed.
     */
    //private boolean isClosed = false;

    /** Check to make sure that the stream has not been closed */
    private void ensureOpen() {
        /* This method does nothing for now.  Once we add throws clauses
	 * to the I/O methods in this class, it will throw an IOException
	 * if the stream has been closed.
	 */
    }

    /**
     * Creates a new byte buffer output stream using the specified
     * ByteBuffer as the backing store. The buffer is used
     * exactly "as-is". It is not copied, duplicated or sliced.
     * Writes to the output stream will start writing to the buffer
     * at its current position. The buffer's position will be updated
     * as data is written to the output stream.
     * If the buffer fills up, it is not re-allocated.
     */
    public JMQByteBufferOutputStream(ByteBuffer buf) {
	this.buf = buf;
    }

    /**
     * Writes the specified byte to this byte buffer output stream. 
     * If the backing buffer fills up an BufferOverflowException is thrown
     * (i.e. the
     * buffer does not grow). The backing buffer's position is updated.
     *
     * @param   b   the byte to be written.
     */
    public synchronized void write(int b)
        throws BufferOverflowException {
	ensureOpen();

        buf.put((byte)b);
    }

    /**
     * Writes <code>len</code> bytes from the specified byte array 
     * starting at offset <code>off</code> to this byte array output stream.
     * If the backing buffer fills up an EOFException is thrown (i.e. the
     * buffer does not grow). The backing buffer's position is updated.
     *
     * @param   b     the data.
     * @param   off   the start offset in the data.
     * @param   len   the number of bytes to write.
     */
    public synchronized void write(byte b[], int off, int len)
        throws BufferOverflowException {
	ensureOpen();

	if ((off < 0) || (off > b.length) || (len < 0) ||
            ((off + len) > b.length) || ((off + len) < 0)) {
	    throw new IndexOutOfBoundsException();
	} else if (len == 0) {
	    return;
	}

        buf.put(b, off, len);
    }

    /**
     * Clears the backing buffer so that all currently accumulated output
     * in the output stream is disgarded. The output stream can be
     * used again, reusing the already allocated buffer space.
     *
     * @see     java.io.ByteArrayInputStream#count
     */
    public synchronized void reset() {
	ensureOpen();
	buf.clear();
    }

    /**
     * Return the backing-store byte buffer. The exact buffer is returned.
     * It is NOT a copy, slice or duplicate. The buffer's position will
     * is whatever the current position is.
     *
     * @return  the current contents of this output stream, as a ByteBuffer
     */
    public synchronized ByteBuffer getByteBuffer() {
	return buf;
    }

    /**
     * Returns the current size of the buffer.
     *
     * @return  the ByteBuffer's <code>capacity</code>, which is the number
     *          of valid bytes in this output stream.
     */
    public int size() {
	return buf.capacity();
    }

    /**
     * Closes this output stream and releases any system resources 
     * associated with this stream. A closed stream cannot perform 
     * output operations and cannot be reopened.
     * <p>
     *
     */
    public synchronized void close() throws IOException {
	//isClosed = true;
	buf = null;
    }
}
