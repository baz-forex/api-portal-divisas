package com.baz.divisas.scim;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;

import com.baz.divisas.scim.rest.SchemaController;
import com.baz.divisas.scim.schema.Schema;
import com.baz.divisas.scim.schema.SchemaAttribute;
import com.baz.divisas.scim.schema.SchemaException;
import com.baz.divisas.scim.schema.SchemaMeta;
import com.baz.divisas.scim.schema.SchemaReader;
import com.baz.divisas.scim.storage.ConstraintViolationException;
import com.baz.divisas.scim.storage.DataException;
import com.baz.divisas.scim.storage.StorageImplementationFactory;
import com.baz.divisas.scim.storage.impl.MongoStorage;
import com.baz.divisas.scim.util.Constants;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SchemaControllerTest {

	@InjectMocks
	private SchemaController schemaController;
	@Mock
	private SchemaReader schemaReader;
	@Mock
	private StorageImplementationFactory storageImplementationFactory;
	@Mock
	private MongoStorage mongoStorage;

	   
	@Test
	void testGet() {
		
		MockHttpServletRequest request = new MockHttpServletRequest();
	    MockHttpServletResponse response = new MockHttpServletResponse();
	    
		ResponseEntity<Map<String, Object>> result = schemaController.get("", "", "", "", request, response);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		
		//**********************//		
		Schema schema = new Schema();
		schema.setId(Constants.RESOURCE_TYPE_USER);
		schema.getId();
		schema.setName("");
		schema.getName();
		schema.setDescription("");
		schema.getDescription();
				
		when(schemaReader.getSchemaByResourceType("User")).thenReturn(schema);	
		when(storageImplementationFactory.getStorageImplementation(any())).thenReturn(mongoStorage);
		when(mongoStorage.get(isA(String.class))).thenReturn(null);
		result = schemaController.get("User", "", "", "", request, response);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		
		//**********************//
		SchemaAttribute subAtrr = new SchemaAttribute();
		subAtrr.setName("test");
		
		SchemaAttribute schAtrr = new SchemaAttribute();
		schAtrr.setCanonicalValues(null);
		schAtrr.getCanonicalValues();
		schAtrr.setCanonicalValues(new String[] {});
		schAtrr.getCanonicalValues();
		schAtrr.setCaseExact(true);
		schAtrr.isCaseExact();
		schAtrr.setDescription("");
		schAtrr.getDescription();
		schAtrr.setMultiValued(true);
		schAtrr.isMultiValued();
		schAtrr.setMutability("");
		schAtrr.getMutability();
		schAtrr.setName("");
		schAtrr.getName();
		schAtrr.setReferenceTypes(new String[] {});
		schAtrr.getReferenceTypes();
		schAtrr.setRequired(true);
		schAtrr.isRequired();
		schAtrr.setReturned(Constants.RETURNED_NEVER);
		schAtrr.getReturned();
		schAtrr.setSubAttributes(Arrays.asList(subAtrr));
		schAtrr.getSubAttributes();
		schAtrr.setType("");
		schAtrr.getType();
		schAtrr.setUniqueness("");
		schAtrr.getUniqueness();
		schAtrr.getSubAttribute("");
		schAtrr.getSubAttribute("test");

		List<SchemaAttribute> attributes = new ArrayList<>();		
		attributes.add(schAtrr);
		
		schema.setAttributes(attributes);
		schema.getAttributes();
		schema.getAttribute("");
		schema.getAttribute("test");
		schema.setMeta(new SchemaMeta());
		schema.getMeta();
		
		when(mongoStorage.get(isA(String.class))).thenReturn(new Document());
		
		result = schemaController.get(Constants.RESOURCE_TYPE_USER, "", "name", null, request, response);
		
		schema.setName(Constants.RESOURCE_TYPE_USER);

		result = schemaController.get(Constants.RESOURCE_TYPE_USER, "", "name", null, request, response);
		
		ReflectionTestUtils.setField(schemaController, "returnGroupsOnUserGet", true);
		ReflectionTestUtils.setField(schemaController, "returnGroupsIncludedFields", "id,displayName");
		ReflectionTestUtils.setField(schemaController, "allowIdOnCreate", true);
		result = schemaController.get(Constants.RESOURCE_TYPE_USER, "", "name", "groups", request, response);
		result = schemaController.get(Constants.RESOURCE_TYPE_USER, "", "name", "meta", request, response);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

	}	
	
	@Test
	void testGetException() {
		
		MockHttpServletRequest request = new MockHttpServletRequest();
	    MockHttpServletResponse response = new MockHttpServletResponse();

		
		Schema schema = new Schema();
				
		when(schemaReader.getSchemaByResourceType("User")).thenReturn(schema);	
		doThrow(DataException.class).when(storageImplementationFactory).getStorageImplementation(any());
		ResponseEntity<Map<String, Object>> result = schemaController.get("User", "", "", "", request, response);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@Test
	void testPost() throws ConstraintViolationException {
		
		MockHttpServletRequest request = new MockHttpServletRequest();
	    MockHttpServletResponse response = new MockHttpServletResponse();
	    
		ResponseEntity<Map<String, Object>> result = schemaController.post("", Collections.emptyMap(), "", "", request, response);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		
		//**********************//		
		Schema schema = new Schema();
		schema.setId("");
		
		SchemaAttribute schAtrr = new SchemaAttribute();
		schAtrr.setReturned(Constants.RETURNED_NEVER);
		
		List<SchemaAttribute> attributes = new ArrayList<>();		
		attributes.add(schAtrr);
		schema.setAttributes(attributes);
		
		Map<String, Object> map = new HashMap<>();
				
		when(schemaReader.getSchemaByResourceType(Constants.RESOURCE_TYPE_USER)).thenReturn(schema);	
		when(storageImplementationFactory.getStorageImplementation(any())).thenReturn(mongoStorage);		
		doNothing().when(mongoStorage).create(any(), any());

		result = schemaController.post(Constants.RESOURCE_TYPE_USER, map, "", "", request, response);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		
		//**********************//	
		map.put("schemas", Collections.emptyList());
		result = schemaController.post(Constants.RESOURCE_TYPE_USER, map, "", "", request, response);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
				
		//**********************//		
		map.put("schemas", Arrays.asList("urn:ietf:params:scim:schemas:core:2.0:User"));
		
		result = schemaController.post(Constants.RESOURCE_TYPE_USER, map, "", "", request, response);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		
		
		
		//**********************//	
		schema.setId("urn:ietf:params:scim:schemas:core:2.0:User");
		result = schemaController.post(Constants.RESOURCE_TYPE_USER, map, "", "", request, response);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		
	}	
	
	@Test
	void testPostException() {
		
		MockHttpServletRequest request = new MockHttpServletRequest();
	    MockHttpServletResponse response = new MockHttpServletResponse();

		Schema schema = new Schema();
		schema.setId("urn:ietf:params:scim:schemas:core:2.0:User");
		
		SchemaAttribute schAtrr = new SchemaAttribute();
		schAtrr.setReturned(Constants.RETURNED_NEVER);
		
		List<SchemaAttribute> attributes = new ArrayList<>();		
		attributes.add(schAtrr);
		schema.setAttributes(attributes);
		
		Map<String, Object> map = new HashMap<>();
		map.put("schemas", Arrays.asList("urn:ietf:params:scim:schemas:core:2.0:User"));
		
		when(schemaReader.getSchemaByResourceType(Constants.RESOURCE_TYPE_USER)).thenReturn(schema);	
		doThrow(SchemaException.class).when(storageImplementationFactory).getStorageImplementation(any());
		ResponseEntity<Map<String, Object>> result = schemaController.post(Constants.RESOURCE_TYPE_USER, map, "", "", request, response);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		
		doThrow(ConstraintViolationException.class).when(storageImplementationFactory).getStorageImplementation(any());
		result = schemaController.post(Constants.RESOURCE_TYPE_USER, map, "", "", request, response);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
		
		doThrow(DataException.class).when(storageImplementationFactory).getStorageImplementation(any());
		result = schemaController.post(Constants.RESOURCE_TYPE_USER, map, "", "", request, response);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
}
