package com.sma.backend.json;

import java.util.Map;

import com.wadpam.open.json.JBaseObject;

/**
 * @author sophea
 */
public class JCategory extends JBaseObject {

    /** a one-liner category name  */
    private String              name;

    /** A longer description of this category */
    private String              description;

    /** key to the category asset */
    private String              appArg0;


    /** If this is a sub-category, the id of its parent */
    private Long                parentId;
    /** logo Url */
    private String              logoUrl;
    
    private Long levelOrder;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    /** type of Category, e.g. DBrand */
    private String type;

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Long getLevelOrder() {
        return levelOrder;
    }

    public void setLevelOrder(Long levelOrder) {
        this.levelOrder = levelOrder;
    }

}
