package com.lizhi.common.global;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lizhi
 * @date: 2020/10/31 16:24
 */
public class PageResponseDataVo<T> extends BaseResponseVo {
    /**
     * 当前页
     */
    private Integer pageNum;
    /**
     * 每页记录数
     */
    private Integer pageSize;
    /**
     * 总记录数
     */
    private Long total;
    /**
     * 当前页记录
     */
    private Integer pageCount;
    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 数据起始位置
     */
    private Integer start;

    /**
     * 数据
     */
    private List<T> list=new ArrayList<>();
}
