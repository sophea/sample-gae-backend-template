package com.sma.backend.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.sma.backend.domain.DCategory;
import com.sma.backend.json.JCategory;
import com.sma.backend.service.CategoryService;
import com.wadpam.docrest.domain.RestCode;
import com.wadpam.docrest.domain.RestReturn;
import com.wadpam.open.json.JCursorPage;
import com.wadpam.open.mvc.CrudController;
import com.wadpam.open.mvc.CrudListener;

/**
 * 
 * @author sophea <a href='mailto:sm@goldengekko.com'> sophea </a>
 * @version $id$ - $Revision$
 * @date 2012
 */
@RestReturn(value = JCategory.class)
@Controller
@RequestMapping(value = "{domain}/category")
public class CategoryController extends CrudController<JCategory, DCategory, Long, CategoryService> {

    public CategoryController() {
        super(JCategory.class);
    }

    /**
     * return all Categories support Header 
     * 
     * @return Iterable<JCategory>
     */
    @RestReturn(value = Iterable.class, entity = JCategory.class, code = {@RestCode(code = 200, message = "OK", description = "categories successfully retrieved")})
    @RequestMapping(value = "v10/all", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<JCategory> getAll(HttpServletRequest request, WebRequest webRequest,
            @PathVariable String domain) {

        Iterable<JCategory> categories = convert(service.getAll());
        JCursorPage<JCategory> jCursorPage = new JCursorPage<JCategory>();
        jCursorPage.setItems((List<JCategory>) categories);
        postService(request, domain, CrudListener.GET_PAGE, jCursorPage, null, null);

        return jCursorPage.getItems();
    }

    /**
     * return categories filtering by type ( Brand, Offer, Store ,....)
     * 
     * @param type
     *            - string it can be Brand, Offer, Store ...
     * @param pageSize
     *            default is 10
     * @param cursorKey
     *            null to get first page
     * 
     * @return JCursorPage<JCategory>
     */
    @RestReturn(value = JCursorPage.class, entity = JCursorPage.class, code = {
            @RestCode(code = 200, message = "OK", description = "categories successfully retrieved"),
            @RestCode(code = 422, message = "Application Error", description = "The request could not be processed"),
            @RestCode(code = 500, message = "Internal Server Error", description = "Internal Server Error")})
    @RequestMapping(value = "v10", method = RequestMethod.GET, params = "type")
    @ResponseBody
    public JCursorPage<JCategory> getCategoriesByType(@RequestParam String type, @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String cursorKey) {
        return convertPage(service.queryPageByType(type, pageSize, cursorKey));
    }

    /**
     * return categories filtering by parent id
     * 
     * @param parentId
     *            - Long category id
     * @param pageSize
     *            default is 10
     * @param cursorKey
     *            null to get first page
     * @return JCursorPage of JCategory
     */
    @RestReturn(value = JCursorPage.class, entity = JCursorPage.class, code = {
            @RestCode(code = 200, message = "OK", description = "categories successfully retrieved"),
            @RestCode(code = 422, message = "Application Error", description = "The request could not be processed"),
            @RestCode(code = 500, message = "Internal Server Error", description = "Internal Server Error")})
    @RequestMapping(value = "v10", method = RequestMethod.GET, params = "parentId")
    @ResponseBody
    public JCursorPage<JCategory> getCategoriesByParents(@RequestParam Long parentId,
            @RequestParam(defaultValue = "10") int pageSize, @RequestParam(required = false) String cursorKey) {

        return convertPage(service.queryPageByParent(parentId, pageSize, cursorKey));
    }

    /**
     * Get categories with page with sorting
     * 
     * @param sortBy
     *            - 1 - name, 2 updated date
     * @param isAscending
     *            - sorting default true
     * @param pageSize
     *            - number of records for a page default is 10
     * @param cursorKey
     *            - null to get first page
     * @return a page of entities
     */
    @RestReturn(value = JCursorPage.class, entity = JCursorPage.class, code = {
            @RestCode(code = 200, description = "should return jcursorpage of jCategory back", message = "ok"),
            @RestCode(code = 400, description = "Bad request", message = "Bad request")})
    @RequestMapping(value = "v10", method = RequestMethod.GET, params = {"sortBy"})
    public ResponseEntity<JCursorPage<JCategory>> getPageByFields(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "sortBy", required = true) Integer sortBy,
            @RequestParam(value = "isAscending", defaultValue = "true") boolean isAscending,
            @RequestParam(defaultValue = "10") int pageSize, @RequestParam(required = false) String cursorKey) {

        return new ResponseEntity<JCursorPage<JCategory>>(convertPage(service.queryPageWithFieldSorting(sortBy, isAscending,
                pageSize, cursorKey)), HttpStatus.OK);
    }

    @Override
    public void convertDomain(DCategory from, JCategory to) {
        convertLongEntity(from, to);

        to.setAppArg0(from.getAppArg0());
        to.setDescription(from.getDescription());
        to.setName(from.getName());
        to.setParentId(from.getParentId());
        to.setType(from.getType());
        to.setLogoUrl(from.getLogoUrl());
        to.setLevelOrder(from.getLevelOrder());
    }

    @Override
    public void convertJson(JCategory from, DCategory to) {
        convertJLong(from, to);
        to.setAppArg0(from.getAppArg0());
        to.setDescription(from.getDescription());
        to.setName(from.getName());
        to.setParentId(from.getParentId());
        to.setType(from.getType());
        to.setLogoUrl(from.getLogoUrl());
        to.setLevelOrder(from.getLevelOrder());
    }

}
