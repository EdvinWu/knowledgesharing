package lv.ctco.controllers;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.parsing.Parser;
import lv.ctco.KnowledgeSharingApplication;
import lv.ctco.entities.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static lv.ctco.Consts.JSON;
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

        Headers header = given().contentType(JSON).body(person).when().post("/register/").getHeaders();
        get(header.getValue("Location")).then().body("password", equalTo("1234DDD"));


    }
}