package com.zhu.ticketgrabbing.entity;

import java.io.Serializable;

/**
 * 列车站点信息(TrainStation)实体类
 *
 * @author makejava
 * @since 2023-10-03 14:51:05
 */
public class TrainStation implements Serializable {
    private static final long serialVersionUID = 337907563267422729L;
    /**
     * id

     */
    private Long stationId;
    /**
     * 站点姓名
     */
    private String stationTitle;
    /**
     * 站点码值
     */
    private String stationCode;
    /**
     * 站点全拼
     */
    private String stationCompleteSpelling;
    /**
     * 站点简拼
     */
    private String stationSimplaSpelling;
    /**
     * 城市码值
     */
    private String stationCityCode;
    /**
     * 所属地区
     */
    private String stationCity;
    /**
     * 站点所属城市序号
     */
    private Integer stationSort;


    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public String getStationTitle() {
        return stationTitle;
    }

    public void setStationTitle(String stationTitle) {
        this.stationTitle = stationTitle;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getStationCompleteSpelling() {
        return stationCompleteSpelling;
    }

    public void setStationCompleteSpelling(String stationCompleteSpelling) {
        this.stationCompleteSpelling = stationCompleteSpelling;
    }

    public String getStationSimplaSpelling() {
        return stationSimplaSpelling;
    }

    public void setStationSimplaSpelling(String stationSimplaSpelling) {
        this.stationSimplaSpelling = stationSimplaSpelling;
    }

    public String getStationCityCode() {
        return stationCityCode;
    }

    public void setStationCityCode(String stationCityCode) {
        this.stationCityCode = stationCityCode;
    }

    public String getStationCity() {
        return stationCity;
    }

    public void setStationCity(String stationCity) {
        this.stationCity = stationCity;
    }

    public Integer getStationSort() {
        return stationSort;
    }

    public void setStationSort(Integer stationSort) {
        this.stationSort = stationSort;
    }

}

