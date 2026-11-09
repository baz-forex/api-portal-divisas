package com.baz.divisas.general.util;

import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource.PSpecified;


public class RSAUtilsPagosDigitales {


    public static PublicKey getPublicKey(String llavePublica) {
        byte[] decode = Base64.getDecoder().decode(llavePublica);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decode);
        PublicKey pk = null;

        try {
            KeyFactory factory = KeyFactory.getInstance("RSA");
            pk = factory.generatePublic(spec);
            return pk;
        } 
        catch (Exception e) {
           LogApp.excepcion(e, "Ocurrio un error al obtener la llave pública");
           return pk;
        }
    }

    public static PrivateKey getPrivateKey(String llavePrivada){
        byte[] decode = Base64.getDecoder().decode(llavePrivada);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decode);
        PrivateKey pk = null;

        try {
            KeyFactory factory = KeyFactory.getInstance("RSA");
            pk = factory.generatePrivate(spec);
            return pk;
        } 
        catch (Exception e) {
        	LogApp.excepcion(e, "Ocurrio un error al obtener la llave privada");
            return pk;
        }
    }

    public static String EncryptOAE256(String plain, String publicKey) {
        try {
            PublicKey llavePublica = getPublicKey(publicKey);
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
            OAEPParameterSpec oaepParameterSpec = new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSpecified.DEFAULT);
            cipher.init(1, llavePublica, oaepParameterSpec);
            String textoPlano = Base64.getEncoder().encodeToString(cipher.doFinal(plain.getBytes(Charset.forName("UTF-8"))));
            return textoPlano;
        } 
        catch (Exception e) {
        	LogApp.excepcion(e, "Ocurrio un error al EncryptOAE256");
            return "";
        }
		
    }

    public static String DecryptOAE256(String result, String privateKey) {
        try {
            PrivateKey llavePrivada = getPrivateKey(privateKey);
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
            OAEPParameterSpec oaepParameterSpec = new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSpecified.DEFAULT);
            cipher.init(2, llavePrivada, oaepParameterSpec);
            String textoPlano = new String(cipher.doFinal(Base64.getDecoder().decode(result)), "UTF-8");
            return textoPlano;
        } 
        catch (Exception e) {
        	LogApp.excepcion(e, "Ocurrio un error al DecryptOAE256");
        	return "";
        }
        
    }
}
