package lv.ctco.controllers;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.parsing.Parser;
import lv.ctco.KnowledgeSharingApplication;
import lv.ctco.entities.Person;
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
import static lv.ctco.Consts.*;
import static org.hamcrest.CoreMatchers.equalTo;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KnowledgeSharingApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:8090")
public class PersonControllerTest {

    @Before
    public void before() {
        RestAssured.port = 8090;
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    public void testGetByIDOK() {
        Person person = new Person();
        person.setUserName("notAName");
        person.setPassword("1234DDD");
        person.setFullName("John Snow");

        Headers header = given().contentType(JSON).body(person).when().post(REGISTER_PATH).getHeaders();
        get(header.getValue("Location")).then().body("password", equalTo("1234DDD"));

    }

    @Test
    public void testPostOK() {
        Person person = new Person();
        person.setUserName("Sherlock");
        person.setFullName("John Snow");

        given().contentType(JSON).body(person).when().post(REGISTER_PATH).then().statusCode(CREATED);

    }

    @Test
    public void testGetByIdFailed() {
        get("/person"+FALLING_ID).then().statusCode(NOT_FOUND);

    }

    @Test
    public void testPostFailed() {
        Person person = new Person();
        person.setUserName("Watson");
        person.setFullName("John Snow");
        given().contentType(JSON).body(person).when().post(REGISTER_PATH).then().statusCode(CREATED);
        given().contentType(JSON).body(person).when().post(REGISTER_PATH).then().statusCode(BAD_REQUEST);

    }
    @Test
    public void testDeleteOK() {
        Person person = new Person();
        person.setUserName("cannonboy");

        Headers header = given().contentType(JSON).body(person).when().post(REGISTER_PATH).getHeaders();
        get(header.getValue("Location")).then().body("userName", equalTo("cannonboy"));
        delete(header.getValue("Location")).then().statusCode(OK);

    }

    @Test
    public void testDeleteFailed() {
        delete("/person"+FALLING_ID).then().statusCode(NOT_FOUND);

    }

    @Test
    public void testPutOK() {
        Person person = new Person();
        person.setUserName("CREEPY");
        person.setFullName("John Snow");


        Headers header = given().contentType(JSON).body(person)
                .when().post(REGISTER_PATH).getHeaders();

        person.setFullName("Mother");

        given().contentType(JSON).body(person)
                .when().put(header.getValue("Location"))
                .then().statusCode(OK);
    }


}