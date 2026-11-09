package com.baz.divisas.general.util;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.baz.divisas.general.config.PropApp;

public final class AESUtils {

	private static final String ALGORITHM_HMAC = "HmacSHA256";
	private static final String AES = "AES";
	private static final int SIZE_BYTES = 16;
	private static final int GCM_IV_LENGTH = 12;
	private static final SecureRandom secureRandom = new SecureRandom();
	
	private AESUtils() {
		super();
	}

	/**
	 * Metodo de encriptacion AES
	 * @param valorCampo - Valor a encriptar
	 * @param aesKeyBase64 - keyAES
	 * @param hmacKeyBase64 - keyHASH
	 * @return Cadena encriptada
	 */
	public static String encrypt(String valorCampo, String aesKeyBase64, String hmacKeyBase64) {

		try {
			SecretKeySpec aesKey = new SecretKeySpec(
					Base64.getDecoder().decode(aesKeyBase64.getBytes(StandardCharsets.UTF_8)), AES);
			SecretKeySpec hmacKey = new SecretKeySpec(
					Base64.getDecoder().decode(hmacKeyBase64.getBytes(StandardCharsets.UTF_8)), ALGORITHM_HMAC);

			byte[] iv = new byte[SIZE_BYTES];
			new SecureRandom().nextBytes(iv);
			Cipher cipher = Cipher.getInstance(PropApp.getAlgoritmoAES());
			cipher.init(Cipher.ENCRYPT_MODE, aesKey, new IvParameterSpec(iv));

			byte[] plainText = valorCampo.getBytes(StandardCharsets.UTF_8);

			byte[] cipherText = cipher.doFinal(plainText);
			byte[] ivCipherText = concatenateBytes(iv, cipherText);
			byte[] hmac = generarHMAC(hmacKey, ivCipherText);
			byte[] ivCipherTextHmac = concatenateBytes(ivCipherText, hmac);

			byte[] ivCipherTextHmacBase64 = Base64.getEncoder().encode(ivCipherTextHmac);
			return new String(ivCipherTextHmacBase64, StandardCharsets.UTF_8);

		} 
		catch (GeneralSecurityException e) {
			LogApp.excepcion(e, "Incidente al cifrar el parametro");
		}

		return "";

	}

	private static byte[] generarHMAC(SecretKey key, byte[] hmacInput) {
		Mac hmac;
		try {
			hmac = Mac.getInstance(ALGORITHM_HMAC);
			hmac.init(key);
			return hmac.doFinal(hmacInput);
		} 
		catch (NoSuchAlgorithmException | InvalidKeyException e) {
			LogApp.excepcion(e, "No ha sido posible generar HMAC");
			return new byte[0];
		}
	}

	private static byte[] concatenateBytes(byte[] first, byte[] second) {

		int byteTam = first.length + second.length;

		byte[] concatBytes = new byte[byteTam];
		System.arraycopy(first, 0, concatBytes, 0, first.length);
		System.arraycopy(second, 0, concatBytes, first.length, second.length);
		return concatBytes;
	}
	
	public static String encryptAES(String plaintext, String llave, String hash) {
	     try {
	    	// --- Encripta en AES-GCM
	         byte[] decodedKey;
	         decodedKey = Base64.getDecoder().decode(llave);
	         SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
	         byte[] iv = new byte[GCM_IV_LENGTH]; //NEVER REUSE THIS IV WITH SAME KEY
	         byte[] associatedData = hash.getBytes(StandardCharsets.UTF_8);
	         secureRandom.nextBytes(iv);
	         final Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
	         GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv); //128 bit auth tag length
	         cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);
	         cipher.updateAAD(associatedData);

	         byte[] cipherText = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

	         ByteBuffer byteBuffer = ByteBuffer.allocate(iv.length + cipherText.length);
	         byteBuffer.put(iv);
	         byteBuffer.put(cipherText);
	         return Base64.getEncoder().encodeToString(byteBuffer.array());
	     } 
	     catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
	         return "Ocurrior un error al cifrar en AES-GCM: " + e;
	     }

	 }
}
