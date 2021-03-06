/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*  */

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;

public class Bar implements ConnectBack {
    public void connect(final URL location) throws IOException {
	try {
	    AccessController.doPrivileged(new Action(location));
	} catch (java.security.AccessControlException e) {
	    throw (java.security.AccessControlException) e;
	} catch (Exception e) {
	    throw (IOException) e;
	}
    }

    private static class Action implements PrivilegedExceptionAction {
	private final URL location;
	Action(URL location) { this.location = location; }
	public Object run() throws Exception {
	    InputStream is = location.openStream();
	    int read = is.read(new byte[is.available()]);
	    System.err.println("read bytes from bar class file: " + read);
	    return null;
	}
    }
}
