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

package org.apache.ignite.internal.processors.security.client;

import java.util.Arrays;
import java.util.Map;
import org.apache.ignite.Ignite;
import org.apache.ignite.cluster.ClusterState;
import org.apache.ignite.internal.client.GridClient;
import org.apache.ignite.internal.client.GridClientClusterState;
import org.apache.ignite.internal.client.GridClientConfiguration;
import org.apache.ignite.internal.client.GridClientFactory;
import org.apache.ignite.internal.processors.security.UserAttributesFactory;
import org.apache.ignite.plugin.security.SecurityCredentials;
import org.apache.ignite.plugin.security.SecurityCredentialsBasicProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.apache.ignite.internal.processors.security.impl.TestAdditionalSecurityProcessor.CLIENT;

/**
 * Reproducer for GridClient authentication fail.
 */
@RunWith(JUnit4.class)
public class AdditionalSecurityCheckGridClientTest extends AdditionalSecurityCheckTest {
    /**
     * @return Grid client configuration.
     */
    @Override protected GridClientConfiguration getGridClientConfiguration() {
        Map<String, String> userAttrs = new UserAttributesFactory().create();

        return new GridClientConfiguration()
            .setSslContextFactory(getClientSslContextFactory()::create)
            .setRouters(Arrays.asList("127.0.0.1:11211", "127.0.0.1:11212"))
            .setSecurityCredentialsProvider(
                new SecurityCredentialsBasicProvider(new SecurityCredentials(CLIENT, "")))
            .setUserAttributes(userAttrs);
    }

    /**
     *
     */
    @Test
    public void testClientInfoGridClientNotFail() throws Exception {
        Ignite ignite = startGrids(2);

        assertEquals(2, ignite.cluster().topologyVersion());

        ignite.cluster().state(ClusterState.ACTIVE);

        try (GridClient client = GridClientFactory.start(getGridClientConfiguration())) {
            assertTrue(client.connected());

            GridClientClusterState state = client.state();

            // Close a coordinator to force the client to send a CLUSTER_CURRENT_STATE message to the other node.
            ignite.close();

            state.state();
        }
    }
}
