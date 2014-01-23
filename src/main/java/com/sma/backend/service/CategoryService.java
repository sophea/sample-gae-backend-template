package com.sma.backend.service;

import net.sf.mardao.core.CursorPage;

import com.sma.backend.domain.DCategory;
import com.wadpam.open.mvc.CrudService;

public interface CategoryService extends CrudService<DCategory, Long> {
    
    Iterable<DCategory> getAll();
    
    CursorPage<DCategory> queryPageByType(String type,int pageSize, String cursorKey);
    
    CursorPage<DCategory> queryPageByParent(Long parentId,int pageSize, String cursorKey);
    
    CursorPage<DCategory> queryPageWithFieldSorting(int sortBy, boolean isAscending,int pageSize, String cursorKey);
    
}
