#
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership. The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License. You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# Test creates a lease a number of leases, places them all in the
# renewal set and then forces a number of failure and makes sure that
# the appropriate events get generated, and that the leases are removed
# from the set.

testClass=RenewalFailureTest
testCategories=renewalservice,renewalservice_impl

com.sun.jini.test.impl.norm.renew_grant=30000
com.sun.jini.test.impl.norm.renew_count=2
com.sun.jini.test.impl.norm.tryShutdown=true
include0=norm.properties
