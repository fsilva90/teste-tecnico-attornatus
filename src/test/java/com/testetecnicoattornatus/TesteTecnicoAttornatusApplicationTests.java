package com.testetecnicoattornatus;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TesteTecnicoAttornatusApplicationTests {

	@Test
	void contextLoads() {
		Assertions.assertThatCode(() -> TesteTecnicoAttornatusApplication.main(new String[]{}))
				.doesNotThrowAnyException();
	}

}
