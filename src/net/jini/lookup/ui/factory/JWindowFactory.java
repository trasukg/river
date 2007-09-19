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
package net.jini.lookup.ui.factory;

import java.awt.Frame;
import java.awt.Window;
import javax.swing.JWindow;

/**
 * UI factory for a Swing <CODE>JWindow</CODE>.
 *
 * <P>If the UI generated by the method declared in this interface implements
 * <CODE>javax.accessibility.Accessible</CODE> and supports the Java Accessibility
 * API, an <CODE>AccessibleUI</CODE> attribute
 * should be placed in the <CODE>UIDescriptor</CODE>'s <CODE>attributes</CODE> set.
 *
 * @author Bill Venners
 */
public interface JWindowFactory extends java.io.Serializable {

    /**
     * Convenience constant to use in the <CODE>toolkit</CODE>
     * field of <CODE>UIDescriptor</CODE>s that contain a
     * <CODE>JWindowFactory</CODE>.
     * The value of this constant is <CODE>"javax.swing"</CODE>.
     */
    String TOOLKIT = "javax.swing";

    /**
     * Convenience constant to use in the <CODE>UIFactoryTypes</CODE>
     * set in the <CODE>attributes</CODE> set of <CODE>UIDescriptor</CODE>s
     * that contain a <CODE>JWindowFactory</CODE>.
     * The value of this constant is <CODE>"net.jini.lookup.ui.factory.JWindowFactory"</CODE>.
     */
    String TYPE_NAME = "net.jini.lookup.ui.factory.JWindowFactory";

    /**
     * Returns a <CODE>JWindow</CODE> with no specified
     * owner.
     *
     * @param roleObject an object defined by the semantics of the UI role interface
     * implemented by the returned UI object. (UI role is indicated in the
     * <code>role</code> field of <code>UIDescriptor</code>s.)
     *
     * @return a <code>JWindow</code> UI
     */
    JWindow getJWindow(Object roleObject);

    /**
     * Returns a <CODE>JWindow</CODE> with specified
     * owner <CODE>Window</CODE>.
     *
     * @param roleObject an object defined by the semantics of the UI role interface
     * implemented by the returned UI object. (UI role is indicated in the
     * <code>role</code> field of <code>UIDescriptor</code>s.)
     *
     * @param owner the <code>Window</code> to act as owner of the returned
     * <code>JWindow</code>
     *
     * @return a <code>JWindow</code> UI
     */
    JWindow getJWindow(Object roleObject, Window owner);

    /**
     * Returns a <CODE>JWindow</CODE> with specified
     * owner <CODE>Frame</CODE>.
     *
     * @param roleObject an object defined by the semantics of the UI role interface
     * implemented by the returned UI object. (UI role is indicated in the
     * <code>role</code> field of <code>UIDescriptor</code>s.)
     *
     * @param owner the <code>Frame</code> to act as owner of the returned
     * <code>JWindow</code>
     *
     * @return a <code>JWindow</code> UI
     */
    JWindow getJWindow(Object roleObject, Frame owner);
}
