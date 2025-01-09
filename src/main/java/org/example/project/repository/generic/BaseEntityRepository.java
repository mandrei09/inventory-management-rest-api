package org.example.project.repository.generic;

import org.example.project.model.generic.BaseEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@NoRepositoryBean
public interface BaseEntityRepository<T extends BaseEntity> extends JpaRepository<T, Integer>, JpaSpecificationExecutor<T> {
    Stream<T> findAllByIsDeletedFalse();

    T findById(Long id);

    Integer findAllByIsDeletedTrueCount();

    Integer deleteAllByIsDeletedTrueAndCreatedAtBefore(Date date);
}

