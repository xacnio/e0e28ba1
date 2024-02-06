package com.example.amdscs.dto.responses;

import java.util.List;

public class PageRes<T> {
    public List<T> data;
    public long totalElements;
    public int totalPages;
    public boolean hasNext;
    public boolean hasPrevious;
}
