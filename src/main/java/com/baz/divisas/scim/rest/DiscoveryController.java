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

import com.baz.divisas.scim.schema.SchemaReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Discovery controller for the SCIM server
 *
 * @author wouter
 */
@RestController
public class DiscoveryController extends Controller {

	private static final Logger LOGGER = LogManager.getLogger(DiscoveryController.class);

	private Map<String, Object> serviceProviderConfig;
	private List<Object> resourceTypes;
	private List<Object> schemas;

	@Autowired
	private SchemaReader schemaReader;

	public DiscoveryController() {
		super();
	}

	@GetMapping(path = "/scim/v2/ServiceProviderConfig", produces = { "application/scim+json", "application/json" })
	public ResponseEntity<Map<String, Object>> getServiceProviderConfig(HttpServletRequest request, 
			HttpServletResponse response) {

		long start = System.currentTimeMillis();
		ResponseEntity<Map<String, Object>> result = null;
		try {
			if (serviceProviderConfig == null) {
				serviceProviderConfig = schemaReader.getServiceProviderConfig();
			}
			result = new ResponseEntity<>(serviceProviderConfig, HttpStatus.OK);
		}
		catch (IOException e) {
			LOGGER.error("can not read service provider config", e);
			Map<String, Object> error = new HashMap<>();
			error.put("error", e.getMessage());
			result = new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		LOGGER.info("ServiceProviderConfig fetched in {} ms", (System.currentTimeMillis() - start));

		return result;
	}

	@GetMapping(path = "/scim/v2/ResourceTypes", produces = { "application/scim+json", "application/json" })
	public ResponseEntity<List<Object>> getResourceTypes(HttpServletRequest request, HttpServletResponse response) {

		long start = System.currentTimeMillis();
		ResponseEntity<List<Object>> result = null;
		try {
			if (resourceTypes == null) {
				resourceTypes = schemaReader.getResourceTypes();
			}
			result = new ResponseEntity<>(resourceTypes, HttpStatus.OK);
		}
		catch (IOException e) {
			LOGGER.error("can not read resource types", e);
			List<Object> error = new ArrayList<>();
			error.add(e.getMessage());
			result = new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		LOGGER.info("resource types fetched in {} ms", (System.currentTimeMillis() - start));

		return result;
	}

	@GetMapping(path = "/scim/v2/Schemas", produces = { "application/scim+json", "application/json" })
	public ResponseEntity<List<Object>> getSchemas(HttpServletRequest request, HttpServletResponse response) {

		long start = System.currentTimeMillis();
		ResponseEntity<List<Object>> result = null;
		try {
			if (schemas == null) {
				schemas = schemaReader.getSchemas();
			}
			result = new ResponseEntity<>(schemas, HttpStatus.OK);
		} 
		catch (IOException e) {
			LOGGER.error("can not read schemas", e);
			List<Object> error = new ArrayList<>();
			error.add(e.getMessage());
			result = new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		LOGGER.info("schemas fetched in {} ms", (System.currentTimeMillis() - start));

		return result;
	}
}
