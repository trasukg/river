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
package com.sun.jini.test.spec.lookupservice.service;
import java.io.Serializable;
import com.sun.jini.test.spec.lookupservice.service.Interface00;
public class Service02 implements Serializable,
                                  Interface00
{
    public int i;
    public Service02(int i) {
        this.i = i;
    }

    public boolean equals(Object obj) {
        try {
            if ( this == obj ) {
                return true;
            } else if (    ((obj.getClass()).equals(Service02.class))
                        && (((Service02)obj).i == i) ) {
                return true;
            } else {
                return false;
            }
        } catch (NullPointerException e) {
            return false;
	}
    }
}
