package com.rfsaca.algafood;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(classes = AlgafoodApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class CadastroCozinhaIT {

    @LocalServerPort
    private int port;

    @Test
    public void deveRetornarStatus200_QuandoConsultarCozinhas() {
        RestAssured.given()
                .basePath("/cozinhas")
                .port(port)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());

    }

    @Test
    public void deveConter4Cozinhas_QuandoConsultarCozinhas() {
        RestAssured.given()
                .basePath("/cozinhas")
                .port(port)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("", Matchers.hasSize(6))
                .body("nome", Matchers.hasItems("Indiana", "Chinesa"));

    }

}
