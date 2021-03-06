package lv.ctco.controllers;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.parsing.Parser;
import lv.ctco.KnowledgeSharingApplication;
import lv.ctco.entities.KnowledgeSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static lv.ctco.Consts.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KnowledgeSharingApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:8090")

public class DateSessionControllerTest {



    @Before
    public void before() {
        RestAssured.port = 8090;
        RestAssured.defaultParser = Parser.JSON;
    }


    @Test
    public void testAddDate() throws Exception {
        KnowledgeSession session = new KnowledgeSession();
        LocalDateTime date = LocalDateTime.now();
        Headers headers =  given()
              .contentType(JSON)
              .body(session)
              .when()
              .post(SESSION_PATH)
              .getHeaders();

        given().contentType(JSON).body(date).when().post(headers.getValue("Location")+DATE_PATH).then().statusCode(CREATED);
    }

    @Test
    public void testGetDate() throws Exception {
        KnowledgeSession session = new KnowledgeSession();
        LocalDateTime date = LocalDateTime.now();
        session.setDate(date);
        Headers headers =  given()
                .contentType(JSON)
                .body(session)
                .when()
                .post(SESSION_PATH)
                .getHeaders();

        given().contentType(JSON).body(date).when().get(headers.getValue("Location") + DATE_PATH).then().statusCode(OK);

    }

    @Test
    public void testUpdateDate() throws Exception {
        KnowledgeSession session = new KnowledgeSession();
        LocalDateTime date = LocalDateTime.now();
        LocalDateTime newDate = LocalDateTime.now();
        Headers headers =  given()
                .contentType(JSON)
                .body(session)
                .when()
                .post(SESSION_PATH)
                .getHeaders();

        given().contentType(JSON).body(date).when().post(headers.getValue("Location") + DATE_PATH).then().statusCode(CREATED);
        given().contentType(JSON).body(newDate).when().put(headers.getValue("Location") + DATE_PATH).then().statusCode(OK);
    }

    @Test
    public void testDeleteDate() throws Exception {
        KnowledgeSession session = new KnowledgeSession();
        LocalDateTime date = LocalDateTime.now();
        session.setDate(date);
        Headers headers =  given()
                .contentType(JSON)
                .body(session)
                .when()
                .post(SESSION_PATH)
                .getHeaders();
        given().contentType(JSON).body(date).when().delete(headers.getValue("Location") + DATE_PATH).then().statusCode(OK);

    }
}