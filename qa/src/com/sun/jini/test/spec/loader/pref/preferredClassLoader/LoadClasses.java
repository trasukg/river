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
package com.sun.jini.test.spec.loader.pref.preferredClassLoader;

import java.util.logging.Level;

// com.sun.jini.qa.harness
import com.sun.jini.qa.harness.TestException;
import com.sun.jini.qa.harness.QAConfig;

// com.sun.jini.qa
import com.sun.jini.qa.harness.QATest;
import com.sun.jini.qa.harness.QAConfig;

// java.io
import java.io.IOException;

// java.net
import java.net.URL;

// java.util.logging
import java.util.logging.Logger;
import java.util.logging.Level;

// davis packages
import net.jini.loader.pref.PreferredClassLoader;

// instrumented preferred class loader
import com.sun.jini.test.spec.loader.util.Item;
import com.sun.jini.test.spec.loader.util.Util;
import com.sun.jini.test.spec.loader.util.QATestPreferredClassLoader;

// test base class
import com.sun.jini.test.spec.loader.pref.AbstractTestBase;


/**
 * <b>Purpose</b><br><br>
 *
 * This test verifies the behavior of the<br>
 * <code>protected Class loadClass(String name, boolean resolve)</code>
 * method of the<br>
 * <code>net.jini.loader.pref.PreferredClassLoader</code> class:
 *
 * <br><blockquote>
 *  If the name parameter is not preferred, the search is the same as with
 *  <code>ClassLoader.loadClass()</code>. If the name is preferred, then this
 *  method will call <code>findClass()</code> to load the class and will not
 *  delegate to the parent class loader.
 * </blockquote>
 *  <ul><lh>Parameters:</lh>
 *    <li>name - the name of the class</li>
 *    <li>resolve - if true then resolve the class</li>
 *  </ul>
 *
 * <b>Test Description</b><br><br>
 *
 *  This test iterates over a set of various parameters passing to
 *  {@link QATestPreferredClassLoader} constructors.
 *  All parameters are passing to the {@link #testCase} method.
 *  <ul><lh>Possible parameters are:</lh>
 *  <li>URL[] urls: http or file based url to qa1-loader-pref.jar file</li>
 *  <li>ClassLoader parent: ClassLoader.getSystemClassLoader()</li>
 *  <li>String exportAnnotation: <code>null</code>,
 *                               "Any export annotation string"</li>
 *  <li>boolean requireDlPerm: <code>true</code>, <code>false</code></li>
 *  </ul>
 *
 *  Each {@link #testCase} iterates over a set of preferred/non-preferred
 *  classes.
 *  There are two sets of classes with the same names there. The first set of
 *  classes can be found in the executing VM's classpath. The second set of
 *  classes are placed in the qa1-loader-pref.jar file and can be downloaded
 *  using http or file based url.
 *  <br><br>
 *  Class {@link Util} has a statically defined lists of all resources
 *  placed in the qa1-loader-pref.jar file. {@link Util#listClasses},
 *  {@link Util#listResources}, {@link Util#listLocalClasses},
 *  {@link Util#listLocalResources} define names and preferred status of
 *  these resources.
 *  <br><br>
 *  For each preferred/non-preferred class the testCase will try to execute
 *  <code>Class.forName</code> passing {@link QATestPreferredClassLoader}
 *  object and will try to execute <code>Class.forName</code> passing the
 *  <code>ClassLoader.getSystemClassLoader()</code>.
 *  <br><br>
 *  Then testCase will verify class identity using <code>equals</code> method.
 *  Loaded classes should be equal for non-preferred classes and should be
 *  not equal for preferred classes.
 *  <br><br>
 *
 * <b>Infrastructure</b><br><br>
 *
 * <ol><lh>This test requires the following infrastructure:</lh>
 *  <li> {@link QATestPreferredClassLoader} is an instrumented
 *       PreferredClassLoader using for davis.loader's and davis.loader.pref's
 *       testing.</li>
 *  <li> <code>META-INF/PREFERRED.LIST</code> with a set of
 *       preferred/non-preferred resources. <code>META-INF/PREFERRED.LIST</code>
 *       should be placed in the qa1-loader-pref.jar file.</li>
 *  <li> A first set of resources should be placed in the qa1.jar file, so these
 *       resource can be found in the executing VM's classpath.</li>
 *  <li> A second set of resources should be placed in the qa1-loader-pref.jar,
 *       so these resources can be downloaded.</li>
 * </ol>
 *
 * <br>
 *
 * <b>Actions</b><br><br>
 * <ol>
 *    <li> construct a {@link QATestPreferredClassLoader} with a single URL to
 *         the qa1-loader-pref.jar file and appropriate parameters.
 *    </li>
 *    <li> for each preferred/non-preferred class do the following:
 *     <ul>
 *       <li> Call {@link QATestPreferredClassLoader#clearAllFlags}
 *            method.</li>
 *       <li> invoke Class.forName method passing
 *            {@link QATestPreferredClassLoader},
 *            invoke Class.forName method passing system class loader and
 *            verify that returned classes are equal for non-preferred classes
 *            and are not equal for preferred classes.</li>
 *       <li> verify that {@link QATestPreferredClassLoader#loadClass}
 *            is called for all classes and
 *            {@link QATestPreferredClassLoader#findClass} is called
 *            for preferred classes only.</li>
 *     </ul>
 *    </li>
 *    <li> for classes that can be found in the parent class loader (such as in
 *         the class path) but cannot be found in the preferred class loader
 *         do the following:
 *     <ul>
 *       <li> Call {@link QATestPreferredClassLoader#clearAllFlags} method.</li>
 *       <li> invoke Class.forName method passing
 *            {@link QATestPreferredClassLoader},
 *            invoke Class.forName method passing system class loader and
 *            verify that returned classes are equal for all classes.</li>
 *       <li> verify that {@link QATestPreferredClassLoader#loadClass} is
 *            called for all classes and
 *            {@link QATestPreferredClassLoader#findClass} is not called.</li>
 *     </ul>
 *    </li>
 * </ol>
 *
 */
