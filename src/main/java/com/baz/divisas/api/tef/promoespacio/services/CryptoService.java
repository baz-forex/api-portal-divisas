package com.baz.divisas.api.tef.promoespacio.services;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;
import com.baz.divisas.general.config.PropPromoEspacio;

@Service
public class CryptoService {

	   private static final int GCM_IV_LENGTH = 16;
	    private static final int GCM_TAG_LENGTH = 128;

	    private final SecureRandom secureRandom = new SecureRandom();

	    private byte[] randomKey;

	    public void generateRandomKey() {
	        this.randomKey = new byte[32];
	        secureRandom.nextBytes(this.randomKey);
	    }

	    public byte[] getRandomKey() {
	        return this.randomKey;
	    }

	    /**
	     * Cifra el client_secret con AES-GCM usando la llave aleatoria.
	     * Equivalente a encrypt_password en Python.
	     */
	    public String encryptPassword() {
	        try {
	            byte[] iv = new byte[GCM_IV_LENGTH];
	            secureRandom.nextBytes(iv);

	            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
	            SecretKeySpec keySpec = new SecretKeySpec(randomKey, "AES");
	            GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
	            cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec);

	            byte[] ciphertext = cipher.doFinal(PropPromoEspacio.getClienteSecret().getBytes());

	            // iv + ciphertext (incluye tag al final)
	            byte[] result = new byte[iv.length + ciphertext.length];
	            System.arraycopy(iv, 0, result, 0, iv.length);
	            System.arraycopy(ciphertext, 0, result, iv.length, ciphertext.length);

	            return Base64.getEncoder().encodeToString(result);
	        } catch (Exception e) {
	            throw new RuntimeException("Error al cifrar password", e);
	        }
	    }

	    /**
	     * Cifra la llave aleatoria con RSA-OAEP (SHA-256, MGF1-SHA1).
	     * Equivalente a get_cipher_request_key en Python.
	     */
	    public String getCipherRequestKey() {
	        try {
	            String publicKeyPem = PropPromoEspacio.getPublicKey()
	                    .replace("-----BEGIN PUBLIC KEY-----", "")
	                    .replace("-----END PUBLIC KEY-----", "")
	                    .replaceAll("\\s", "");

	            byte[] keyBytes = Base64.getDecoder().decode(publicKeyPem);
	            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
	            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	            PublicKey publicKey = keyFactory.generatePublic(spec);

	            // OAEP con SHA-256 y MGF1-SHA1 (igual que Python)
	            OAEPParameterSpec oaepParams = new OAEPParameterSpec(
	                    "SHA-256",
	                    "MGF1",
	                    MGF1ParameterSpec.SHA1,
	                    PSource.PSpecified.DEFAULT
	            );

	            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPPadding");
	            cipher.init(Cipher.ENCRYPT_MODE, publicKey, oaepParams);

	            // En Python se cifra base64(random_key)
	            byte[] dataToEncrypt = Base64.getEncoder().encode(randomKey);
	            byte[] encrypted = cipher.doFinal(dataToEncrypt);

	            return Base64.getEncoder().encodeToString(encrypted);
	        } catch (Exception e) {
	            throw new RuntimeException("Error al cifrar request key", e);
	        }
	    }

	    /**
	     * Descifra el token recibido con AES-GCM.
	     * Equivalente a decrypt_data en Python.
	     */
	    public String decryptToken(String tokenCifrado) {
	        try {
	            byte[] data = Base64.getDecoder().decode(tokenCifrado);
	            byte[] iv = new byte[GCM_IV_LENGTH];
	            System.arraycopy(data, 0, iv, 0, GCM_IV_LENGTH);

	            byte[] encryptedContent = new byte[data.length - GCM_IV_LENGTH];
	            System.arraycopy(data, GCM_IV_LENGTH, encryptedContent, 0, encryptedContent.length);

	            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
	            SecretKeySpec keySpec = new SecretKeySpec(randomKey, "AES");
	            GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
	            cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmSpec);

	            byte[] decrypted = cipher.doFinal(encryptedContent);
	            return new String(decrypted);
	        } catch (Exception e) {
	            throw new RuntimeException("Error al descifrar token", e);
	        }
	    }
}
