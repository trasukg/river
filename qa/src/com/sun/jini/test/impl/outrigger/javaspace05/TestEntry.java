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
package com.sun.jini.test.impl.outrigger.javaspace05;

import net.jini.id.ReferentUuid;
import net.jini.id.Uuid;

/**
 * All of the entries used by the tests in this package implement
 * the TestEntry interface to allow the tests to use multiple
 * entry class hierarchies without duplicating code to handle
 * each.
 */
public interface TestEntry extends ReferentUuid, Cloneable {
    public void setUuid(Uuid id);
    public String getColor();
    public void setColor(String color);
    public TestEntry dup();
}
