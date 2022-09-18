package io.spring.core.articlehistory;

import io.spring.application.Page;
import io.spring.core.user.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleHistoryRepository extends JpaRepository<ArticleHistory, Long> {
    int countByUserId(String id);

    List<ArticleHistory> findByUserIdOrderByIdDesc(String s);
}
