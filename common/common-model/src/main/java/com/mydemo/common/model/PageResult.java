package com.mydemo.common.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页返回体
 */
@Data
public class PageResult<T> implements Serializable {

    private long total;
    private int page;
    private int size;
    private List<T> records;

    public static <T> PageResult<T> of(List<T> records, long total, int page, int size) {
        PageResult<T> result = new PageResult<>();
        result.setRecords(records);
        result.setTotal(total);
        result.setPage(page);
        result.setSize(size);
        return result;
    }

    public static <T> PageResult<T> empty(int page, int size) {
        return of(Collections.emptyList(), 0, page, size);
    }
}
