package com.storage.web;

import com.storage.model.dto.Dto;
import com.storage.model.dto.PostSouvenirDto;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pagination {

    private Pagination() {

    }
    private static final int SIZE = 8;
    public static Map<String, Object> getParams(
            HttpServletRequest req, String key, List<? extends Dto> dtoList) {
        String pageParam = req.getParameter("page");
        int page = pageParam != null ? Integer.parseInt(pageParam) : 0;

        int size = dtoList.size();

        int totalPages = (int) Math.ceil((double) size / SIZE);
        page = Math.min(totalPages, Math.max(1, page));
        int offset = Math.abs(Math.min((page - 1) * SIZE, size));
        int toIndex = Math.min(offset + SIZE, size);

        Map<String, Object> params = new HashMap<>();

        params.put(key, offset <= toIndex
                ? dtoList.subList(offset, toIndex)
                : new ArrayList<PostSouvenirDto>());
        params.put("totalPages", totalPages);
        params.put("currentPage", page);
        return params;
    }
}
