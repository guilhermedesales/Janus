package com.guilherme.Janus;

import com.guilherme.Janus.model.CategoriaTarefa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureWebTestClient
@DataJpaTest
@ActiveProfiles("test")
class JanusApplicationTests {

    //@Autowired
    //private WebTestClient webTestClient;

	@Test
	void testCreateTarefaSucess() {



	}

}
