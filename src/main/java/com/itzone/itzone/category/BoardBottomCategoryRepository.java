package com.itzone.itzone.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardBottomCategoryRepository extends JpaRepository<BoardBottomCategory, Long> {
}
