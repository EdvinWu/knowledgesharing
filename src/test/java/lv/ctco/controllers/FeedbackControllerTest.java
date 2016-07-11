package lv.ctco.controllers;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.parsing.Parser;
import lv.ctco.KnowledgeSharingApplication;
import lv.ctco.entities.Feedback;
import lv.ctco.entities.KnowledgeSession;
import lv.ctco.entities.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import lv.ctco.Consts;


import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static lv.ctco.Consts.*;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KnowledgeSharingApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:8090")

public class FeedbackControllerTest {


    @Before
    public void before() {
        RestAssured.port = 8090;
        RestAssured.defaultParser = Parser.JSON;
    }


    @Test
    public void testSessionAccessToFeedbackNotFound() throws Exception {
        get(SESSION_PATH + FALLING_ID + FEEDBACK_PATH).then().statusCode(NOT_FOUND);
    }

    @Test
    public void testGetAllSessionOK() {

        get(SESSION_PATH).then().statusCode(OK);
    }

    @Test
    public void testGetOneFeedbackOK() {

        KnowledgeSession session = new KnowledgeSession();
        session.setAuthor("John");
        session.setTitle("Snow");
        session.setVotes(0);

        List<Feedback> feedbacks = new ArrayList<>();
        Feedback feedback = new Feedback();
        feedback.setComment("Comment");
        feedback.setRating(10);
        feedbacks.add(feedback);
        session.setFeedbacks(feedbacks);

        Headers header = given()
                .contentType(JSON)
                .body(session)
                .when()
                .post(SESSION_PATH).getHeaders();

        Headers header1 = given()
                .contentType(JSON)
                .body(feedback)
                .when()
                .post("/" + session.getId() + FEEDBACK_PATH).getHeaders();


        get(header.getValue("Location") + header1.getValue("Location")).then().statusCode(OK);

    }

    @Test
    public void testGetOneSessionNotFound() {
        get(SESSION_PATH + FALLING_ID + FEEDBACK_PATH).then().statusCode(NOT_FOUND);
    }

    @Test
    public void testGetOneFeedbackNotFound() {

        KnowledgeSession session = new KnowledgeSession();
        session.setAuthor("John");
        session.setTitle("Snow");
        session.setVotes(0);

        get(SESSION_PATH + session.getId() + FEEDBACK_PATH + FALLING_ID).then().statusCode(NOT_FOUND);
    }
}
