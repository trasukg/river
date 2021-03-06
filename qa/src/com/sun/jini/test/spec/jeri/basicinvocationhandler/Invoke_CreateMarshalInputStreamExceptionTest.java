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
package com.sun.jini.test.spec.jeri.basicinvocationhandler;

import java.util.logging.Level;


import java.util.logging.Level;
import java.lang.reflect.UndeclaredThrowableException;
import java.io.IOException;
import java.rmi.UnknownHostException;
import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.MarshalException;
import java.rmi.UnmarshalException;
import java.rmi.ConnectIOException;

/**
 * <pre>
 * Purpose
 *   This test verifies the behavior of the BasicInvocationHandler.invoke
 *   method when an exception is thrown by 
 *   BasicInvocationHandler.createMarshalInputStream
 *
 * Test Cases
 *   This test iterates over a set of exceptions and boolean values.
 *   Each {firstResponseByte,deliveryStatus,exception} triple denotes one 
 *   test case and is defined by the variables:
 *      byte firstResponseByte
 *      byte deliveryStatus
 *      Throwable createMarshalInputStreamException
 *
 * Infrastructure
 *   This test requires the following infrastructure:
 *     1) FakeInterface
 *          -an interface which declares one method that throws Throwable
 *     2) FakeObjectEndpoint
 *          -newCall returns OutboundRequestIterator passed to constructor
 *          -executeCall method returns null
 *     3) FakeOutboundRequestIterator
 *          -hasNext method returns true on first call and false after that
 *          -next method returns OutboundRequest passed to constructor
 *           and throws NoSuchElementException if called more than once
 *     4) FakeOutboundRequest
 *          -abort method does nothing
 *          -getDeliveryStatus method returns deliveryStatus
 *          -getRequestOutputStream method returns a ByteArrayOutputStream
 *          -getResponseInputStream method returns passed in 
 *           ByteArrayInputStream
 *          -getUnfulfilledConstraints method return InvocationConstraints.EMPTY
 *          -populateContext method does nothing
 *     5) FakeBasicInvocationHandler
 *          -subclasses BasicInvocationHandler
 *          -overloaded createMarshalInputStream
 *           throws createMarshalInputStreamException
 *
 * Actions
 *   For each test case the test performs the following steps:
 *     1) construct a FakeOutboundRequest
 *     2) construct a FakeOutboundRequestIterator,passing in FakeOutboundRequest
 *     3) construct a FakeObjectEndpoint, passing in FakeOutboundRequestIterator
 *     4) construct a FakeBasicInvocationHandler, passing in FakeObjectEndpoint
 *        and FakeMethodConstraints
 *     5) create a dynamic proxy for the FakeInterface using the
 *        FakeBasicInvocationHandler
 *     6) write firstResponseByte to stream returned from
 *        FakeOutboundRequest.getResponseInputStream 
 *     7) invoke FakeOutboundRequest.setDeliveryStatusReturn
 *        passing in deliveryStatus
 *     8) invoke FakeBasicInvocationHandler.setCreateMarshalInputStreamException
 *        passing in createMarshalInputStreamException
 *     9) invoke the method on the dynamic proxy
 *    10) assert createMarshalInputStreamException is thrown directly or
 *        appropriately wrapped
 * </pre>
 */
public class Invoke_CreateMarshalInputStreamExceptionTest 
    extends AbstractInvokeTest 
{
    // test cases
    Throwable[] cases = {
        new IOException(),
        new java.net.UnknownHostException(),    //IOException subclass
        new java.net.ConnectException(),        //IOException subclass
        new RemoteException(),                  //IOException subclass
        new java.rmi.UnknownHostException(""),  //RemoteException subclass
        new java.rmi.ConnectException(""),      //RemoteException subclass
        new MarshalException(""),               //RemoteException subclass
        new UnmarshalException(""),             //RemoteException subclass
        new ConnectIOException(""),             //RemoteException subclass
        new SecurityException(),                //RuntimeException subclass
        new ArrayIndexOutOfBoundsException(),   //RuntimeException subclass
        new UndeclaredThrowableException(null), //RuntimeException subclass
        new NullPointerException(),             //RuntimeException subclass
        new LinkageError(),                     //Error subclass
        new AssertionError()                    //Error subclass
    };

    boolean[] deliveryStatusCases = { true, false };

    int[] firstResponseByteCases = { 0x01, 0x02 };

    // inherit javadoc
    public void run() throws Exception {
        //loop over deliveryStatusCases, firstResponseByteCases and
        //exception test cases
        for (int g = 0; g < deliveryStatusCases.length; g++) {
            boolean deliveryStatus = deliveryStatusCases[g];

            for (int h = 0; h < firstResponseByteCases.length; h++) {
                int firstResponseByte = firstResponseByteCases[h];

                for (int i = 0; i < cases.length; i++) {
                    logger.log(Level.FINE,
                        "=================================");
                    Throwable createMarshalInputStreamException = cases[i];
                    logger.log(Level.FINE,"test case " + (counter++) + ": " 
                        + "deliveryStatus:" + deliveryStatus
                        + ",firstResponseByte:" + firstResponseByte
                        + ",exception:" +createMarshalInputStreamException);
                    logger.log(Level.FINE,"");

                    iterator.init();
                    request.setDeliveryStatusReturn(deliveryStatus);
                    request.setResponseInputStream(firstResponseByte,null);
                    handler.setCreateMarshalInputStreamException(
                        createMarshalInputStreamException);

                    try {
                        impl.fakeMethod();
                        assertion(false);
                    } catch (Throwable t) {
                        check(true,deliveryStatus,false,true,
                            createMarshalInputStreamException,t);
                    }

                } //end inner loop
            } //end middle loop
        } //end outter loop
    }

}

