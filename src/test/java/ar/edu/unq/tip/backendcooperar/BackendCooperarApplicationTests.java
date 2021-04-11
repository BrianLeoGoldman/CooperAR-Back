package ar.edu.unq.tip.backendcooperar;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendCooperarApplicationTests {

	@Test
	void contextLoads() {
		String [] args = new String[1];
		args[0] = "arg";
		BackendCooperarApplication.main(args);
	}

}
