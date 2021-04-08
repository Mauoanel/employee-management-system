package com.xib.assessment.repository;

import com.xib.assessment.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    boolean existsByNameIgnoreCase(String name);

    List<Team> findAllByAgentsNullAndManagersNull();
}
