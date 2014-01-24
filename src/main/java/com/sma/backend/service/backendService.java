/*
 * INSERT COPYRIGHT HERE
 */

package com.sma.backend.service;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.appengine.api.NamespaceManager;
import com.wadpam.oauth2.domain.DFactory;
import com.wadpam.oauth2.service.FactoryService;
import com.wadpam.oauth2.service.OAuth2Service;
import com.wadpam.open.domain.DAppDomain;
import com.wadpam.open.service.DomainService;

/**
 *
 * @author sophea
 */
public class backendService {
    public static final String SAMPLE = "sample";

    @Autowired
    private DomainService domainService;
    
    @Autowired
    private FactoryService factoryService;
    
    public void init() throws IOException, ServletException {
        
        // create a tutorial app domain:
        NamespaceManager.set(null);
        DAppDomain tutorial = new DAppDomain();
        tutorial.setId(SAMPLE);
        tutorial.setUsername(SAMPLE);
        tutorial.setPassword(SAMPLE);
        domainService.create(tutorial);
        
        NamespaceManager.set(SAMPLE);
        DFactory facebook = new DFactory();
        facebook.setId(OAuth2Service.PROVIDER_ID_FACEBOOK);
        facebook.setClientId("255653361131262");
        facebook.setClientSecret("Not Needed");
        factoryService.create(facebook);
        
        NamespaceManager.set(null);
    }
    
}
