package vn.eren.authenticationstructuresp.config.specification;


import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import vn.eren.authenticationstructuresp.common.constant.DateTimeConstant;
import vn.eren.authenticationstructuresp.common.util.DateTimeUtil;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public abstract class BaseSpecification<T> {
    protected final List<Specification<T>> specifications = new ArrayList<>();

    /**
     * Aggregate method that handles all types of conditions
     *
     * @param fieldName Name of the field to add the condition
     * @param operation Type of operator: EQUAL, NOT_EQUAL, LIKE, IN, NULL, NOT_NULL,
     *                  GREATER_THAN, LESS_THAN, BETWEEN, etc.
     * @param value Value or list of values
     * @param additionalValue Additional value for special conditions (like BETWEEN)
     * @param dateFormat Date format for Date/Instant fields
     * @return this BaseSpecification instance (to support method chaining)
     */
    public BaseSpecification<T> with(String fieldName, String operation, T value, T additionalValue, String dateFormat) {
        if (fieldName == null || operation == null) {
            return this;
        }

        String op = operation.toUpperCase();
        String format = dateFormat != null ? dateFormat : DateTimeConstant.yyyy_MM_dd_HH_mm_ss_;

        switch (op) {
            // Example: spec.with("fullName", "EQUAL", "abc, null, null);
            case "EQUAL":
                if (!ObjectUtils.isEmpty(value)) {
                    specifications.add((root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(root.get(fieldName), value));
                }
                break;
            case "NOT_EQUAL":
                if (!ObjectUtils.isEmpty(value)) {
                    specifications.add((root, query, criteriaBuilder) ->
                            criteriaBuilder.notEqual(root.get(fieldName), value));
                }
                break;

            case "LIKE":
                if (!ObjectUtils.isEmpty(value) && value instanceof String) {
                    specifications.add((root, query, criteriaBuilder) ->
                            criteriaBuilder.like(
                                    criteriaBuilder.lower(root.get(fieldName)),
                                    "%" + ((String) value).toLowerCase() + "%"));
                }
                break;

            case "NOT_LIKE":
                if (!ObjectUtils.isEmpty(value) && value instanceof String) {
                    specifications.add((root, query, criteriaBuilder) ->
                            criteriaBuilder.notLike(
                                    criteriaBuilder.lower(root.get(fieldName)),
                                    "%" + ((String) value).toLowerCase() + "%"));
                }
                break;

            case "STARTS_WITH":
                if (!ObjectUtils.isEmpty(value) && value instanceof String) {
                    specifications.add((root, query, criteriaBuilder) ->
                            criteriaBuilder.like(criteriaBuilder.lower(root.get(fieldName)),
                                    ((String) value).toLowerCase() + "%"));
                }
                break;

            case "ENDS_WITH":
                if (!ObjectUtils.isEmpty(value) && value instanceof String) {
                    specifications.add((root, query, criteriaBuilder) ->
                            criteriaBuilder.like(criteriaBuilder.lower(root.get(fieldName)),
                                    "%" + ((String) value).toLowerCase()));
                }
                break;

            case "IN":
                if (value instanceof Collection && !CollectionUtils.isEmpty((Collection<?>) value)) {
                    specifications.add((root, query, criteriaBuilder) ->
                            root.get(fieldName).in((Collection<?>) value));
                }
                break;

            case "NULL":
                specifications.add((root, query, criteriaBuilder) ->
                        criteriaBuilder.isNull(root.get(fieldName)));
                break;

            case "NOT_NULL":
                specifications.add((root, query, criteriaBuilder) ->
                        criteriaBuilder.isNotNull(root.get(fieldName)));
                break;

            case "GREATER_THAN":
                if (!ObjectUtils.isEmpty(value)) {
                    if (value instanceof String && isDateField(fieldName)) {
                        Instant date = DateTimeUtil.convertStringToInstant((String) value, format);
                        specifications.add((root, query, criteriaBuilder) ->
                                criteriaBuilder.greaterThanOrEqualTo(root.get(fieldName), date));
                    } else if (value instanceof Comparable) {
                        specifications.add((root, query, criteriaBuilder) ->
                                criteriaBuilder.greaterThanOrEqualTo(root.get(fieldName), (Comparable) value));
                    }
                }
                break;

            case "LESS_THAN":
                if (!ObjectUtils.isEmpty(value)) {
                    if (value instanceof String && isDateField(fieldName)) {
                        Instant date = DateTimeUtil.convertStringToInstant((String) value, format);
                        specifications.add((root, query, criteriaBuilder) ->
                                criteriaBuilder.lessThanOrEqualTo(root.get(fieldName), date));
                    } else if (value instanceof Comparable) {
                        specifications.add((root, query, criteriaBuilder) ->
                                criteriaBuilder.lessThanOrEqualTo(root.get(fieldName), (Comparable) value));
                    }
                }
                break;

            case "BETWEEN":
                if (!ObjectUtils.isEmpty(value) && !ObjectUtils.isEmpty(additionalValue)) {
                    if (value instanceof String && additionalValue instanceof String && isDateField(fieldName)) {
                        Instant startDate = DateTimeUtil.convertStringToInstant((String) value, format);
                        Instant endDate = DateTimeUtil.convertStringToInstant((String) additionalValue, format);
                        specifications.add((root, query, criteriaBuilder) ->
                                criteriaBuilder.between(root.get(fieldName), startDate, endDate));
                    } else if (value instanceof Comparable && additionalValue instanceof Comparable) {
                        if (value.getClass().equals(additionalValue.getClass())) {
                            specifications.add((root, query, criteriaBuilder) ->
                                    criteriaBuilder.between(root.get(fieldName),
                                            (Comparable) value, (Comparable) additionalValue));
                        } else {
                            throw new IllegalArgumentException("Mismatched types for BETWEEN condition: " +
                                    value.getClass().getSimpleName() + " and " + additionalValue.getClass().getSimpleName());
                        }
                    }
                }
                break;

            // spec.with("roles.id", "JOIN_IN", request.getRoleIds(), "id", null);
            case "JOIN_EQUAL":
                if (!ObjectUtils.isEmpty(value) && !ObjectUtils.isEmpty(additionalValue)) {
                    specifications.add((root, query, criteriaBuilder) -> {
                        String[] joinPath = fieldName.split("\\.");
                        String joinField = joinPath[0];
                        String targetField = joinPath.length > 1 ? joinPath[1] : additionalValue.toString();
                        Join<T, ?> join = root.join(joinField, JoinType.INNER);
                        return criteriaBuilder.equal(join.get(targetField), value);
                    });
                }
                break;

            // spec.with("roles.permissions", "JOIN_EQUAL", request.getPermissionCode(), "code", null);
            case "JOIN_IN":
                if (value instanceof Collection && !CollectionUtils.isEmpty((Collection<?>) value)
                        && !ObjectUtils.isEmpty(additionalValue)) {
                    specifications.add((root, query, criteriaBuilder) -> {
                        String[] joinPath = fieldName.split("\\.");
                        String joinField = joinPath[0];
                        String targetField = joinPath.length > 1 ? joinPath[1] : additionalValue.toString();
                        Join<T, ?> join = root.join(joinField, JoinType.INNER);
                        return join.get(targetField).in((Collection<?>) value);
                    });
                }
                break;

            default:
                // Do nothing if the operation is invalid
                break;
        }
        return this;
    }

    protected boolean isDateField(String fieldName) {
        return fieldName.contains("Date") || fieldName.contains("Time") ||
                fieldName.endsWith("At") || fieldName.contains("Instant");
    }



    public Specification<T> build() {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.and(specifications.stream().filter(Objects::nonNull)
                .map(s -> s.toPredicate(root, query, criteriaBuilder)).toArray(Predicate[]::new));
    }
}
