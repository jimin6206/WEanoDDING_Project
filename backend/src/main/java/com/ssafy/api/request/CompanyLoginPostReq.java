package com.ssafy.api.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("CompanyLoginPostRequest")
public class CompanyLoginPostReq {

    @ApiModelProperty(name="업체 ID", example="ssafy123")
    String companyId;
    @ApiModelProperty(name="업체 Password", example="company_password")
    String companyPassword;

}
