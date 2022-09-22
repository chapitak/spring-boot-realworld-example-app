package io.spring.api;

import io.spring.application.ArticleQueryService;
import io.spring.application.ChunkRequest;
import io.spring.application.data.ArticleHistoryData;
import io.spring.core.user.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/article-histories")
@AllArgsConstructor
public class ArticleHistoryAPI {
  private ArticleQueryService articleQueryService;

  @GetMapping
  public ResponseEntity getMyArticleHistories(
      @RequestParam(value = "offset", defaultValue = "0") int offset,
      @RequestParam(value = "limit", defaultValue = "10") int limit,
      @AuthenticationPrincipal User user) {
    Pageable pageable = new ChunkRequest(offset, limit, Sort.by(Sort.Direction.DESC, "id"));
    return ResponseEntity.ok(articleQueryService.findArticleHistories(user, pageable));
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<?> articleHistory(
      @PathVariable("id") String id, @AuthenticationPrincipal User user) {
    return ResponseEntity.ok(
        articleHistoryResponse(
            articleQueryService.findArticleHistory(user, Long.parseLong(id)).get().toData()));
  }

  private Map<String, Object> articleHistoryResponse(ArticleHistoryData articleHistoryData) {
    return new HashMap<String, Object>() {
      {
        put("article", articleHistoryData);
      }
    };
  }
}
