package com.zhu.ticketgrabbing.entity;

import lombok.Data;

@Data
public class QueryDto {

    private String from;
    private String to;
    private String date;
    private String time;

}
