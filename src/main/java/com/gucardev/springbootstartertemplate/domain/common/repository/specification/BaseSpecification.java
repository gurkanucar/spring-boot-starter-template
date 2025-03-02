package com.gucardev.springbootstartertemplate.domain.common.repository.specification;

import com.gucardev.springbootstartertemplate.domain.common.enumeration.DeletedStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BaseSpecification {

    public static <T> Specification<T> like(String fieldName, String value) {
        return (root, query, cb) -> {
            if (value == null || value.isEmpty()) return null;
            return cb.like(cb.lower(root.get(fieldName)), "%" + value.toLowerCase() + "%");
        };
    }

    public static <T> Specification<T> equals(String fieldName, Object value) {
        return (root, query, cb) -> {
            if (value == null) return null;
            return cb.equal(root.get(fieldName), value);
        };
    }

    public static <E, I> Specification<E> byId(I id) {
        return (root, query, cb) -> {
            if (id == null) return null;
            return cb.equal(root.get("id"), id);
        };
    }

    public static <T> Specification<T> byIds(List<?> ids) {
        return (root, query, cb) -> {
            if (ids == null || ids.isEmpty()) return null;
            return root.get("id").in(ids);
        };
    }

    public static <T> Specification<T> deleted(DeletedStatus deletedStatus) {
        return (root, query, cb) -> {
            if (deletedStatus == null || deletedStatus == DeletedStatus.DELETED_UNKNOWN) {
                return null; // No filtering, return all records
            }
            return deletedStatus == DeletedStatus.DELETED_TRUE
                    ? cb.isTrue(root.get("deleted"))
                    : cb.isFalse(root.get("deleted"));
        };
    }

    public static <T> Specification<T> createdBetween(LocalDate start, LocalDate end) {
        return (root, query, cb) -> {
            if (start == null || end == null) return null;
            // Convert LocalDate to LocalDateTime for proper comparison
            LocalDateTime startDateTime = start.atStartOfDay();
            LocalDateTime endDateTime = end.atTime(23, 59, 59, 999999999);
            return cb.between(root.get("createdDate"), startDateTime, endDateTime);
        };
    }
}
