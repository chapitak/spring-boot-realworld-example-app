package io.spring.api;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.spring.JacksonCustomizations;
import io.spring.api.security.WebSecurityConfig;
import io.spring.application.ArticleQueryService;
import io.spring.application.data.ArticleHistoryDataList;
import io.spring.core.article.Article;
import io.spring.core.articlehistory.ArticleHistory;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest({ArticleHistoryAPI.class})
@Import({WebSecurityConfig.class, JacksonCustomizations.class})
public class ArticleHistoryAPITest extends TestWithCurrentUser {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ArticleQueryService articleQueryService;


    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        RestAssuredMockMvc.mockMvc(mvc);
    }

    @Test
    void should_read_article_history() {
        String slug = "test-new-article";
        DateTime time = new DateTime();
        Article article =
                new Article(
                        "Test New Article",
                        "Desc",
                        "Body",
                        Arrays.asList("java", "spring", "jpg"),
                        user.getId(),
                        time);
        ArticleHistory articleHistory = article.toArticleHistory(0);

        when(articleQueryService.findArticleHistory(any(), any())).thenReturn(Optional.of(articleHistory));

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .header("Authorization", "Token " + token)
                .when()
                .get("/article-histories/1")
                .then()
                .statusCode(200)
                .body("article.title", equalTo("Test New Article"))
                .body("article.body", equalTo("Body"))
                .body("article.createdAt", equalTo(ISODateTimeFormat.dateTime().withZoneUTC().print(time)))
                .body("article.revType", equalTo("생성"));
    }

    @Test
    void should_read_article_histories() {
        String slug = "test-new-article";
        DateTime time = new DateTime();
        Article article =
                new Article(
                        "Test New Article",
                        "Desc",
                        "Body",
                        Arrays.asList("java", "spring", "jpg"),
                        user.getId(),
                        time);
        ArticleHistory articleHistory = article.toArticleHistory(0);

        when(articleQueryService.findArticleHistories(any(), any())).thenReturn(new ArticleHistoryDataList(Collections.singletonList(articleHistory.toData()), 1));

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .header("Authorization", "Token " + token)
                .when()
                .get("/article-histories")
                .then()
                .statusCode(200)
                .body("articles[0].title", equalTo("Test New Article"))
                .body("articles[0].body", equalTo("Body"))
                .body("articles[0].createdAt", equalTo(ISODateTimeFormat.dateTime().withZoneUTC().print(time)))
                .body("articles[0].revType", equalTo("생성"));
    }
}
