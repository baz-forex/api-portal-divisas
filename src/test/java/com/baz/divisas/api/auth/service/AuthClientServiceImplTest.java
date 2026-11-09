package com.baz.divisas.api.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.baz.divisas.api.auth.dao.AuthClientDao;
import com.baz.divisas.api.auth.model.AuthClient;
import com.baz.divisas.general.model.Constant;
import com.mongodb.client.result.InsertOneResult;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AuthClientServiceImplTest {

	@InjectMocks
	private AuthClientServiceImpl authClientService;

	@Mock
	private AuthClientDao authDao;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private InsertOneResult mockInsertResult;

	@Test
	void testCreateAuthClient() {
		when(passwordEncoder.encode(isA(String.class))).thenReturn("encodedPassword");
		when(authDao.insertClient(isA(AuthClient.class))).thenReturn(mockInsertResult);

		AuthClient result = authClientService.createAuthClient("testClient");

		assertThat(result).isNotNull();
		assertThat(result.getClientId()).isNotNull();
		assertThat(result.getClientSecret()).isNotNull();
		// El clientId devuelto debe ser hexadecimal (el hex de "testClient")
		assertThat(result.getClientId()).isEqualTo(Hex.encodeHexString("testClient".getBytes(java.nio.charset.StandardCharsets.UTF_8)));
	}

	@Test
	void testGetAuthClient() {
		AuthClient authClient = new AuthClient();
		authClient.setClientId("usuario1");
		authClient.setClientSecret("secret123");

		when(authDao.findById(isA(String.class))).thenReturn(authClient);

		AuthClient result = authClientService.getAuthClient("usuario1:secret123");

		assertThat(result).isNotNull();
		assertThat(result.getClientId()).isEqualTo("usuario1");
	}

	@Test
	void testGetAuthClientNoEncontrado() {
		when(authDao.findById(isA(String.class))).thenReturn(null);

		AuthClient result = authClientService.getAuthClient("noexiste:password");

		assertThat(result).isNull();
	}

	@Test
	void testParseClientSecret() {
		// "secret" en hex es "736563726574"
		String secretHex = Hex.encodeHexString("secret".getBytes(java.nio.charset.StandardCharsets.UTF_8));

		Map<String, Object> entity = new HashMap<>();
		entity.put(Constant.CLIENT_SECRET, secretHex);

		String result = authClientService.parseClientSecret(entity);

		assertThat(result).isEqualTo("secret");
	}

	@Test
	void testParseClientSecretHexInvalido() {
		Map<String, Object> entity = new HashMap<>();
		entity.put(Constant.CLIENT_SECRET, "ZZZZ");

		String result = authClientService.parseClientSecret(entity);

		assertThat(result).isEmpty();
	}

	@Test
	void testExtractCredentials() {
		String clientIdHex = Hex.encodeHexString("usuario1".getBytes(java.nio.charset.StandardCharsets.UTF_8));
		String clientSecretHex = Hex.encodeHexString("password123".getBytes(java.nio.charset.StandardCharsets.UTF_8));

		Map<String, Object> entity = new HashMap<>();
		entity.put(Constant.CLIENT_ID, clientIdHex);
		entity.put(Constant.CLIENT_SECRET, clientSecretHex);

		when(passwordEncoder.encode(isA(String.class))).thenReturn("encodedSecret");

		String result = authClientService.extractCredentials(entity);

		assertThat(result).isNotNull();
		assertThat(result).startsWith("usuario1:");
		assertThat(result).endsWith("encodedSecret");
	}

	@Test
	void testExtractCredentialsSinClientId() {
		Map<String, Object> entity = new HashMap<>();
		entity.put(Constant.CLIENT_SECRET, "algo");

		String result = authClientService.extractCredentials(entity);

		assertThat(result).isNull();
	}

	@Test
	void testExtractCredentialsSinClientSecret() {
		Map<String, Object> entity = new HashMap<>();
		entity.put(Constant.CLIENT_ID, "algo");

		String result = authClientService.extractCredentials(entity);

		assertThat(result).isNull();
	}

	@Test
	void testExtractCredentialsMapVacio() {
		Map<String, Object> entity = new HashMap<>();

		String result = authClientService.extractCredentials(entity);

		assertThat(result).isNull();
	}
}
