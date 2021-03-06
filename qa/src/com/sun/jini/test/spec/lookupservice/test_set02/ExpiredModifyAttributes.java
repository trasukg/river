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

import java.rmi.RemoteException;
import java.io.IOException;
import com.sun.jini.test.spec.lookupservice.QATestRegistrar;
import com.sun.jini.test.spec.lookupservice.QATestUtils;
import net.jini.core.lookup.ServiceRegistrar;
import net.jini.core.lookup.ServiceItem;
import net.jini.core.lookup.ServiceRegistration;
import net.jini.core.lookup.ServiceMatches;
import net.jini.core.lookup.ServiceTemplate;
import net.jini.core.lease.*;
import net.jini.core.event.*;
import net.jini.core.entry.Entry;

/** This class is used to test UnkonwLeaseException throws by modifyAttributes().
 *  It create nInstances of ServiceItems, and register them into a lookup
 *  service. Wait for the lease to expire, then call modifAttributes on each 
 *  services. Verify that UnkonwLeaseException is throw every time we call
 *  modifyAtributes().
 */
public class ExpiredModifyAttributes extends QATestRegistrar {
    /** the expected number of matches when testing lookup by ID */
    private static int EXPECTED_N_MATCHES = 1;
    /* lease duration to 10 seconds */
    private final static long DEFAULT_LEASEDURATION = 10000;
    private static long leaseDuration = DEFAULT_LEASEDURATION;
    private ServiceRegistration[] srvcRegs ;
    private ServiceItem[] srvcItems ;
    private int nInstances = 0;
    private long leaseStartTime; 
    private Entry[] attrEntries;
    private Entry[] modAttrs;
    private int attrsLen;

    public void setup(QAConfig sysConfig) throws Exception {
	super.setup(sysConfig);
	attrEntries = super.createAttributes(ATTR_CLASSES);
	modAttrs = new Entry[attrEntries.length];
	for(int i=0; i<modAttrs.length;i++) {
	    modAttrs[i] = null;
	}
        nInstances = super.getNInstances();
	srvcItems = super.createServiceItems(TEST_SRVC_CLASSES);
 	srvcRegs = registerAll(leaseDuration);
	leaseStartTime = QATestUtils.getCurTime();
    }

    /** Executes the current QA test. */
    public void run() throws Exception {
	QATestUtils.computeDurAndWait(leaseStartTime, leaseDuration + 1000);
	doModifyAttributes();
    }

    private void doModifyAttributes() throws Exception {
	for(int i=0; i<srvcRegs.length; i++) {
	    try {
		srvcRegs[i].modifyAttributes(attrEntries, modAttrs);
                throw new TestException("UnknownLeaseException was not thrown");
	    } catch (UnknownLeaseException e) {
	    }
	}
    }
}
