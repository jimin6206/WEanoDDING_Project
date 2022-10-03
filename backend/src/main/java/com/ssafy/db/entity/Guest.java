package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "guest")
public class Guest {

    @Id
    int guestNum;

    int weddingNum;
    String guestName;
    String guestGroup;
    int guestMoney;
    String guestMessage;
    int guestSelect;
    String guestPhone;
}
