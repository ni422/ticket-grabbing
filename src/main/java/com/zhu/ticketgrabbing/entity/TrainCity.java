package com.zhu.ticketgrabbing.entity;

import java.io.Serializable;

/**
 * 地区(TrainCity)实体类
 *
 * @author makejava
 * @since 2023-10-03 14:51:32
 */
public class TrainCity implements Serializable {
    private static final long serialVersionUID = 610416207665175762L;
    
    private Integer id;
    
    private String title;
    
    private String code;
    
    private String simple;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSimple() {
        return simple;
    }

    public void setSimple(String simple) {
        this.simple = simple;
    }

}

