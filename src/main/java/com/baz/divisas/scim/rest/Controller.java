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

import be.personify.util.SearchCriteria;
import be.personify.util.SearchCriteriaUtil;
import be.personify.util.SearchCriterium;
import be.personify.util.SearchOperation;
import be.personify.util.StringUtils;
import be.personify.util.exception.InvalidFilterException;
import be.personify.util.scim.PatchOperation;
import be.personify.util.scim.ScimMutability;

import com.baz.divisas.general.util.DateUtils;
import com.baz.divisas.general.util.LogApp;
import com.baz.divisas.scim.schema.Schema;
import com.baz.divisas.scim.schema.SchemaAttribute;
import com.baz.divisas.scim.schema.SchemaException;
import com.baz.divisas.scim.schema.SchemaReader;
import com.baz.divisas.scim.storage.ConfigurationException;
import com.baz.divisas.scim.storage.ConstraintViolationException;
import com.baz.divisas.scim.storage.DataException;
import com.baz.divisas.scim.storage.Storage;
import com.baz.divisas.scim.storage.StorageImplementationFactory;
import com.baz.divisas.scim.util.Constants;
import com.baz.divisas.scim.util.PropertyFactory;
import com.baz.divisas.scim.util.ScimErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Main controller class for the SCIM operations
 *
 * @author vanderw
 */
public class Controller {

	private static final String SORT_ORDER = "SortOrder";
	private static final String SORT_BY = "SortBy";

	private static final String SCHEMA_VALIDATION = "schema validation : ";

	/**
	 * Limite maximo de segmentos permitidos en el path de una operacion PATCH,
	 * para evitar que un path con un numero arbitrario de segmentos provoque
	 * iteraciones no acotadas al resolverlo en PatchUtils.
	 */
	private static final int MAX_PATCH_PATH_SEGMENTS = 10;


	@Value("${scim.allowIdOnCreate:true}")
	private boolean allowIdOnCreate;
	
	@Value("${scim.returnGroupsOnUserGet:true}")
	private boolean returnGroupsOnUserGet;
	
	@Value("${scim.returnGroupsOnUserSearch:true}")
	private boolean returnGroupsOnUserSearch;
	
	@Value("${scim.returnGroupsOnUser.max:100}")
	private int returnGroupsMax;
	
	@Value("${scim.returnGroupsOnUser.includedFields:id,displayName}")
	private String returnGroupsIncludedFields;
	
	@Autowired
	private PropertyFactory propertyFactory;
	
	private Map<String,String> defaultSort = new HashMap<>();
	
	private List<String> returnGroupsIncludedFieldsArray;

	@Autowired
	private StorageImplementationFactory storageImplementationFactory;
	
	private SearchCriteriaUtil searchCriteriaUtil = new SearchCriteriaUtil();

	@Autowired
	private SchemaReader schemaReader;
	
	@Autowired
	private PatchUtils patchUtils;

	public Controller() {
		super();
	}

	//POST
	protected ResponseEntity<Map<String, Object>> post(Map<String, Object> entity, HttpServletRequest request, 
			HttpServletResponse response, Schema schema, String attributes, String excludedAttributes) {
		
		ResponseEntity<Map<String, Object>> resp;
		
		try {
			// validate
			schemaReader.validate(schema, entity, true, request.getMethod());
			
			// prepare
			String id = createId(entity);
			entity.put(Constants.ID, id);
			String location = UriComponentsBuilder.fromHttpRequest(new ServletServerHttpRequest(request))
												  .build().toUriString() + StringUtils.SLASH + id;
			createMeta(id, entity, schema.getName(), location);
			response.addHeader(Constants.HEADER_LOCATION, location);

			// store and return
			storageImplementationFactory.getStorageImplementation(schema).create(id, entity);
			LogApp.mensaje(schema.getName() +" "+ id+ " created");
			resp = new ResponseEntity<>(filterAttributes(schema, entity, 
														 getListFromString(attributes), excludedAttributes), HttpStatus.CREATED);

		}
		catch (SchemaException e) {
			LogApp.mensaje(SCHEMA_VALIDATION, e);
			resp = showError(HttpStatus.BAD_REQUEST.value(), SCHEMA_VALIDATION + e.getMessage(), ScimErrorType.INVALID_SINTAX);
		}
		catch (ConstraintViolationException e) {
			LogApp.mensaje(SCHEMA_VALIDATION, e);
			resp = showError(HttpStatus.CONFLICT.value(), SCHEMA_VALIDATION + e.getMessage(), ScimErrorType.UNIQUENESS);
		}
		catch (DataException | ConfigurationException e) {
			LogApp.mensaje(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e);
			resp = showError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		}
		
		return resp;
	}

