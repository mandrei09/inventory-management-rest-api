package org.example.project.utils;

import org.example.project.service.generic.BaseService;
import org.hibernate.query.sqm.PathElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

public class GenericSpecification<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericSpecification.class);

    public static <T> Specification<T> getQueryableAnd(Map<String, String> filters) {
        return (root, query, criteriaBuilder) -> {
            if (filters == null || filters.isEmpty()) {
                return criteriaBuilder.isFalse(root.get("isDeleted"));
            }

            var dynamicFilters = filters.entrySet().stream()
                    .map(entry -> {
                        String key = entry.getKey();
                        String value = entry.getValue();

                        Class<?> fieldType = root.get(key).getJavaType();

                        if (Boolean.class.equals(fieldType) || boolean.class.equals(fieldType)) {
                            return criteriaBuilder.equal(root.get(key), Boolean.parseBoolean(value));
                        }
                        return criteriaBuilder.equal(root.get(key), value);
                    })
                    .reduce(criteriaBuilder::and)
                    .orElse(criteriaBuilder.conjunction());

            return criteriaBuilder.and(dynamicFilters, criteriaBuilder.isFalse(root.get("isDeleted")));
        };
    }

}
