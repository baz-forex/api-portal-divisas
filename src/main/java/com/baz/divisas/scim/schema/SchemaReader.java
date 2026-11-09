/*
*     Copyright 2019-2022 Wouter Van der Beken @ https://personify.be
*
* Generated software by personify.be

* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*          http://www.apache.org/licenses/LICENSE-2.0
*
 * Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.baz.divisas.scim.schema;

import com.baz.divisas.scim.util.Constants;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SchemaReader {

	private static final Logger LOGGER = LogManager.getLogger(SchemaReader.class);

	private Map<String, Schema> schemaMap = new HashMap<>();

	private Map<String, String> schemaMapper = new HashMap<>();

	@Value("${scim.serviceProviderConfig.location}")
	private String serviceProviderConfigLocation;
	
	@Value("${scim.resourceTypes.location}")
	private String resourceTypesLocation;
	
	@Value("${scim.schema.location}")
	private String schemaLocation;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	public SchemaReader() {
		super();
	}

	@PostConstruct
	public void read() throws IOException {
		LOGGER.info("using schema location {} ", schemaLocation);
		LOGGER.info("resourceloader {}", resourceLoader);

		JsonNode root = Constants.objectMapper.readTree(resourceLoader.getResource(schemaLocation).getInputStream());
		if (root.isArray()) {
			Iterator<JsonNode> iterator = root.elements();
			Schema schema = null;
			while (iterator.hasNext()) {
				schema = Constants.objectMapper.treeToValue(iterator.next(), Schema.class);
				LOGGER.info("loading schema with id [{}]", schema.getId());
				schemaMap.put(schema.getId(), schema);
				schemaMapper.put(schema.getName(), schema.getId());
			}
		} 
		else {
			LOGGER.info("it's no array, no array found in the schema");
		}
	}

	public Map<String, Object> getServiceProviderConfig() throws IOException {
		return Constants.objectMapper.readValue(resourceLoader.getResource(serviceProviderConfigLocation).getInputStream(),
				Map.class);
	}
	
	public List<Object> getResourceTypes() throws IOException {
		return Constants.objectMapper.readValue(resourceLoader.getResource(resourceTypesLocation).getInputStream(),
				List.class);
	}
	
	public List<Object> getSchemas() throws IOException {
		return Constants.objectMapper.readValue(resourceLoader.getResource(schemaLocation).getInputStream(),
				List.class);
	}

	/**
	 * Gets the schema from the cache
	 *
	 * @param id the id of the schema
	 * @return the schema with the given id
	 */
	public Schema getSchema(String id) {
		return schemaMap.get(id);
	}

	public Schema getSchemaByResourceType(String resourceType) {
		return getSchema(schemaMapper.get(resourceType));
	}

	/**
	 * Validates
	 *
	 * @param schema        the schema to validate
	 * @param map           the map to be validated
	 * @param checkRequired boolean indicating if the required attributes have to be
	 *                      checked
	 * @return the same map
	 * @throws SchemaException exception containing the errors
	 */
	public Map<String, Object> validate(Schema schema, Map<String, Object> map, 
			boolean checkRequired, String operation) {
		for (SchemaAttribute attribute : schema.getAttributes()) {
			validateAttribute(map.get(attribute.getName()), attribute, checkRequired, operation);
		}
		return map;
	}
	

	private void validateAttribute(Object o, SchemaAttribute attribute, 
			boolean checkRequired, String operation) {
		try {
			if (o == null) {
				if (attribute.isRequired() && checkRequired) {
					throw new SchemaException("attribute with name [" + attribute.getName() + "] is required");
				}
			} 
			else {

				validateReadOnly(operation, attribute);
				
				SchemaAttributeType type = SchemaAttributeType.fromString(attribute.getType());
				
				if (type == SchemaAttributeType.STRING && !attribute.isMultiValued() ) {
					validateCanonicalValues(o, attribute);
				} 
				else if (type == SchemaAttributeType.COMPLEX) {
					validateComplex(o, operation, attribute, checkRequired);
				} 
				else if (type ==SchemaAttributeType.BOOLEAN) {
					Boolean.valueOf(o.toString());
				}
				else {
					// Do nothing
				}
			}
		}
		catch (RuntimeException e) {
			LOGGER.error("Error al validar atributo", e);
			throw new SchemaException("schema validation for attribute [" + attribute.getName() +
					"] with value [" + o + "] " + e.getMessage());
		}
	}
	
	
	private static void validateReadOnly(String operation, SchemaAttribute attribute) {
		
		if ( ("PUT".equals(operation)||"POST".equals(operation)) && "readOnly".equalsIgnoreCase(attribute.getMutability())) {
			throw new SchemaException("attribute is readonly");
		}
	}
	
	
	private static void validateCanonicalValues(Object o, SchemaAttribute attribute) {
		
		String s = (String) o;
		if (attribute.getCanonicalValues() != null && attribute.getCanonicalValues().length > 0) {
			boolean found = false;
			for (String value : attribute.getCanonicalValues()) {
				if (s.equals(value)) {
					found = true;
					break;
				}
			}
			if (!found) {
				throw new SchemaException("only one of " + Arrays.toString(attribute.getCanonicalValues()) + " is allowed");
			}
		}
	}

	
	private void validateComplex(Object o, String operation, SchemaAttribute attribute, 
			boolean checkRequired) {
		
		
		if (attribute.isMultiValued()) {
			for (Map<String, Object> mm : (List<Map<String, Object>>) o) {
				validateMap(attribute, mm, checkRequired, operation);
			}
		} 
		else {
			validateMap(attribute, (Map<String, Object>) o, checkRequired, operation);
		}
		
	}
	
	
	private void validateMap(SchemaAttribute attribute, Map<String, Object> mm, 
			boolean checkRequired, String operation) {
		for (Entry<String, Object> entry : mm.entrySet()) {
			boolean keyFoundInSchema = false;
			for (SchemaAttribute subAttribute : attribute.getSubAttributes()) {
				if (entry.getKey().equals(subAttribute.getName())) {
					validateAttribute(mm.get(entry.getKey()), subAttribute, checkRequired, operation);
					keyFoundInSchema = true;
				}
			}
			if (!keyFoundInSchema) {
				throw new SchemaException("unsupported attribute name [" + entry.getKey() + "]");
			}
		}
	}
	
}
