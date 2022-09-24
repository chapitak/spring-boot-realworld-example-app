package io.spring.application.article;

import io.spring.api.TestWithCurrentUser;
import io.spring.application.ArticleQueryService;
import io.spring.application.ChunkRequest;
import io.spring.application.data.ArticleHistoryData;
import io.spring.application.data.ArticleHistoryDataList;
import io.spring.core.article.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Transactional
@SpringBootTest
public class ArticleCommandServiceTest extends TestWithCurrentUser {
    @Autowired
    ArticleCommandService articleCommandService;

    @Autowired
    ArticleQueryService articleQueryService;

    String title = "How to create a dragon";
    String description = "Ever wonder how?";
    String body = "You have to believe";

    @Test
    void create_make_articlehistory_test() {
        // given
        articleCommandService.createArticle(new NewArticleParam(title, description, body, Collections.emptyList()), user);

        // when
        ArticleHistoryDataList articleHistoryDataList = articleQueryService.findArticleHistories(user
                , new ChunkRequest(0, 1, Sort.by(Sort.Direction.DESC, "id")));
        ArticleHistoryData articleHistoryData = articleHistoryDataList.getArticleHIstoryDatas().get(0);

        // then
        assertThat(articleHistoryData.getRevType()).isEqualTo("생성");
        assertThat(articleHistoryData.getTitle()).isEqualTo("How to create a dragon");
    }

    @Test
    void update_make_articlehistory_test() {
        // given
        Article targetArticle = articleCommandService.createArticle(new NewArticleParam(title, description, body, Collections.emptyList()), user);
        String updatedTitle = "How to update a dragon";
        articleCommandService.updateArticle(targetArticle, new UpdateArticleParam(updatedTitle, body, description));

        // when
        ArticleHistoryDataList articleHistoryDataList = articleQueryService.findArticleHistories(user
                , new ChunkRequest(0, 1, Sort.by(Sort.Direction.DESC, "id")));
        ArticleHistoryData articleHistoryData = articleHistoryDataList.getArticleHIstoryDatas().get(0);

        // then
        assertThat(articleHistoryData.getRevType()).isEqualTo("수정");
        assertThat(articleHistoryData.getTitle()).isEqualTo("How to update a dragon");
    }

    @Test
    void delete_make_articlehistory_test() {
        // given
        String deletedTitle = "How to delete a dragon";
        Article targetArticle = articleCommandService.createArticle(new NewArticleParam(deletedTitle, description, body, Collections.emptyList()), user);
        articleCommandService.deleteArticle(targetArticle);

        // when
        ArticleHistoryDataList articleHistoryDataList = articleQueryService.findArticleHistories(user
                , new ChunkRequest(0, 1, Sort.by(Sort.Direction.DESC, "id")));
        ArticleHistoryData articleHistoryData = articleHistoryDataList.getArticleHIstoryDatas().get(0);

        // then
        assertThat(articleHistoryData.getRevType()).isEqualTo("삭제");
        assertThat(articleHistoryData.getTitle()).isEqualTo(deletedTitle);
    }
}