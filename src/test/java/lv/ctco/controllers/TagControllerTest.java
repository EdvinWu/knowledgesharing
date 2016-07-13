package lv.ctco.controllers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import lv.ctco.KnowledgeSharingApplication;
import lv.ctco.entities.Tag;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static lv.ctco.Consts.*;
import static lv.ctco.Consts.SESSION_PATH;
import static lv.ctco.Consts.TAG_PATH;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KnowledgeSharingApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:8090")
public class TagControllerTest {
    @Before
    public void before() {
        RestAssured.port = 8090;
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    public void testGetAllSessionTags() {
        get(SESSION_PATH+"/1" + TAG_PATH).then().statusCode(OK);
    }

    @Test
    public void testAddTag() throws Exception {
        Tag tag = new Tag();
        tag.setName("name");

        given().when().body(tag).contentType(ContentType.JSON).post(SESSION_PATH+"/1" + TAG_PATH).then().statusCode(201);
    }


}
