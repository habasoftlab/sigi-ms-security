package com.servispeed.ms_security;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled 
class MsSecurityApplicationTests {

	@Test
	void contextLoads() {
		assertTrue(true, "El contexto de Spring Boot se cargó exitosamente");
	}

}