	//PUT
	protected ResponseEntity<Map<String, Object>> put(String id, Map<String, Object> entity, HttpServletRequest request, 
			HttpServletResponse response, Schema schema, String attributes, String excludedAttributes) {

		ResponseEntity<Map<String, Object>> resp;
		
		try {
			// validate
			schemaReader.validate(schema, entity, true, request.getMethod());
			if (!entity.get(Constants.ID).equals(id)) {
				LogApp.mensaje("id [" + entity.get(Constants.ID) +
						"] given in the data does not match the one in the url [" + id + "]");
				return showError(HttpStatus.BAD_REQUEST.value(), "id given in the data does not match the one in the url");
			}

			Map<String, Object> existingUser = storageImplementationFactory.getStorageImplementation(schema).get(id);
			if (existingUser != null) {
				entity.put(Constants.KEY_META, existingUser.get(Constants.KEY_META));
			} 
			else {
				LogApp.mensaje(schema.getName() + " " + id + " can not be updated");
				return showError(HttpStatus.NOT_FOUND.value(), schema.getName() + " can not be updated");
			}

			// prepare
			String location = UriComponentsBuilder.fromHttpRequest(new ServletServerHttpRequest(request)).build().toUriString();
			createMeta(id, entity, schema.getName(), location);
			response.addHeader(Constants.HEADER_LOCATION, location);

			// store
			storageImplementationFactory.getStorageImplementation(schema).update(id, entity);

			LogApp.mensaje(schema.getName() +" "+ id+" updated");

			resp = new ResponseEntity<>(filterAttributes(schema, entity, 
										getListFromString(attributes), excludedAttributes), HttpStatus.OK);

		} 
		catch (SchemaException e) {
			LogApp.mensaje(SCHEMA_VALIDATION, e);
			resp = showError(HttpStatus.BAD_REQUEST.value(), SCHEMA_VALIDATION + e.getMessage(), ScimErrorType.INVALID_SINTAX);
		}
		catch (ConstraintViolationException e) {
			LogApp.mensaje(SCHEMA_VALIDATION, e);
			resp = showError(HttpStatus.CONFLICT.value(), SCHEMA_VALIDATION + e.getMessage(), ScimErrorType.UNIQUENESS);
		}
		catch (DataException | ConfigurationException e) {
			LogApp.mensaje(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e);
			resp = showError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		}
		
		return resp;
	}

