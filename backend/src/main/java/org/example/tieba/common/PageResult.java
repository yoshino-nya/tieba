package org.example.tieba.common;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {
    private int total;
    private int pages;
    private int current;
    private int size;
    private List<T> records;

    public PageResult() {

    }
    public PageResult(int total, int current, int size, List<T> records) {
        this.total = total;
        this.current = current;
        this.size = size;
        this.records = records;
        this.pages = (this.total + this.size - 1) / size;
    }
}
