package com.ssafy.api.service;

import com.ssafy.api.request.CompanyRegisterPostReq;
import com.ssafy.api.request.CompanyUpdatePostReq;
import com.ssafy.db.entity.Company;


public interface CertifyService {

    String certifyLogin(String imp_uid);

}
