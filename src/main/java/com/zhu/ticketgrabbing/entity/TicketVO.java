package com.zhu.ticketgrabbing.entity;

import lombok.Data;

@Data
public class TicketVO {

    /**
     * 车次
     */
    private String trainNumber;
    /**
     * 出发时间
     */
    private String departureTime;
    /**
     * 到达时间
     */
    private String arrivalTime;
    /**
     * 出发站
     */
    private String departureStation;
    /**
     * 到达站
     */
    private String arrivalStation;
    /**
     * 历时
     */
    private String duration;
    /**
     * 价格
     */
    private String price;

    /**
     * 始发站
     */
    private String extreInfo;


}
