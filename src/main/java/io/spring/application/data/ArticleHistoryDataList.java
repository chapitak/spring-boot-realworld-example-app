package io.spring.application.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class ArticleHistoryDataList {
    @JsonProperty("articles")
    private final List<ArticleHistoryData> articleHIstoryDatas;

    @JsonProperty("articlesCount")
    private final int count;

    public ArticleHistoryDataList(List<ArticleHistoryData> articleHistoryDatas, int count) {
        this.articleHIstoryDatas = articleHistoryDatas;
        this.count = count;
    }
}
