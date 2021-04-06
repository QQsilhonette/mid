package com.tuya.hardware.symphony.dtlog.dto;

import com.alibaba.fastjson.JSONObject;
import com.tuya.hardware.symphony.dtlog.base.DTO;

import java.util.List;

/**
 * @author 一兮
 * @Date 2021/03/15
 * @Description
 */
public class EsLogPagination extends DTO {
    private Long totalCount;
    private List<JSONObject> datas;
    private Integer pageNo;

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<JSONObject> getDatas() {
        return datas;
    }

    public void setDatas(List<JSONObject> datas) {
        this.datas = datas;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }
}
