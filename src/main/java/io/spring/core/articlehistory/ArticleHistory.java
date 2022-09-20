package io.spring.core.articlehistory;

import io.spring.application.data.ArticleHistoryData;
import io.spring.core.article.RevType;
import io.spring.core.article.Tag;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "articlehistories")
public class ArticleHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="rev_type")
    private int revType;
    @Column(name="revised_at", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private DateTime revisedAt;
    @Column(name="user_id")
    private String userId;
    @Column(name="article_id")
    private String articleId;
    private String slug;
    private String title;
    @Column(columnDefinition="TEXT")
    private String description;
    @Column(columnDefinition="TEXT")
    private String body;
    @ElementCollection
    @CollectionTable(name="articlehistories_tag_ids")
    private List<String> tagIds;
    @Column(name="created_at",columnDefinition="TIMESTAMP")
    private DateTime createdAt;
    @Column(name="updated_at",columnDefinition="TIMESTAMP")
    private DateTime updatedAt;

    public ArticleHistory() {

    }

    public ArticleHistory(int revType, String userId, String articleId, String slug, String title, String description
            , String body, List<Tag> tags, DateTime createdAt, DateTime updatedAt) {
        this.revType = revType;
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

    public ArticleHistoryData toData() {
        return new ArticleHistoryData(id, RevType.getName(revType), revisedAt, userId, articleId, slug, title, description, body
                , createdAt, updatedAt);

    }
}
