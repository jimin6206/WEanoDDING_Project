package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.Company;
import com.ssafy.db.entity.QCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CompanyRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QCompany qCompany = QCompany.company;

    public Optional<Company> findCompanyByCompanyId(String companyId) {
        Company company = jpaQueryFactory.select(qCompany).from(qCompany)
                .where(qCompany.companyId.eq(companyId)).fetchOne();
        if(company == null) return Optional.empty();
        return Optional.ofNullable(company);
    }
}
