package com.ssafy.api.service;

import com.ssafy.api.request.CompanyRegisterPostReq;
import com.ssafy.api.request.CompanyUpdatePostReq;
import com.ssafy.db.entity.Company;


public interface CompanyService {

    Company createCompany(CompanyRegisterPostReq companyRegisterInfo);

    void deleteCompany(String companyId);


    Company updateCompany(String companyId, CompanyUpdatePostReq companyUpdateInfo);

    Company selectCompany(String companyId);
}
