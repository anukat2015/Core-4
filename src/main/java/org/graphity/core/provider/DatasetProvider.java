/*
 * Copyright 2015 Martynas Jusevičius <martynas@graphity.org>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.graphity.core.provider;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.DatasetFactory;
import com.hp.hpl.jena.shared.NoReaderForLangException;
import com.hp.hpl.jena.shared.NoWriterForLangException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFLanguages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Martynas Jusevičius <martynas@graphity.org>
 */
@Provider
public class DatasetProvider implements MessageBodyReader<Dataset>, MessageBodyWriter<Dataset>
{
 
    private static final Logger log = LoggerFactory.getLogger(DatasetProvider.class);

    public boolean isQuadsMediaType(MediaType mediaType)
    {
        MediaType formatType = new MediaType(mediaType.getType(), mediaType.getSubtype()); // discard charset param
        Lang lang = RDFLanguages.contentTypeToLang(formatType.toString());
        return lang != null && RDFLanguages.isQuads(lang);
    }
    
    // READER
    
    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
    {
        return type == Dataset.class && isQuadsMediaType(mediaType);
    }

    @Override
    public Dataset readFrom(Class<Dataset> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException
    {
	if (log.isTraceEnabled()) log.trace("Reading Dataset with HTTP headers: {} MediaType: {}", httpHeaders, mediaType);
	
	Dataset dataset = DatasetFactory.createMem();

        MediaType formatType = new MediaType(mediaType.getType(), mediaType.getSubtype()); // discard charset param        
        Lang lang = RDFLanguages.contentTypeToLang(formatType.toString());
        if (lang == null)
        {
            Throwable ex = new NoReaderForLangException("Media type not supported");
            if (log.isErrorEnabled()) log.error("MediaType {} not supported by Jena", mediaType);
            throw new WebApplicationException(ex, Response.Status.INTERNAL_SERVER_ERROR);
        }
	String syntax = lang.getName();
	if (log.isDebugEnabled()) log.debug("Syntax used to read Dataset: {}", syntax);

	// extract base URI from httpHeaders? extract charset from MediaType
        //mediaType.getParameters().containsKey("charset")
	//return dataset.read(entityStream, null, syntax);
        RDFDataMgr.read(dataset, entityStream, lang);
        return dataset;
    }
    
    // WRITER
    
    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
    {
        return Dataset.class.isAssignableFrom(type) && isQuadsMediaType(mediaType);
    }

    @Override
    public long getSize(Dataset model, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
    {
	return -1;
    }

    @Override
    public void writeTo(Dataset dataset, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException
    {
	if (log.isTraceEnabled()) log.trace("Writing Dataset with HTTP headers: {} MediaType: {}", httpHeaders, mediaType);

        MediaType formatType = new MediaType(mediaType.getType(), mediaType.getSubtype()); // discard charset param        
        Lang lang = RDFLanguages.contentTypeToLang(formatType.toString());
        if (lang == null)
        {
            Throwable ex = new NoWriterForLangException("Media type not supported");
            if (log.isErrorEnabled()) log.error("MediaType {} not supported by Jena", formatType);
            throw new WebApplicationException(ex, Response.Status.INTERNAL_SERVER_ERROR);
        }
	String syntax = lang.getName();
	if (log.isDebugEnabled()) log.debug("Syntax used to write Dataset: {}", syntax);

	//dataset.write(entityStream, syntax);
        RDFDataMgr.write(entityStream, dataset, lang);
    }

}
