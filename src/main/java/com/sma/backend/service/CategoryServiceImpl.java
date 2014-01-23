package com.sma.backend.service;

import net.sf.mardao.core.CursorPage;

import com.sma.backend.dao.DCategoryDao;
import com.sma.backend.domain.DCategory;
import com.wadpam.open.mvc.MardaoCrudService;

public class CategoryServiceImpl extends MardaoCrudService<DCategory, Long, DCategoryDao> 
        implements CategoryService {
    
    
    public Iterable<DCategory> getAll() {
        return dao.queryAll();
    }

    @Override
    public CursorPage<DCategory> queryPageByType(String type,int pageSize, String cursorKey) {
        return dao.queryPageByType(type,pageSize, cursorKey);
    }

    @Override
    public CursorPage<DCategory> queryPageByParent(Long parentId,int pageSize, String cursorKey) {

        return dao.queryPageByParentId(parentId, pageSize, cursorKey);
    }
    
    @Override
    public CursorPage<DCategory> queryPageWithFieldSorting(int sortBy, boolean isAscending, int pageSize, String cursorKey) {
        
        return dao.queryPageWithSortField(pageSize, cursorKey, sortBy, isAscending);
    }
    
}
