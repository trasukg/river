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
package com.sun.jini.test.spec.security.util;

// java
import java.lang.SecurityManager;

// net.jini
import net.jini.security.SecurityContext;
import net.jini.security.policy.SecurityContextSource;


/**
 * Security manager implementing SecurityContextSource interface.
 */
public class SCSSecurityManager extends SecurityManager
        implements SecurityContextSource {

    /** SecurityContext which will be returned by getContext() method. */
    protected SecurityContext sc = new FakeSecurityContext();

    /** How much times getContext method was called. */
    protected int gcCallsNum = 0;

    /**
     * Returns test SecurityContext.
     *
     * @return test SecurityContext
     */
    public SecurityContext getContext() {
        ++gcCallsNum;
        return sc;
    }

    /**
     * Returns how much times getContext method was called.
     *
     * @return how much times getContext method was called
     */
    public int getCallsNum() {
        return gcCallsNum;
    }
}
