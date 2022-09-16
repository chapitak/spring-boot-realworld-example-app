package io.spring.application.article;

import io.spring.core.article.Article;
import io.spring.core.articlehistory.ArticleHistoryRepository;
import io.spring.core.article.ArticleRepository;
import io.spring.core.user.User;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@AllArgsConstructor
public class ArticleCommandService {

  private ArticleRepository articleRepository;
  private ArticleHistoryRepository articleHistoryRepository;

  public Article createArticle(@Valid NewArticleParam newArticleParam, User creator) {
    Article article =
        new Article(
            newArticleParam.getTitle(),
            newArticleParam.getDescription(),
            newArticleParam.getBody(),
            newArticleParam.getTagList(),
            creator.getId());
    articleRepository.save(article);
    articleHistoryRepository.save(article.toArticleHistory(0));
    return article;
  }

  public Article updateArticle(Article article, @Valid UpdateArticleParam updateArticleParam) {
    article.update(
        updateArticleParam.getTitle(),
        updateArticleParam.getDescription(),
        updateArticleParam.getBody());
    articleRepository.save(article);
    articleHistoryRepository.save(article.toArticleHistory(1));
    return article;
  }
}
