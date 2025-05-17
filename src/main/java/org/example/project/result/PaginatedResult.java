package org.example.project.result;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedResult<T> {
    private List<T> data;
    private int page;
    private int perPage;
    private long total;
    private int lastPage;

    public PaginatedResult(List<T> data, int page, int perPage, long total) {
        this.data = data;
        this.page = page;
        this.perPage = perPage;
        this.total = total;
        this.lastPage = total > 0 ? (int) Math.ceil((double) total / perPage) : 1;
    }
}
