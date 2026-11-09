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
package com.baz.divisas.scim.rest;

import com.baz.divisas.general.util.LogApp;
import com.baz.divisas.scim.schema.Schema;
import com.baz.divisas.scim.schema.SchemaReader;
import com.baz.divisas.scim.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Schema controller for the SCIM server implementation
 *
 * @author wouter
 */
@RestController
public class SchemaController extends Controller {

	@Autowired
	private SchemaReader schemaReader;
	

	public SchemaController() {
		super();
	}

	/**
	 * POST of a entity
	 *
	 * @param resourceType       the resourcetype for which to POST
	 * @param entity             the entity to post
	 * @param attributes         the attributes to be included from the response
	 * @param excludedAttributes the attributes to be excluded from the response
	 * @param request            the HttpServletRequest
	 * @param response           the HttpServletResponse
	 * @return the map as a ResponseEntity
	 */
	@PostMapping(path = "/scim/v2/{resourceType}s", produces = { "application/scim+json", "application/json" })
	public ResponseEntity<Map<String, Object>> post(@PathVariable String resourceType,
			@RequestBody Map<String, Object> entity,
			@RequestParam(required = false, name = "attributes") String attributes,
			@RequestParam(required = false, name = "excludedAttributes") String excludedAttributes,
			HttpServletRequest request, HttpServletResponse response) {
		
		ResponseEntity<Map<String, Object>> resp;
		Schema schema = schemaReader.getSchemaByResourceType(resourceType);
		
		if (schema != null) {
			List<String> schemas = extractSchemas(entity);
			
			if (schemas != null && !schemas.isEmpty()) {
				
				if (schemas.contains(schema.getId())) {
					resp = post(entity, request, response, schema, attributes, excludedAttributes);
				}
				else {
					resp = invalidSchemaForResource(schemas, resourceType);
				}
			} 
			else {
				resp = post(entity, request, response, schema, attributes, excludedAttributes);
			}
		}
		else {
			LogApp.mensaje(resourceType + " " + HttpStatus.NOT_FOUND.getReasonPhrase());
			resp = showError(HttpStatus.NOT_FOUND.value(), "resource type " + HttpStatus.NOT_FOUND.getReasonPhrase(), null);
		}
		
		return resp;
	}

	/**
	 * PUT of a entity
	 *
	 * @param resourceType       the resourcetype of the entity to PUT
	 * @param id                 the id of the entity of the given resourcetype
	 * @param entity             the entity itself as a map
	 * @param attributes         the attributes to be included from the response
	 * @param excludedAttributes the attributes to be excluded from the response
	 * @param request            the HttpServletRequest
	 * @param response           the HttpServletResponse
	 * @return the map as a ResponseEntity
	 */
	@PutMapping(path = "/scim/v2/{resourceType}s/{id}", produces = { "application/scim+json", "application/json" })
	public ResponseEntity<Map<String, Object>> put(@PathVariable String resourceType, @PathVariable String id,
			@RequestBody Map<String, Object> entity,
			@RequestParam(required = false, name = "attributes") String attributes,
			@RequestParam(required = false, name = "excludedAttributes") String excludedAttributes,
			HttpServletRequest request, HttpServletResponse response) {
		
		ResponseEntity<Map<String, Object>> resp;
		Schema schema = schemaReader.getSchemaByResourceType(resourceType);
		
		if (schema != null) {
			List<String> schemas = extractSchemas(entity);
			if (schemas != null && !schemas.isEmpty()) {
				
				if (schemas.contains(schema.getId())) {
					resp = put(id, entity, request, response, schema, attributes, excludedAttributes);
				}
				else {
					resp = invalidSchemaForResource(schemas, resourceType);
				}
			} 
			else {
				resp = put(id, entity, request, response, schema, attributes, excludedAttributes);
			}
		}
		else {
			LogApp.mensaje(resourceType + " " + HttpStatus.NOT_FOUND.getReasonPhrase());
			resp = showError(HttpStatus.NOT_FOUND.value(), "resource type " + HttpStatus.NOT_FOUND.getReasonPhrase(), null);
		}
		
		return resp;
	}

