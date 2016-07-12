package lv.ctco.controllers;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import lv.ctco.KnowledgeSharingApplication;
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
import static org.junit.Assert.*;

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
        LocalDateTime date = LocalDateTime.now();
        given().contentType(JSON).body(date).when().post(DATE_PATH).then().statusCode(CREATED);
        given().contentType(JSON).body(date).when().post(DATE_PATH).then().statusCode(NOT_FOUND);
    }

    @Test
    public void testGetDate() throws Exception {

    }

    @Test
    public void testUpdateDate() throws Exception {

    }

    @Test
    public void testDeleteDate() throws Exception {

    }
}