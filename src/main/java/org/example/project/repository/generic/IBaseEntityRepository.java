package org.example.project.repository.generic;

import org.example.project.model.embedable.InventoryAssetId;
import org.example.project.model.generic.BaseEntity;
import org.example.project.result.Result;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@NoRepositoryBean
public interface IBaseEntityRepository<T extends BaseEntity> extends JpaRepository<T, Integer>, JpaSpecificationExecutor<T> {
    List<T> findAllByIsDeletedFalse();
    List<T> findAll(Specification<T> spec);

    T findById(Long id);
    T findById(InventoryAssetId id);

    Integer deleteAllByIsDeletedTrueAndCreatedAtBefore(Date date);


}

