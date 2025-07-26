package com.example.demo.specification;

import com.example.demo.entity.Member;
import com.example.demo.models.MemberFilterModel;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class MemberSpecification {

    public static Specification<Member> build(MemberFilterModel filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getMemberId() != null && !filter.getMemberId().isEmpty()) {
                predicates.add(root.get("memberId").in(filter.getMemberId()));
            }

            if (filter.getMemberType() != null && !filter.getMemberType().isEmpty()) {
                predicates.add(root.get("memberType").in( filter.getMemberType()));
            }

            if (filter.getActiveStatus() != null) {
                predicates.add(cb.equal(root.get("activeStatus"), filter.getActiveStatus()));
            }

            if (filter.getReportingTo() != null && !filter.getReportingTo().isEmpty()) {
                predicates.add(root.get("reportingTo").in( filter.getReportingTo()));
            }

            if (filter.getName() != null && !filter.getName().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + filter.getName().toLowerCase() + "%"));
            }

            if (filter.getPhoneNo() != null ) {
                predicates.add(cb.like(root.get("phoneNo"), "%" + filter.getPhoneNo() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

