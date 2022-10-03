package com.ssafy.common.auth;

import com.ssafy.api.service.CompanyService;
import com.ssafy.db.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


/**
 * 현재 액세스 토큰으로 부터 인증된 유저의 상세정보(활성화 여부, 만료, 롤 등) 관련 서비스 정의.
 */
@Component
public class SsafyCompanyDetailService implements UserDetailsService{
	@Autowired
	CompanyService companyService;
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    		Company company = companyService.selectCompany(username);
    		if(company != null) {
    			SsafyCompanyDetails companyDetails = new SsafyCompanyDetails(company);
    			return companyDetails;
    		}
    		return null;
    }
}
