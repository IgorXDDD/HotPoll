package com.pik.hotpoll;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class HotpollApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void getTestParam(){
		HotpollApplication hotpollApplication = new HotpollApplication();
		String result = hotpollApplication.testString("OK");
		assertEquals(result, "{\"string\": \"Testing OK!\"}");
	}

}
