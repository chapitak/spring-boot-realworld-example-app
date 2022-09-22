package io.spring.core.articlehistory;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleHistoryRepository extends JpaRepository<ArticleHistory, Long> {
  int countByUserId(String id);

  List<ArticleHistory> findByUserIdOrderByIdDesc(String useId, Pageable page);

  Optional<ArticleHistory> findByIdAndUserId(Long id, String userId);
}
