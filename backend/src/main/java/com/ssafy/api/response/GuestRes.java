package com.ssafy.api.response;

import com.ssafy.db.entity.Guest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("GuestResponse")
public class GuestRes {
    @ApiModelProperty(name="사람이름", example="최년우")
    String guestName;
    @ApiModelProperty(name="사람소속", example="싸피7기")
    String guestGroup;
    @ApiModelProperty(name="축의금", example="50000")
    int guestMoney;
    @ApiModelProperty(name="방명록", example="축하해")
    String guestMessage;
    @ApiModelProperty(name="신부측 or 신랑측", example="1")
    int guestSelect;
    @ApiModelProperty(name="전화번호", example="010-1111-1111")
    String guestPhone;



    public static GuestRes of(Guest guest) {
        GuestRes res = new GuestRes();
        res.setGuestName(guest.getGuestName());
        res.setGuestGroup(guest.getGuestGroup());
        res.setGuestMoney(guest.getGuestMoney());
        res.setGuestMessage(guest.getGuestMessage());
        res.setGuestSelect(guest.getGuestSelect());
        res.setGuestPhone(guest.getGuestPhone());
        return res;
    }
}
