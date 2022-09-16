package io.spring.core.articlehistory;

import io.spring.core.article.Tag;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "articlehistory")
public class ArticleHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int revType;
    private DateTime revTime;
    private String userId;
    private String articleId;
    private String slug;
    private String title;
    private String description;
    private String body;
    @ElementCollection
    @CollectionTable(name = "articlehistory_tagIds")
    private List<String> tagIds;
    private DateTime createdAt;
    private DateTime updatedAt;

    public ArticleHistory() {

    }

    public ArticleHistory(int revType, String userId, String articleId, String slug, String title, String description
            , String body, List<Tag> tags, DateTime createdAt, DateTime updatedAt) {
        this.revType = revType;
        this.revTime = DateTime.now();
        this.userId = userId;
        this.articleId = articleId;
        this.slug = slug;
        this.title = title;
        this.description = description;
        this.body = body;
        this.tagIds = tags.stream()
                .map(Tag::getId)
                .collect(Collectors.toList());
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ArticleHistory from(int revType, String id, String slug, String title, String description, String body
            , List<Tag> tags, String userId, DateTime createdAt, DateTime updatedAt) {
        return new ArticleHistory(revType, userId, id, slug, title, description, body, tags, createdAt, updatedAt);
    }
}