	//PATCH
	protected ResponseEntity<Map<String, Object>> patch(String id, Map<String, Object> patchRequest, 
			HttpServletRequest request, HttpServletResponse response, Schema schema, String attributes, 
			String excludedAttributes) {

		ResponseEntity<Map<String, Object>> resp;
		
		try {
			// validate
			schemaReader.validate(schema, patchRequest, false, request.getMethod());
			if (!patchRequest.containsKey(Constants.KEY_OPERATIONS)) {
				return showError(HttpStatus.BAD_REQUEST.value(), "no operations present");
			}
	
			Map<String, Object> existingEntity = storageImplementationFactory.getStorageImplementation(schema).get(id);
			
			if (existingEntity == null) {
				LogApp.mensaje(schema.getName() + " " + id + " can not be updated");
				return showError(HttpStatus.NOT_FOUND.value(), schema.getName() + " can not be updated");
			}
			
			patchRequest.put(Constants.KEY_META, existingEntity.get(Constants.KEY_META));

			String location = UriComponentsBuilder.fromHttpRequest(new ServletServerHttpRequest(request)).build().toUriString();

			response.addHeader(Constants.HEADER_LOCATION, location);

			List<Map<String, Object>> operations = (List<Map<String, Object>>) patchRequest.get(Constants.KEY_OPERATIONS);
			
			PatchOperation opType = null;
			String path = null;
			Object value = null;
						
			ResponseEntity<Map<String, Object>> error = null;
			
			for (Map<String, Object> operation : operations) {
				
				opType = PatchOperation.valueOf(((String)operation.get(Constants.KEY_OP)));
				path = (String) operation.get(Constants.KEY_PATH);
				
				error = validateErrorPatch(operation, opType, schema, path);
				
				if(error != null) {
					break;
				}
				
				value = operation.get(Constants.KEY_VALUE);
				patchUtils.patchEntity( existingEntity, opType, path, value, schema);
			}

			if(error == null) {
				createMeta(id, existingEntity, schema.getName(), location);
				storageImplementationFactory.getStorageImplementation(schema).update(id, existingEntity);

				LogApp.mensaje(schema.getName()+" "+id+" patched");

				resp = new ResponseEntity<>(filterAttributes(schema, existingEntity, 
											getListFromString(attributes), excludedAttributes), HttpStatus.OK);
			}
			else {
				resp = error;
			}

		} 
		catch (SchemaException e) {
			LogApp.mensaje(SCHEMA_VALIDATION, e);
			resp = showError(HttpStatus.BAD_REQUEST.value(), SCHEMA_VALIDATION + e.getMessage(), ScimErrorType.INVALID_SINTAX);
		}
		catch (ConstraintViolationException e) {
			LogApp.mensaje(SCHEMA_VALIDATION, e);
			resp = showError(HttpStatus.CONFLICT.value(), SCHEMA_VALIDATION + e.getMessage(), ScimErrorType.UNIQUENESS);
		}
		catch (DataException | ConfigurationException e) {
			LogApp.mensaje(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e);
			resp = showError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		}
		
		return resp;
	}
	
	private ResponseEntity<Map<String, Object>> validateErrorPatch(Map<String, Object> operation, 
			PatchOperation opType, Schema schema, String path){
		
		ResponseEntity<Map<String, Object>> error = null;
		
		if (!operation.containsKey(Constants.KEY_OP) || opType == null ) {
			LogApp.mensaje("Invalid Operation : " + operation);
			error = showError(HttpStatus.NOT_FOUND.value(), "Invalid Operation");
		}
		else if (!isPatchPathSegmentCountValid(path)) {
			LogApp.mensaje("path exceeds the maximum number of allowed segments : " + path);
			error = showError(HttpStatus.BAD_REQUEST.value(), "path exceeds the maximum number of allowed segments",
					ScimErrorType.INVALID_SINTAX);
		}
		else {

			if (!canPerformAction(schema,opType,path)){
				LogApp.mensaje("operation " + opType + " is not allowed for path " + path);
				error = showError(HttpStatus.FORBIDDEN.value(), "operation " + opType + " is not allowed for the given path");
			}
		}
		return error;
	}

	/**
	 * Valida que el path de una operacion PATCH no exceda el numero maximo de
	 * segmentos permitidos, evitando que un path con un numero arbitrario de
	 * segmentos provoque iteraciones no acotadas en PatchUtils.getPath.
	 *
	 * @param path - path de la operacion PATCH
	 * @return true si el path es nulo o su numero de segmentos es valido
	 */
	private boolean isPatchPathSegmentCountValid(String path) {

		if (path == null) {
			return true;
		}

		return patchUtils.getPathSegments(path).size() <= MAX_PATCH_PATH_SEGMENTS;
	}
	
	//GET
	protected ResponseEntity<Map<String, Object>> get(String id, HttpServletRequest request, 
			HttpServletResponse response, Schema schema, String attributes, String excludedAttributes) {

		try {
			Map<String, Object> object = storageImplementationFactory.getStorageImplementation(schema).get(id);

			ResponseEntity<Map<String, Object>> result = null;
			if (object != null) {
				List<String> includeList = getListFromString(attributes);
				object = filterAttributes(schema, object, includeList, excludedAttributes);
				//include groups
				if ( haveToIncludeGroups(schema,excludedAttributes, "GET")) {
					includeGroups(id, object);
				}
				result = new ResponseEntity<>(object, HttpStatus.OK);
				response.addHeader(Constants.HEADER_LOCATION, 
								   UriComponentsBuilder.fromHttpRequest(new ServletServerHttpRequest(request)).build().toUriString());
			}
			else {
				LogApp.mensaje("the resource with id " + id + " is not found");
				return showError(HttpStatus.NOT_FOUND.value(), "the resource with the given id is not found");
			}
			
			LogApp.mensaje(schema.getName()+" "+id+" fetched");

			return result;
		} 
		catch (DataException | ConfigurationException e) {
			LogApp.mensaje("Error", e);
			return showError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		}
	}

