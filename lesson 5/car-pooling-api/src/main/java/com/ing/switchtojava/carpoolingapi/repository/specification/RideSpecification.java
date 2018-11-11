package com.ing.switchtojava.carpoolingapi.repository.specification;

import com.ing.switchtojava.carpoolingapi.domain.Ride;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;

public class RideSpecification implements Specification<Ride> {

    private SearchCriteria criteria;

    public RideSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Ride> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return criteriaBuilder.like(root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {
                return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        if (criteria.getOperation().equalsIgnoreCase("before")) {
            return criteriaBuilder.lessThan(root.<LocalDateTime>get(criteria.getKey()), LocalDateTime.parse(criteria.getValue().toString()));
        } else if (criteria.getOperation().equalsIgnoreCase("after")) {
            return criteriaBuilder.greaterThan(root.<LocalDateTime>get(criteria.getKey()), LocalDateTime.parse(criteria.getValue().toString()));
        }

        return null;
    }
}
