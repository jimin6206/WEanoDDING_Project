package com.ssafy.api.response;


import com.ssafy.db.entity.Hall;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@ApiModel("HallResponse")
public class HallRes {

    @ApiModelProperty(name="업체가 갖고 있는 홀", example="1홀")
    String hallName;

    public static HallRes of(Hall hall) {
        HallRes res = new HallRes();
        res.setHallName(hall.getHallName());

        return res;
    }
}