package org.example.project.utils;

import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

public class GenericSpecification<T> {

    public static <T> Specification<T> getQueryableAnd(Map<String, String> filters) {
        return (root, query, criteriaBuilder) -> {
            if (filters == null || filters.isEmpty()) {
                return criteriaBuilder.conjunction(); 
            }

            return filters.entrySet().stream()
                    .map(entry -> criteriaBuilder.equal(root.get(entry.getKey()), entry.getValue()))
                    .reduce(criteriaBuilder::and)
                    .orElse(criteriaBuilder.conjunction());
        };
    }
}
