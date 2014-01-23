package com.sma.backend.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import net.sf.mardao.core.domain.AbstractLongEntity;

/**
 *
 * @author sophea
 */
@Entity
public class DCategory extends AbstractLongEntity {

    /** a one-liner category name*/
    @Basic
    private String name;
    
    /** A longer description of this category*/
    @Basic 
    private String description;
    
    /** key to the category asset generate purpose*/
    @Basic
    private String appArg0;
    
    /** type of Category */
    @Basic
    private String type;
    
    /** If this is a sub-category, the id of its parent */
    @Basic
    private Long parentId;

    /** logoUrl */
    @Basic
    private String logoUrl;
    
    @Basic
    private Long levelOrder;
    
    /** The external (customer) Id export/import */
    @Basic
    private String             extId;
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppArg0() {
        return appArg0;
    }

    public void setAppArg0(String appArg0) {
        this.appArg0 = appArg0;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getExtId() {
        return extId;
    }

    public void setExtId(String extId) {
        this.extId = extId;
    }

    public Long getLevelOrder() {
        return levelOrder;
    }

    public void setLevelOrder(Long levelOrder) {
        this.levelOrder = levelOrder;
    }

    
}
