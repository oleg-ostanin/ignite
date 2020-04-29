/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ignite.internal.processors.security.impl;

import java.util.Collection;
import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.internal.GridKernalContext;
import org.apache.ignite.internal.processors.security.SecurityContext;
import org.apache.ignite.internal.processors.security.client.AdditionalAttributeSecurityCheckTest;
import org.apache.ignite.plugin.security.AuthenticationContext;

import static org.apache.ignite.internal.processors.security.impl.TestAdditionalSecurityPluginProvider.ADDITIONAL_SECURITY_CLIENT_VERSION_ATTR;

/**
 * Security processor for test AuthenticationContext with user attributes.
 */
public class TestAdditionalAttributeSecurityProcessor extends TestAdditionalSecurityProcessor {
    /** Attribute handler. */
    private AdditionalAttributeSecurityCheckTest.AttributeHandler hndlr;

    /**
     * Constructor.
     */
    public TestAdditionalAttributeSecurityProcessor(GridKernalContext ctx, TestSecurityData nodeSecData,
        Collection<TestSecurityData> predefinedAuthData, boolean globalAuth, boolean checkSslCerts,
        AdditionalAttributeSecurityCheckTest.AttributeHandler hndlr) {
        super(ctx, nodeSecData, predefinedAuthData, globalAuth, checkSslCerts);

        this.hndlr = hndlr;
    }

    /** {@inheritDoc} */
    @Override public SecurityContext authenticate(AuthenticationContext authCtx) throws IgniteCheckedException {
        if(authCtx.nodeAttributes() != null)
            hndlr.handle(authCtx.nodeAttributes().get(ADDITIONAL_SECURITY_CLIENT_VERSION_ATTR).toString());

        return super.authenticate(authCtx);
    }
}
