package com.sma.backend.config;
import java.util.Collection;
import java.util.Map;

import com.wadpam.open.security.RolesInterceptor;
import com.wadpam.open.security.SecurityConfig;
import com.wadpam.open.security.SecurityDetailsService;
import com.wadpam.open.security.SecurityInterceptor;
/**
 *
 */
public class UriSecurityConfig implements SecurityConfig {
    private SecurityInterceptor basicInterceptor;
    private SecurityInterceptor oauth2Interceptor;
    private RolesInterceptor rolesInterceptor;
    
    public static final String ROLE_BACKOFFICE_ADMIN = "ROLE_BACKOFFICE_ADMIN";

    public void init() {
        //common whitelist
        Collection<Map.Entry<String, Collection<String>>> commonWhitelist =
            WHITELIST_BUILDER
            .with("\\A/api/endpoints\\z", GET)
            .add("\\A/api", GET)
            .build();
        
        // whitelist for basic authentication
        Collection<Map.Entry<String, Collection<String>>> basicWhitelist =
                WHITELIST_BUILDER.with(commonWhitelist)
                .build();
        basicInterceptor.setWhitelistedMethods(basicWhitelist);
       
        // similar for oauth2Whitelist
       Collection<Map.Entry<String, Collection<String>>> oauth2Whitelist = 
           
           WHITELIST_BUILDER
           .with(commonWhitelist)           
           .add("\\A/api/[^/]+/federated/v", GET, POST) //register token
           .add("\\A/api/", GET, POST) //register token
           .build();
       
       oauth2Interceptor.setWhitelistedMethods(oauth2Whitelist);
       
        // roles and rules for methods
       Collection<Map.Entry<String, Collection<String>>> ruledMethods =
                WHITELIST_BUILDER
                .with("\\A[^:]+:/api/[^/]+/_admin/",ANONYMOUS, SecurityDetailsService.ROLE_CONTAINER_ADMIN)
                .add("DELETE:/api/[^/]+/federated/v",USER)
                .add("(POST|GET):/api/[^/]+/federated/v", USER, APPLICATION)
                // Endpoints open up for anyone
                .add("(POST|GET):/api/endpoints\\z", ANONYMOUS, USER, APPLICATION, ADMIN, SecurityDetailsService.ROLE_CONTAINER_ADMIN)
                .add("GET:/api/[^/]+/user", USER, APPLICATION, ADMIN, ROLE_BACKOFFICE_ADMIN)
                // and the catch-all ROLE_ADMIN:
                .add("(POST|GET):/api/", ADMIN,SecurityDetailsService.ROLE_CONTAINER_ADMIN,ANONYMOUS,USER,APPLICATION)
                .build();
        rolesInterceptor.setRuledMethods(ruledMethods);
    }

    public void setBasicInterceptor(SecurityInterceptor basicInterceptor) {
        this.basicInterceptor = basicInterceptor;
    }

    public void setOauth2Interceptor(SecurityInterceptor oauth2Interceptor) {
        this.oauth2Interceptor = oauth2Interceptor;
    }

    public void setRolesInterceptor(RolesInterceptor rolesInterceptor) {
        this.rolesInterceptor = rolesInterceptor;
    }
}
