package lv.ctco.controllers;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import lv.ctco.KnowledgeSharingApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static io.restassured.RestAssured.get;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KnowledgeSharingApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:8090")
public class SessionControllerTest {

    public static final int OK = HttpStatus.OK.value();

    @Before
    public void before(){
        RestAssured.port = 8090;
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    public void testGetAllOK() {
        get("/session").then().statusCode(OK);
    }
}
