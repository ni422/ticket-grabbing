package com.zhu.ticketgrabbing.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class QueryResVO<T> {

    private T flag;

    private Map<T,T> map;

    private List<T> result;

}