	private void includeGroups(String id, Map<String, Object> object ) {
		Schema groupsSchema = schemaReader.getSchemaByResourceType(Constants.RESOURCE_TYPE_GROUP);
		List<Map<String, Object>> groupSearch = storageImplementationFactory.getStorageImplementation(groupsSchema)
				.search(new SearchCriteria(new SearchCriterium("members.value", id, SearchOperation.EQUALS)), 
															   1, returnGroupsMax, null, null);
		
		if ( returnGroupsIncludedFieldsArray == null ) {
			returnGroupsIncludedFieldsArray = Arrays.asList(returnGroupsIncludedFields.split(StringUtils.COMMA));
		}
		List<Map<String, Object>> filteredGroups = new ArrayList<>();
		for( Map<String, Object> m : groupSearch ) {
			filteredGroups.add(filterAttributes(groupsSchema, m, returnGroupsIncludedFieldsArray, null));
		}
		object.put("groups", filteredGroups);
	}

	private boolean haveToIncludeGroups(Schema schema, String excludedAttributes, String operation ) {
		
		return validateIncludeGroups(schema, excludedAttributes) && validateOperationIncludeGroups(operation);
	
	}
	
	private static boolean validateIncludeGroups(Schema schema, String excludedAttributes) {
		
		return schema.getName().equalsIgnoreCase(Constants.RESOURCE_TYPE_USER) && 
			   (excludedAttributes== null || !excludedAttributes.contains("groups"));
	}

	private boolean validateOperationIncludeGroups(String operation) {
		
		return ("GET".equalsIgnoreCase(operation) && returnGroupsOnUserGet) || 
			   ("SEARCH".equalsIgnoreCase(operation) && returnGroupsOnUserSearch);
	}

	/**
	 * Search entities
	 * @param startIndex the start index
	 * @param count the count
	 * @param schema the schema
	 * @param filter the filter
	 * @param sortBy sortby 
	 * @param sortOrder the order
	 * @param attributes the attributes
	 * @param excludedAttributes the excluded attributes
	 * @return a response entity
	 */
	protected ResponseEntity<Map<String, Object>> search(Integer startIndex, Integer count, Schema schema, 
			  String filter, String sortBy, String sortOrder, String attributes, String excludedAttributes) {

		try {
			SearchCriteria searchCriteria = searchCriteriaUtil.composeSearchCriteriaFromSCIMFilter(filter);
			Storage storage = storageImplementationFactory.getStorageImplementation(schema);

			List<String> includeList = getListFromString(attributes);
			
			if ( StringUtils.isEmpty(sortBy)) {
				sortBy = getSearchDefault(schema, SORT_BY);
			}
			
			if ( StringUtils.isEmpty(sortOrder)) {
				sortOrder = getSearchDefault(schema, SORT_ORDER);
			}

			List<Map<String, Object>> dataFetched = storage.
													search(searchCriteria, startIndex, count, sortBy, sortOrder, includeList);
			
			if (dataFetched == null) {
				throw new DataException("null value returned from storage layer implemetation, should be empty list");
			}
			
			List<Map<String, Object>> data = new ArrayList<>();

			for (Map<String, Object> entity : dataFetched) {
				if ( haveToIncludeGroups(schema, excludedAttributes, "SEARCH")) {
					includeGroups( (String)entity.get("id"), entity );
				}
				data.add(filterAttributes(schema, entity, includeList, excludedAttributes));
			}

			ResponseEntity<Map<String, Object>> result = null;

			Map<String, Object> responseObject = new HashMap<>();
			responseObject.put(Constants.KEY_SCHEMAS, new String[] { Constants.SCHEMA_LISTRESPONSE });
			responseObject.put(Constants.KEY_STARTINDEX, startIndex);
			responseObject.put(Constants.KEY_ITEMSPERPAGE, count);
			responseObject.put(Constants.KEY_TOTALRESULTS, storage.count(searchCriteria));
			responseObject.put(Constants.KEY_RESOURCES, data);

			result = new ResponseEntity<>(responseObject, HttpStatus.OK);
			
			LogApp.mensaje("resources of type "+schema.getName()+" start ["+startIndex+"] count["+count+
					      "] filter ["+filter+"] fetched");
			
			return result;

		} 
		catch (InvalidFilterException ife) {
			LogApp.mensaje("the filter [" + filter + "] is not correct : " + ife.getMessage(), ife);
			return showError(HttpStatus.BAD_REQUEST.value(), 
					"the given filter is not correct", ScimErrorType.INVALID_FILTER);
		} 
		catch (DataException | ConfigurationException e) {
			LogApp.mensaje("Error", e);
			return showError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		}
	}

