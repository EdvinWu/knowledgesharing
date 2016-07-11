package lv.ctco.controllers;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.parsing.Parser;
import lv.ctco.Consts;
import lv.ctco.KnowledgeSharingApplication;
import lv.ctco.entities.KnowledgeSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import static lv.ctco.Consts.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KnowledgeSharingApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:8090")
public class SessionControllerTest {

    @Before
    public void before() {
        RestAssured.port = 8090;
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    public void testGetAllOK() {
        get(SESSION_PATH).then().statusCode(OK);
    }

    @Test
    public void testGetOneNotFound() {
        get(SESSION_PATH + FALLING_ID).then().statusCode(NOT_FOUND);
    }

    @Test
    public void testGetOneOK() {
        KnowledgeSession session = new KnowledgeSession();
        session.setAuthor("John");
        session.setTitle("Snow");
        session.setVotes(0);

        Headers header = given().contentType(JSON).body(session).when().post(SESSION_PATH).getHeaders();
        get(header.getValue("Location")).then().body("author", equalTo("John"));

    }

    @Test
    public void testPostCreated() {
        KnowledgeSession session = new KnowledgeSession();
        given().contentType(JSON).body(session)
                .when().post(SESSION_PATH)
                .then().statusCode(CREATED);
    }

    @Test
    public void testDeleteOK() {
        KnowledgeSession session = new KnowledgeSession();
        session.setAuthor("John");
        session.setTitle("Snow");
        session.setVotes(0);

        Headers header = given().contentType(JSON).body(session).when().post(SESSION_PATH).getHeaders();
        delete(header.getValue("Location")).then().statusCode(OK);
    }

    @Test
    public void testDeleteNotFound() {
        delete(Consts.SESSION_PATH + FALLING_ID).then().statusCode(NOT_FOUND);
    }

    @Test
    public void testPutOK() {
        KnowledgeSession session = new KnowledgeSession();
        session.setAuthor("John123");
        session.setTitle("Snow123");
        session.setVotes(0);


        Headers header = given().contentType(JSON).body(session)
                .when().post(SESSION_PATH).getHeaders();

        session.setAuthor("Joe");
        session.setTitle("XO");
        session.setVotes(0);
        given().contentType(JSON).body(session)
                .when().put(header.getValue("Location"))
                .then().statusCode(OK);
    }

    @Test
    public void testPutFails() {
        KnowledgeSession student = new KnowledgeSession();
        given().contentType(JSON).body(student)
                .when().put(SESSION_PATH + FALLING_ID)
                .then().statusCode(NOT_FOUND);
    }



}
