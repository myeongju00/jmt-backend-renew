package com.gdsc.jmt.global.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class PageResponse {
    private Integer totalPages;
    private Integer currentPage;
    private Long totalElements;
    private boolean isPageLast;
    private Integer size;
    private Integer numberOfElements;
    private boolean isPageFirst;
    private boolean isEmpty;

    public PageResponse(Page<?> page) {
        currentPage = page.getNumber() + 1;
        isPageLast = page.isLast();
        isPageFirst = page.isFirst();
        isEmpty = page.isEmpty();
        totalPages = page.getTotalPages();
        numberOfElements = page.getNumberOfElements();
        totalElements = page.getTotalElements();
        size = page.getSize();
    }
}
