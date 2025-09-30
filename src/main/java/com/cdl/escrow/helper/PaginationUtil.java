/**
 * PaginationUtil.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 17/07/25
 */


package com.cdl.escrow.helper;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

public class PaginationUtil {

    public static HttpHeaders generatePaginationHttpHeaders(UriComponentsBuilder uriBuilder, Page<?> page) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", Long.toString(page.getTotalElements()));
        String baseUrl = uriBuilder.toUriString();

        int pageNumber = page.getNumber();
        int pageSize = page.getSize();

        if (pageNumber < page.getTotalPages() - 1) {
            headers.add(HttpHeaders.LINK,
                    createLink(baseUrl, pageNumber + 1, pageSize, "next"));
        }
        if (pageNumber > 0) {
            headers.add(HttpHeaders.LINK,
                    createLink(baseUrl, pageNumber - 1, pageSize, "prev"));
        }

        return headers;
    }

    private static String createLink(String baseUrl, int page, int size, String rel) {
        return "<" + baseUrl + "?page=" + page + "&size=" + size + ">; rel=\"" + rel + "\"";
    }
}

