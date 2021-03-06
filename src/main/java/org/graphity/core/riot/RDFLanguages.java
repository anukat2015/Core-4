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

package org.graphity.core.riot;

import org.apache.jena.riot.Lang;
import org.apache.jena.riot.LangBuilder;
import org.graphity.core.MediaType;

/**
 *
 * @author Martynas Jusevičius <martynas@graphity.org>
 */
public class RDFLanguages extends org.apache.jena.riot.RDFLanguages
{

    public static final String strLangRDFPOST    = "RDF/POST" ;
    
    public static final Lang RDFPOST = LangBuilder.create(strLangRDFPOST, MediaType.APPLICATION_RDF_URLENCODED).
            addAltContentTypes(MediaType.APPLICATION_FORM_URLENCODED).
            addFileExtensions("rpo").
            build();

    // JSON-LD support is built-in in later Jena versions
    
    public static final String strLangJSONLD    = "JSON-LD" ;
    
    public static final Lang JSONLD = LangBuilder.create(strLangJSONLD, MediaType.APPLICATION_LD_JSON).
            addAltNames("JSONLD").
            addFileExtensions("jsonld").
            build();

}
