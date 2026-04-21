package com.stocksaathi.sales.specifications;

import com.stocksaathi.sales.model.Customer;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomerSpecification {
    public static Specification<Customer> filterBy(
            String phone,
            String email,
            String name,
            UUID businessId) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("businessId"), businessId));
            predicates.add(cb.equal(root.get("isDeleted"), false));

            if (phone != null && !phone.isBlank()) {
                predicates.add(
                        cb.like(root.get("phone"), "%" + phone + "%")
                );
            }

            if (email != null && !email.isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("email")),
                                "%" + email.toLowerCase() + "%"
                        )
                );
            }

            if (name != null && !name.isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("name")),
                                "%" + name.toLowerCase() + "%"
                        )
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}