	/**
	 * PATCH a entity
	 *
	 * @param resourceType       the resourcetype of the entity to PATCH
	 * @param id                 the id of the entity
	 * @param entity             the entity
	 * @param attributes         the attributes to be included from the response
	 * @param excludedAttributes the attributes to be excluded from the response
	 * @param request            the HttpServletRequest
	 * @param response           the HttpServletResponse
	 * @return the map as a ResponseEntity
	 */
	@PatchMapping(path = "/scim/v2/{resourceType}s/{id}", produces = { "application/scim+json", "application/json" })
	public ResponseEntity<Map<String, Object>> patch(@PathVariable String resourceType, @PathVariable String id,
			@RequestBody Map<String, Object> entity,
			@RequestParam(required = false, name = "attributes") String attributes,
			@RequestParam(required = false, name = "excludedAttributes") String excludedAttributes,
			HttpServletRequest request, HttpServletResponse response) {
		Schema schema = schemaReader.getSchemaByResourceType(resourceType);
		if (schema != null) {
			List<String> schemas = extractSchemas(entity);
			if (schemas.contains(Constants.SCHEMA_PATCHOP)) {
				return patch(id, entity, request, response, schema, attributes, excludedAttributes);
			}
			return invalidSchemaForResource(resourceType, Constants.SCHEMA_PATCHOP);
		}
		LogApp.mensaje(resourceType + " " + HttpStatus.NOT_FOUND.getReasonPhrase());
		return showError(HttpStatus.NOT_FOUND.value(), "resource type " + HttpStatus.NOT_FOUND.getReasonPhrase(), null);
	}

	/**
	 * GETs the entity
	 *
	 * @param resourceType       the resourceType of the entity
	 * @param id                 the id of the entity
	 * @param attributes         the attributes to be included from the response
	 * @param excludedAttributes the attributes to be excluded from the response
	 * @param request            the HttpServletRequest
	 * @param response           the HttpServletResponse
	 * @return the map containing the entity
	 */
	@GetMapping(path = "/scim/v2/{resourceType}s/{id}", produces = { "application/scim+json", "application/json" })
	public ResponseEntity<Map<String, Object>> get(@PathVariable String resourceType, @PathVariable String id,
			@RequestParam(required = false, name = "attributes") String attributes,
			@RequestParam(required = false, name = "excludedAttributes") String excludedAttributes,
			HttpServletRequest request, HttpServletResponse response) {
		Schema schema = schemaReader.getSchemaByResourceType(resourceType);
		if (schema != null) {
			return get(id, request, response, schema, attributes, excludedAttributes);
		}
		LogApp.mensaje(resourceType + " " + HttpStatus.NOT_FOUND.getReasonPhrase());
		return showError(HttpStatus.NOT_FOUND.value(), "resource type " + HttpStatus.NOT_FOUND.getReasonPhrase(), null);
	}

	/**
	 * Searches the entity
	 *
	 * @param resourceType       the resource type
	 * @param startIndex         the index to start from
	 * @param count              the number of records to return
	 * @param filter             the filter to apply
	 * @param sortBy             the sortby attribute
	 * @param sortOrder          the sortorder
	 * @param attributes         the attributes to be included from the response
	 * @param excludedAttributes the attributes to be excluded from the response
	 * @param request            the HttpServletRequest
	 * @param response           the HttpServletResponse
	 * @return the response entity
	 */
	@GetMapping(path = "/scim/v2/{resourceType}s", produces = { "application/scim+json", "application/json" })
	public ResponseEntity<Map<String, Object>> search(@PathVariable String resourceType,
			@RequestParam(required = false, name = "startIndex", defaultValue = "1") Integer startIndex,
			@RequestParam(required = false, name = "count", defaultValue = "200") Integer count,
			@RequestParam(required = false, name = "filter") String filter,
			@RequestParam(required = false, name = "sortBy") String sortBy,
			@RequestParam(required = false, name = "sortOrder") String sortOrder,
			@RequestParam(required = false, name = "attributes") String attributes,
			@RequestParam(required = false, name = "excludedAttributes") String excludedAttributes,
			HttpServletRequest request, HttpServletResponse response) {
		Schema schema = schemaReader.getSchemaByResourceType(resourceType);
		if (schema != null) {
			return search(startIndex, count, schema, filter, sortBy, sortOrder, attributes, excludedAttributes);
		}
		LogApp.mensaje(resourceType + " " + HttpStatus.NOT_FOUND.getReasonPhrase());
		return showError(HttpStatus.NOT_FOUND.value(), "resource type " + HttpStatus.NOT_FOUND.getReasonPhrase(), null);
	}

	/**
	 * DELETEs the entity
	 *
	 * @param resourceType the resourcetype of the entity to be deleted
	 * @param id           the id of the entity to be deleted
	 * @return the entity deleted
	 */
	@DeleteMapping(path = "/scim/v2/{resourceType}s/{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable String resourceType, @PathVariable String id) {
		Schema schema = schemaReader.getSchemaByResourceType(resourceType);
		if (schema != null) {
			return delete(id, schema);
		}
		LogApp.mensaje("the resource of type " + resourceType + " is not found");
		return showError(HttpStatus.NOT_FOUND.value(), "the resource of the given type is not found", null);
	}
}
