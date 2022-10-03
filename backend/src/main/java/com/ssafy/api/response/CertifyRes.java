package com.ssafy.api.response;


import com.ssafy.db.entity.Hall;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@ApiModel("CertifyResponse")
public class CertifyRes {

    @ApiModelProperty(name="certificationsInfo")
    String certificationsInfo;

    public static CertifyRes of(String certificationsInfo) {
        CertifyRes res = new CertifyRes();
        res.setCertificationsInfo(certificationsInfo);

        return res;
    }
}