public class LoadClasses extends AbstractTestBase {

    /** String to format message string */
    static final String str1 = "preferred class";

    /** String to format message string */
    static final String str2 = "non-preferred class";

    /** String that indicates fail status */
    String message = "";

    /** System class loader */
    ClassLoader parent = Util.systemClassLoader();

    /**
     * Run the test according <b>Test Description</b>
     */
    public void run() throws Exception {
        String annotation = super.annotation;
        testCase(true, null);
        testCase(true, annotation);
        testCase(false, null);
        testCase(false, annotation);

        if (message.length() > 0) {
            throw new TestException(message);
        }
    }

    /**
     * Reset setup parameters by passing parameters and create
     * {@link QATestPreferredClassLoader}.
     * <br><br>
     * Then run the test case according <b>Test Description</b>
     *
     * @param isHttp flag to define whether http or file url will be used
     *        for download preferred classes and resources
     * @param annotation the exportAnnotation string
     *
     * @throws TestException if could not create instrumented preferred class
     *         loader
     */
    public void testCase(boolean isHttp, String annotation)
            throws TestException {

        /*
         * Reset setup parameters by passing parameters.
         */
        super.isHttp = isHttp;
        super.annotation = annotation;

        /*
         * 1) construct a QATestPreferredClassLoader according setup parameters
         *    with a single URL to the "qa1-loader-pref.jar file.
         */
        createLoader(Util.PREFERREDJarFile);

        /*
         * 2) for each preferred/non-preferred class do the following:
         * a) Call QATestPreferredClassLoader.clearAllFlags() method.
         * b) invoke Class.forName method passing
         *    QATestPreferredClassLoader, invoke Class.forName method
         *    passing system class loader and assert that returned
         *    classes are equals for non-preferred classes and are not
         *    equals for preferred classes.
         * c) assert that QATestPreferredClassLoader.loadClass() is called
         *    for all classes and QATestPreferredClassLoader.findClass()
         *    is called for preferred classes only.
         */
        for (int item = 0; item < Util.listClasses.length; item++) {
            loader.clearAllFlags();
            String name = Util.listClasses[item].name;
            Class classDefault = null;
            Class classPreferred = null;

            try {
                classDefault = Class.forName(name, false, parent);
                classPreferred = Class.forName(name, false, loader);
            } catch (ClassNotFoundException e) {
                message += "\nClass not found: " + name;
                break;
            } catch (SecurityException se) {
                // Do not expect SecurityException.
                // Tests with expected SecurityException
                // are GetPermissionsHttpSecurityException and
                // GetPermissionsFileSecurityException in the
                // GetPermissions test case.
                message += "\nClass.forName("
                         + name + ", false, loader)\n"
                         + "  throws: " + se.toString() + "\n"
                         + "  expected: returned class";

                // Fast fail approach
                throw new TestException(message);
            }
            boolean expected = !Util.listClasses[item].pref;
            boolean returned = classDefault.equals(classPreferred);

            if (expected != returned) {
                message += "\nClass.forName("
                         + name + ", false, PreferredClassLoader)\n"
                         + "  returned:" + (expected ? str1 : str2) + "\n"
                         + "  expected:" + (expected ? str2 : str1);

                // Fast fail approach
                throw new TestException(message);
            } else {
                String msg = "Class.forName(" + name
                           + ", false, PreferredClassLoader)"
                           + "  returned " + (expected ? str2 : str1)
                           + "  as expected";
                logger.log(Level.FINE, msg);
            }

            if (!loader.loadClassIsInvoked) {
                message += "\nClass.forName("
                         + name + ", false, PreferredClassLoader) "
                         + "does not invoke PreferredClassLoader.loadClass()";

                // Fast fail approach
                throw new TestException(message);
            }

            if (!expected && !loader.findClassIsInvoked) {
                message += "\nClass.forName("
                         + name + ", false, PreferredClassLoader) "
                         + "does not invoke PreferredClassLoader.findClass()"
                         + " for preferred class";

                // Fast fail approach
                throw new TestException(message);
            }

            if (expected && loader.findClassIsInvoked) {
                message += "\nClass.forName("
                         + name + ", false, PreferredClassLoader) "
                         + "invoke PreferredClassLoader.findClass()"
                         + " for non-preferred class";

                // Fast fail approach
                throw new TestException(message);
            }
        }

        /*
         *    3) for classes that can be found in the parent class loader
         *       (such as in the class path) but cannot be found in the
         *       preferred class loader do the following:
         *       a) Call QATestPreferredClassLoader.clearAllFlags() method.
         *       b) invoke Class.forName method passing
         *          QATestPreferredClassLoader, invoke Class.forName
         *          method passing system class loader and assert that
         *          returned classes are equals for all classes.
         *       c) assert that QATestPreferredClassLoader.loadClass() is
         *          called for all classes and
         *          QATestPreferredClassLoader.findClass() is not called.
         */
        for (int item = 0; item < Util.listLocalClasses.length; item++) {
            loader.clearAllFlags();
            String name = Util.listLocalClasses[item].name;
            Class classDefault = null;
            Class classPreferred = null;

            try {
                classDefault = Class.forName(name, false, parent);
                classPreferred = Class.forName(name, false, loader);
            } catch (ClassNotFoundException e) {
                message += "\nClass not found: " + name;
                break;
            }
            boolean expected = true;
            boolean returned = classDefault.equals(classPreferred);

            if (expected != returned) {
                message += "\nClass.forName("
                         + name + ", false, PreferredClassLoader)\n"
                         + "  returned:" + (expected ? str1 : str2) + "\n"
                         + "  expected:" + (expected ? str2 : str1);

                // Fast fail approach
                throw new TestException(message);
            } else {
                String msg = "Class.forName(" + name
                           + ", false, PreferredClassLoader)"
                           + "  returned " + (expected ? str2 : str1)
                           + "  as expected";
                logger.log(Level.FINE, msg);
            }

            if (!loader.loadClassIsInvoked) {
                message += "\nClass.forName("
                         + name + ", false, PreferredClassLoader) "
                         + "does not invoke PreferredClassLoader.loadClass";

                // Fast fail approach
                throw new TestException(message);
            }

            if (loader.findClassIsInvoked) {
                message += "\nClass.forName("
                         + name + ", false, PreferredClassLoader) "
                         + "invoke PreferredClassLoader.findClass"
                         + " for non-preferred class";

                // Fast fail approach
                throw new TestException(message);
            }
        }
    }
}
