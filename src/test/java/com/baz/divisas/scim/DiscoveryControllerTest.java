package com.baz.divisas.scim;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

import com.baz.divisas.scim.rest.DiscoveryController;
import com.baz.divisas.scim.schema.SchemaReader;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DiscoveryControllerTest {

	@InjectMocks
	private DiscoveryController discoveryController;
	@Mock
	private SchemaReader schemaReader;
	


	@Test
	void testGetServiceProviderConfig() throws IOException {
		
		MockHttpServletRequest request = new MockHttpServletRequest();
	    MockHttpServletResponse response = new MockHttpServletResponse();
	    
		when(schemaReader.getServiceProviderConfig()).thenReturn(Collections.emptyMap());			
		ResponseEntity<Map<String, Object>> result = discoveryController.getServiceProviderConfig(request, response);	
		result = discoveryController.getServiceProviderConfig(request, response);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

	}
	
	@Test
	void testGetServiceProviderConfigException() throws IOException {
		
		MockHttpServletRequest request = new MockHttpServletRequest();
	    MockHttpServletResponse response = new MockHttpServletResponse();

		doThrow(IOException.class).when(schemaReader).getServiceProviderConfig();
		ResponseEntity<Map<String, Object>> result = discoveryController.getServiceProviderConfig(request, response);		
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Test
	void testGetResourceTypes() throws IOException {
		
		MockHttpServletRequest request = new MockHttpServletRequest();
	    MockHttpServletResponse response = new MockHttpServletResponse();
	    
		when(schemaReader.getResourceTypes()).thenReturn(Collections.emptyList());	
		
	    ResponseEntity<List<Object>> result = discoveryController.getResourceTypes(request, response);
	    result = discoveryController.getResourceTypes(request, response);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
	}	
	
	@Test
	void testGetResourceTypesException() throws IOException {
		
		MockHttpServletRequest request = new MockHttpServletRequest();
	    MockHttpServletResponse response = new MockHttpServletResponse();

		doThrow(IOException.class).when(schemaReader).getResourceTypes();
		ResponseEntity<List<Object>> result = discoveryController.getResourceTypes(request, response);		
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Test
	void testGetSchemas() throws IOException {
		
		MockHttpServletRequest request = new MockHttpServletRequest();
	    MockHttpServletResponse response = new MockHttpServletResponse();
	    
		when(schemaReader.getSchemas()).thenReturn(Collections.emptyList());	
		
	    ResponseEntity<List<Object>> result = discoveryController.getSchemas(request, response);
	    result = discoveryController.getSchemas(request, response);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
	}	
	
	@Test
	void testGetSchemasException() throws IOException {
		
		MockHttpServletRequest request = new MockHttpServletRequest();
	    MockHttpServletResponse response = new MockHttpServletResponse();

		doThrow(IOException.class).when(schemaReader).getSchemas();
		 ResponseEntity<List<Object>> result = discoveryController.getSchemas(request, response);		
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
}
