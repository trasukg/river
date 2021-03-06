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
package com.sun.jini.test.spec.lookupservice.test_set02;
import com.sun.jini.qa.harness.QAConfig;

import java.util.logging.Level;
import com.sun.jini.qa.harness.TestException;

import com.sun.jini.test.spec.lookupservice.QATestRegistrar;
import net.jini.core.lookup.ServiceTemplate;
import net.jini.core.entry.Entry;
import java.rmi.RemoteException;

/**
 * This class is used to verify that doing a getFieldValues with a
 * bad set index results in an IndexOutOfBoundsException.
 */
public class GetFieldValuesBadIndex extends QATestRegistrar {

    public void run() throws Exception {
	try {
	    getProxy().getFieldValues(new ServiceTemplate(null, null,
							  new Entry[0]),
				      1, "foo");
	    throw new TestException("getFieldValues did not "
				  + "throw IndexOutOfBoundsException");
	} catch (IndexOutOfBoundsException e) {
	}
    }
}
