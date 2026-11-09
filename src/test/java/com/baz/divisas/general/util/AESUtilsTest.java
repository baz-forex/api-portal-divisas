package com.baz.divisas.general.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AESUtilsTest {

	@InjectMocks
	private AESUtils aes;
	
	@Test
	public void encriptaAES() {
			
		AESUtils.encryptAES("123123123","7wmkNnQGL58hIM5I79GNh1D9jztTh1wyE4s/I7qNIr4=","MMP5mog8SkT76oqjrJz1aw==");
		
		AESUtils.encryptAES("123123123","7wmkNnQGL58hIM5I79GNh1D9jztTh1wyE4s/I7qNIr4=","==");
	}
}