	protected ResponseEntity<Map<String, Object>> delete(String id, Schema schema) {

		Map<String, Object> m = storageImplementationFactory.getStorageImplementation(schema).get(id);

		ResponseEntity<Map<String, Object>> result = null;
		if (m != null) {
			boolean deleted = storageImplementationFactory.getStorageImplementation(schema).delete(id);
			if (deleted) {
				Map<String, Object> resp = new HashMap<>();
				resp.put(HttpStatus.NO_CONTENT.toString(), HttpStatus.NO_CONTENT.getReasonPhrase());
				result = new ResponseEntity<>(resp, HttpStatus.NO_CONTENT);
			}
			else {
				LogApp.mensaje("could not delete resource of type " + schema.getName() + " with id " + id);
				return showError(HttpStatus.BAD_REQUEST.value(), "could not delete resource of type " +
								 schema.getName());
			}
		} 
		else {
			LogApp.mensaje("the resource with id " + id + " is not found");
			return showError(HttpStatus.NOT_FOUND.value(), "the resource with the given id is not found");
		}
		LogApp.mensaje("resource of type "+schema.getName()+" with id "+id+" deleted");
		return result;
	}

	protected void createMeta(String id, Map<String, Object> user, String resourceType, String location) {

		String formattedDate = DateUtils.getDateFormat(Constants.DATEFORMAT_STRING);
		
		LogApp.mensaje("create meta  "+formattedDate+" "+id+" "+user+" "+resourceType+" "+location);
		
		Map<String, String> map = new HashMap<>();
		if (user.containsKey(Constants.KEY_META)) {
			map = (Map<String, String>) user.get(Constants.KEY_META);
			if ( map == null ) {
				map = new HashMap<>();
			}
		}
		else {
			map.put(Constants.KEY_CREATED, formattedDate);
		}
		map.put(Constants.KEY_RESOURCE_TYPE, resourceType);
		map.put(Constants.KEY_LAST_MODIFIED, formattedDate);
		map.put(Constants.KEY_VERSION, createVersion());
		map.put(Constants.KEY_LOCATION, location);

		user.put(Constants.KEY_META, map);
	}
	
	private String getSearchDefault(Schema schema, String type) {
		String kk = schema.getName() + StringUtils.DOT + type;
		if ( defaultSort.containsKey(kk)) {
			return defaultSort.get(kk);
		}
		else {
			String key = "scim.search." + schema.getName() + ".default" + type;
			String value = propertyFactory.getProperty(key);
			if ( StringUtils.isEmpty(value)) {
				value = StringUtils.EMPTY_STRING;
			}
			defaultSort.put(kk, value);
			return value;
		}
	}

	protected String createVersion() {
		return StringUtils.EMPTY_STRING + System.currentTimeMillis();
	}

	protected ResponseEntity<Map<String, Object>> showError(int status, String detail) {
		return showError(status, detail, null);
	}

	protected ResponseEntity<Map<String, Object>> showError(int status, String detail, ScimErrorType scimType) {
		Map<String, Object> error = new HashMap<>();
		error.put(Constants.KEY_SCHEMAS, new String[] { "urn:ietf:params:scim:api:messages:2.0:Error"});
		if (scimType != null) {
			error.put(Constants.KEY_SCIMTYPE, scimType);
		}
		error.put(Constants.KEY_DETAIL, detail);
		error.put(Constants.KEY_STATUS, StringUtils.EMPTY_STRING + status);
		return new ResponseEntity<>(error, HttpStatus.valueOf(status));
	}

