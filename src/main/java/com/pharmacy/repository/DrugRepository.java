package com.pharmacy.repository;

import com.pharmacy.entity.Drug;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugRepository extends JpaRepository<Drug, Long> {
    // Assessment Point 3: Sorting & Pagination via Pageable override
    Page<Drug> findAll(Pageable pageable);
}
