package com.ssafy.api.response;

import com.ssafy.db.entity.Company;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원 본인 정보 조회 API ([GET] /api/v1/users/me) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("CompanyResponse")
public class CompanyRes {
	@ApiModelProperty(name="업체 ID", example="ssafy123")
	String companyId;
	@ApiModelProperty(name="업체 Password", example="company_password")
	String companyPassword;
	@ApiModelProperty(name="업체 Name", example="company_name")
	String companyName;
	@ApiModelProperty(name="업체 telnum", example="company_telnum")
	String companyTelnum;
	
	public static CompanyRes of(Company company) {
		CompanyRes res = new CompanyRes();
		res.setCompanyId(company.getCompanyId());
		res.setCompanyName(company.getCompanyName());
		res.setCompanyPassword(company.getCompanyPassword());
		res.setCompanyTelnum(company.getCompanyTelnum());
		return res;
	}
}
