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
package org.graphity.core.provider;

import com.hp.hpl.jena.update.UpdateFactory;
import com.hp.hpl.jena.update.UpdateRequest;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import org.graphity.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JAX-RS provider for reading SPARQL Update from request body.
 * Needs to be registered in the application.
 * 
 * @author Martynas Jusevičius <martynas@graphity.org>
 * @see <a href="http://jena.apache.org/documentation/javadoc/arq/com/hp/hpl/jena/update/UpdateRequest.html">Jena UpdateRequest</a>
 * @see <a href="http://jsr311.java.net/nonav/javadoc/javax/ws/rs/ext/MessageBodyReader.html">JAX-RS MessageBodyReader</a>
 */
@Provider
@Consumes(MediaType.APPLICATION_SPARQL_UPDATE)
public class UpdateRequestReader implements MessageBodyReader<UpdateRequest>
{

    private static final Logger log = LoggerFactory.getLogger(UpdateRequestReader.class);

    @Override
    public boolean isReadable(Class<?> type, Type type1, Annotation[] antns, javax.ws.rs.core.MediaType mt)
    {
        return type == UpdateRequest.class;
    }

    @Override
    public UpdateRequest readFrom(Class<UpdateRequest> type, Type type1, Annotation[] antns, javax.ws.rs.core.MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream in) throws IOException, WebApplicationException
    {
	if (log.isTraceEnabled()) log.trace("Reading UpdateRequest with HTTP headers: {} MediaType: {}", httpHeaders, mediaType);
	return UpdateFactory.read(in);
    }
    
}
