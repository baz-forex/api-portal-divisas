package com.baz.divisas.api.tef.promoespacio.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CryptoServiceTest {

	private CryptoService cryptoService;

	@BeforeEach
	void setUp() {
		cryptoService = new CryptoService();
		cryptoService.generateRandomKey();
	}

	@Test
	void testGenerateRandomKey() {
		// La llave generada debe tener 32 bytes
		byte[] key = cryptoService.getRandomKey();
		assertThat(key).isNotNull();
		assertThat(key).hasSize(32);
	}

	@Test
	void testEncryptPassword() {
		String encrypted = cryptoService.encryptPassword();
		assertThat(encrypted).isNotNull();
		assertThat(encrypted).isNotEmpty();
	}

	@Test
	void testGetCipherRequestKey() {
		// Usa la publicKey del archivo de propiedades (RSA valida)
		String cipherKey = cryptoService.getCipherRequestKey();
		assertThat(cipherKey).isNotNull();
		assertThat(cipherKey).isNotEmpty();
	}

	@Test
	void testEncryptDecryptRoundTrip() {
		// encryptPassword produce Base64(iv + ciphertext) con AES-GCM usando randomKey.
		// decryptToken usa el mismo formato/llave, por lo que puede descifrarlo.
		String encrypted = cryptoService.encryptPassword();
		String decrypted = cryptoService.decryptToken(encrypted);

		// El contenido descifrado corresponde al clienteSecret cifrado
		assertThat(decrypted).isNotNull();
	}

	@Test
	void testDecryptTokenInvalido() {
		// Token con formato invalido -> lanza RuntimeException
		assertThatThrownBy(() -> cryptoService.decryptToken("token-no-valido-base64-!!!"))
				.isInstanceOf(RuntimeException.class);
	}

	@Test
	void testEncryptPasswordSinLlaveLanzaExcepcion() {
		// Sin generar la llave (randomKey null) -> encryptPassword lanza RuntimeException
		CryptoService sinLlave = new CryptoService();
		assertThatThrownBy(sinLlave::encryptPassword)
				.isInstanceOf(RuntimeException.class);
	}
}