	protected Map<String, Object> filterAttributes(Schema schema, Map<String, Object> entity, 
			List<String> includeList, String excludedAttributes) {
		
		Map<String, Object> copy = new HashMap<>();
		copy.putAll(entity);
		List<String> excludeList = getListFromString(excludedAttributes);
		
		for (SchemaAttribute attribute : schema.getAttributes()) {			
			removeAtrr(attribute, includeList, excludeList, copy);
		}

		// meta is not in the schema?
		if (includeList != null && !includeList.isEmpty() && !includeList.contains(Constants.KEY_META)) {
			copy.remove(Constants.KEY_META);
		}
		
		if (excludeList != null && !excludeList.isEmpty() && excludeList.contains(Constants.KEY_META)) {
			copy.remove(Constants.KEY_META);
		}

		return copy;
	}
	
	private static void removeAtrr(SchemaAttribute attribute, List<String> includeList, List<String> excludeList, 
			Map<String, Object> copy) {
		
		if (attribute.getReturned().equalsIgnoreCase(Constants.RETURNED_NEVER)) {
			copy.remove(attribute.getName());
		}
		
		if (includeList != null && !includeList.isEmpty() &&
			(!attribute.getReturned().equalsIgnoreCase(Constants.RETURNED_ALWAYS)
			 && !includeList.contains(attribute.getName()))	) {
			copy.remove(attribute.getName());
		}
		
		if (excludeList != null && !excludeList.isEmpty() && 
				(!attribute.getReturned().equalsIgnoreCase(Constants.RETURNED_ALWAYS)
				 && excludeList.contains(attribute.getName())) ) {
			copy.remove(attribute.getName());
		}
	}

	protected List<String> getListFromString(String attributes) {
		if (!StringUtils.isEmpty(attributes)) {
			return Arrays.asList(attributes.split(StringUtils.COMMA));
		}
		return new ArrayList<>();
	}
	
	protected String createId(Map<String, Object> object) {
		Object id = object.get(Constants.ID);
		if (allowIdOnCreate && id != null) {			
			return id.toString();
		}
		else {
			return UUID.randomUUID().toString();
		}
	}

	protected List<String> extractSchemas(Map<String, Object> user) {
		return (List<String>) user.get(Constants.KEY_SCHEMAS);
	}

	protected ResponseEntity<Map<String, Object>> invalidSchemaForResource(List<String> schemas, String resourceType) {
		LogApp.mensaje("for type " + resourceType + " content is not compliant with schemas " + schemas.toString());
		return showError(HttpStatus.BAD_REQUEST.value(), "content is not compliant with the required schemas",
				ScimErrorType.INVALID_SINTAX);
	}
	
	protected ResponseEntity<Map<String, Object>> invalidSchemaForResource(String resourceType, String requiredSchema) {
		LogApp.mensaje("for patching " + resourceType + ", given schemas is not containing " + requiredSchema);
		return showError(HttpStatus.BAD_REQUEST.value(), "given schemas do not contain the required schema",
				ScimErrorType.INVALID_SINTAX);
	}

	private static boolean canPerformAction(Schema schema, PatchOperation operation, String path) {
		if ( path != null && path.indexOf(StringUtils.DOT) != -1){
			SchemaAttribute attribute = schema.getAttribute(path);
			
			if ( attribute != null && !StringUtils.isEmpty(attribute.getMutability())){
				
				ScimMutability mutability = ScimMutability.valueOf(attribute.getMutability());
				
				if ( isOperationAddRemRep(operation) && isAttrReadOnly(mutability)) {
					return false;
				}
				
			}
			//required attributes can not be removed according to spec
			if ( attribute != null && attribute.isRequired() && operation == PatchOperation.remove ){
				return false;
			}
		}
		return true;
	}

	private static boolean isOperationAddRemRep(PatchOperation op) {
		return op == PatchOperation.add || op == PatchOperation.remove || op == PatchOperation.replace;
	}
	
	private static boolean isAttrReadOnly(ScimMutability mutability) {
		return mutability == ScimMutability.immutable || mutability == ScimMutability.readOnly;
	}
	
}
