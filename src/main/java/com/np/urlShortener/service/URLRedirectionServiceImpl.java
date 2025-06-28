package com.np.urlShortener.service;

import com.np.urlShortener.dao.URLGenerationDao;
import com.np.urlShortener.model.URLMappings;
import org.springframework.stereotype.Service;

@Service
public class URLRedirectionServiceImpl implements URLRedirectionService{

    URLGenerationDao dao;

    public URLRedirectionServiceImpl(URLGenerationDao dao) {
        this.dao = dao;
    }

    @Override
    public URLMappings findMapping(String shortCode) {
        URLMappings mappings = dao.findByShortCode(shortCode);
        if(mappings != null){
            mappings.setClicks(mappings.getClicks()+1);
            dao.save(mappings);
        }
        return mappings;
    }
}
