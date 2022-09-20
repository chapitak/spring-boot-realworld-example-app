package io.spring.application.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.spring.application.DateTimeCursor;
import io.spring.core.articlehistory.ArticleHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleHistoryData implements io.spring.application.Node {
  private Long id;
  private String revType;
  private DateTime revisedAt;
  private String userId;
  private String articleId;
  private String slug;
  private String title;
  private String description;
  private String body;
  private DateTime createdAt;
  private DateTime updatedAt;

  @Override
  public DateTimeCursor getCursor() {
    return new DateTimeCursor(updatedAt);
  }
}
