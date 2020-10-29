package com.leadtechnologist.licketyscript.config;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

//@QuarkusTest
public class GreetingResourceTest {

//    @Test
    public void testHelloEndpoint() {
        given()
            .when().get("/config-yaml/greeting")
            .then()
            .statusCode(200)
            .body(is("hello quarkus!"));
    }

}
