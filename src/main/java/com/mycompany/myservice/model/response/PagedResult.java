package com.mycompany.myservice.model.response;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;

public record PagedResult<T>(
        List<T> data,
        long totalElements,
		long pageNumber,
		long totalPages,
        boolean isFirst,
        boolean isLast,
        boolean hasNext,
        boolean hasPrevious) {
    public <R> PagedResult(IPage<R> page, List<T> data) {
        this(
                data,
                page.getTotal(),
                page.getCurrent(),
                page.getPages(),
				page.getCurrent()==1,
				page.getCurrent()==page.getPages(),
				((Page)page).hasNext(),
				((Page)page).hasPrevious());
    }
}
