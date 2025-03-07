package io.spring.infrastructure.article;

import io.spring.core.article.Article;
import io.spring.core.article.ArticleRepository;
import io.spring.core.user.User;
import io.spring.core.user.UserRepository;
import io.spring.infrastructure.mybatis.mapper.ArticleMapper;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ArticleRepositoryTransactionTest {
  @Autowired private ArticleRepository articleRepository;

  @Autowired private UserRepository userRepository;

  @Autowired private ArticleMapper articleMapper;

/*
  @Test
  public void transactional_test() {
    User user = new User("aisensiy@gmail.com", "aisensiy", "123", "bio", "default");
    userRepository.save(user);
    Article article =
        new Article("test", "desc", "body", Arrays.asList("java", "spring"), user.getId());
    articleRepository.save(article);
    Article anotherArticle =
        new Article("test", "desc", "body", Arrays.asList("java", "spring", "other"), user.getId());
    try {
      articleRepository.save(anotherArticle);
    } catch (Exception e) {
      Assertions.assertNull(articleMapper.findTag("other"));
    }
  }
*/
}
