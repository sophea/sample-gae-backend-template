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
 * @author sosandstrom
 */
public class TutorialService {
    public static final String TUTORIAL = "tutorial";

    @Autowired
    private DomainService domainService;
    
    @Autowired
    private FactoryService factoryService;
    
    public void init() throws IOException, ServletException {
        
        // create a tutorial app domain:
        NamespaceManager.set(null);
        DAppDomain tutorial = new DAppDomain();
        tutorial.setId(TUTORIAL);
        tutorial.setUsername(TUTORIAL);
        tutorial.setPassword(TUTORIAL);
        domainService.create(tutorial);
        
        NamespaceManager.set(TUTORIAL);
        DFactory facebook = new DFactory();
        facebook.setId(OAuth2Service.PROVIDER_ID_FACEBOOK);
        facebook.setClientId("255653361131262");
        facebook.setClientSecret("Not Needed");
        factoryService.create(facebook);
        
        NamespaceManager.set(null);
    }
    
}
