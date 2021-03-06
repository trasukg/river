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
package com.sun.jini.test.spec.security.proxytrust.proxytrustinvocationhandler;

import java.util.logging.Level;

// com.sun.jini
import com.sun.jini.test.spec.security.proxytrust.util.RMCTEImpl;
import com.sun.jini.test.spec.security.proxytrust.util.FakeException;


/**
 * Remote non-public class implementing RemoteMethodControl, TrustEquivalence
 * and NonPublicInterface interfaces.
 */
class RMCTENPINonPublicClass extends RMCTEImpl implements NonPublicInterface {

    /**
     * Test method.
     */
    public void test() {}

    /**
     * Method from NonPublicInterface interfaces. Does nothing.
     *
     * @return -1
     */
    public int test1(int i) {
        return -1;
    }

    /**
     * Method from NonPublicInterface interfaces. Does nothing.
     */
    public void exTest() throws FakeException {}
}
