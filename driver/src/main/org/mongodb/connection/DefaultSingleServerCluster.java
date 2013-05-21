/*
 * Copyright (c) 2008 - 2013 10gen, Inc. <http://10gen.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mongodb.connection;

import org.mongodb.MongoClientOptions;
import org.mongodb.MongoCredential;
import org.mongodb.MongoException;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

import static org.mongodb.assertions.Assertions.isTrue;
import static org.mongodb.assertions.Assertions.notNull;

class DefaultSingleServerCluster extends DefaultCluster {
    private final Server server;

    public DefaultSingleServerCluster(final ServerAddress serverAddress, final List<MongoCredential> credentialList,
                                      final MongoClientOptions options, final BufferPool<ByteBuffer> bufferPool,
                                      final ServerFactory serverFactory) {
        super(bufferPool, credentialList, options, serverFactory);
        notNull("serverAddress", serverAddress);

        this.server = createServer(serverAddress, new ServerStateListener() {
            @Override
            public void notify(final ServerDescription serverDescription) {
                updateDescription(new ClusterDescription(Arrays.asList(serverDescription), 15));
            }

            @Override
            public void notify(final MongoException e) {
            }
        });
    }

    @Override
    public Server getServer(final ServerAddress serverAddress) {
        isTrue("open", !isClosed());

        return server;
    }

    @Override
    public void close() {
        if (!isClosed()) {
            server.close();
            super.close();
        }
    }
}