/**
 *  Copyright 2014 Martynas Jusevičius <martynas@graphity.org>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.graphity.core.model;

import com.hp.hpl.jena.query.DatasetAccessor;
import javax.ws.rs.core.MediaType;
import org.graphity.core.client.GraphStoreClient;

/**
 * A class representing a Graph Store proxy.
 * 
 * @author Martynas Jusevičius <martynas@graphity.org>
 */
public interface GraphStoreProxy extends Proxy, DatasetAccessor
{
    
    GraphStoreClient getClient();
    
    /**
     * Returns an array of readable media types.
     * 
     * @return array of media types
     */
    MediaType[] getReadableMediaTypes();    
    
}
