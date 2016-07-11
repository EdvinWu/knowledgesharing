package lv.ctco.controllers;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.parsing.Parser;
import lv.ctco.Consts;
import lv.ctco.KnowledgeSharingApplication;
import lv.ctco.entities.KnowledgeSession;
import lv.ctco.entities.ScheduledSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import static lv.ctco.Consts.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KnowledgeSharingApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:8090")

public class ScheduledSessionControllerTest {

    @Before
    public void before() {
        RestAssured.port = 8090;
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    public void testGetAllOK() {
        get(SCHEDULED_PATH).then().statusCode(OK);
    }

    @Test
    public void testGetOneNotFound() {
        get(SCHEDULED_PATH + FALLING_ID).then().statusCode(NOT_FOUND);
    }

    @Test
    public void testGetOneOK() {
        ScheduledSession session = new ScheduledSession();
        session.setPlace("John");

        Headers header = given().contentType(JSON).body(session).when().post(SCHEDULED_PATH).getHeaders();
        get(header.getValue("Location")).then().body("place", equalTo("John"));

    }

    @Test
    public void testPostCreated() {
        ScheduledSession session = new ScheduledSession();
        session.setPlace("John");

        given().contentType(JSON).body(session)
                .when().post(SCHEDULED_PATH)
                .then().statusCode(CREATED);
    }

    @Test
    public void testDeleteOK() {
        ScheduledSession session = new ScheduledSession();

        Headers header = given().contentType(JSON).body(session).when().post(SCHEDULED_PATH).getHeaders();
        delete(header.getValue("Location")).then().statusCode(OK);
    }

    @Test
    public void testDeleteNotFound() {
        delete(SCHEDULED_PATH + FALLING_ID).then().statusCode(NOT_FOUND);
    }

    @Test
    public void testPutOK() {
        ScheduledSession session = new ScheduledSession();
        session.setPlace("John");
        session.setDate(LocalDateTime.now().toString());


        Headers header = given().contentType(JSON).body(session)
                .when().post(SCHEDULED_PATH).getHeaders();

        session.setPlace("Joe");
        session.setDate(LocalDateTime.now().toString());
        given().contentType(JSON).body(session)
                .when().put(header.getValue("Location"))
                .then().statusCode(OK);
    }

    @Test
    public void testPutFails() {
        KnowledgeSession student = new KnowledgeSession();
        given().contentType(JSON).body(student)
                .when().put(SCHEDULED_PATH + FALLING_ID)
                .then().statusCode(NOT_FOUND);
    }
}
