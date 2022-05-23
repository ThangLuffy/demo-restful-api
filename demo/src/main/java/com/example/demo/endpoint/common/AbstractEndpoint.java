package com.example.demo.endpoint.common;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.List;

@Slf4j
public class AbstractEndpoint {
    public static final String HEADER_PAGE_COUNT = "X-Page-Count";
    public static final String HEADER_PAGE_NUMBER = "X-Page-Number";
    public static final String HEADER_PAGE_SIZE = "X-Page-Size";
    public static final String HEADER_TOTAL_COUNT = "X-Total-Count";

    private <T extends Serializable> HttpHeaders buildPagingHeaders(Page<T> page) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HEADER_PAGE_COUNT, String.valueOf(page.getTotalPages()));
        headers.set(HEADER_PAGE_NUMBER, String.valueOf(page.getPageable().getPageNumber()));
        headers.set(HEADER_PAGE_SIZE, String.valueOf(page.getSize()));
        headers.set(HEADER_TOTAL_COUNT, String.valueOf(page.getTotalElements()));
        return headers;
    }

    public <T extends Serializable> ResponseEntity<List<T>> buildPagingResponse(@NonNull Page<T> page) {
        return ResponseEntity.ok().headers(buildPagingHeaders(page)).body(page.getContent());
    }
}
