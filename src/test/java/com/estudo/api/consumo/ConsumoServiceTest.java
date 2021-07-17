package com.estudo.api.consumo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Tag("unit")
public class ConsumoServiceTest {
	
	@Before
	public void setUp() {
		System.out.println("Antes do teste");
	}
	
	@After
	public void finish() {
		System.out.println("Depois do teste");
	}
	
	@Test
	public void teste1() {
		System.out.println("Teste 1");
	}
}
