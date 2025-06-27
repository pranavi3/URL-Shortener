package com.np.urlShortener.service;

import com.np.urlShortener.dao.URLGenerationDao;
import com.np.urlShortener.model.GenerateRequest;
import com.np.urlShortener.model.GenerateResponse;
import com.np.urlShortener.model.URLMappings;
import org.springframework.stereotype.Service;

@Service
public class URLGenerationServiceImpl implements URLGenerationService{

    Base62Encoder encoder;
    URLGenerationDao dao;
    static long generatedId;

    public URLGenerationServiceImpl(Base62Encoder encoder, URLGenerationDao dao) {
        this.encoder = encoder;
        this.dao = dao;
    }

    @Override
    public GenerateResponse generateCode(GenerateRequest body) {
        GenerateResponse response = new GenerateResponse();

        URLMappings existing = dao.findByLongURL(body.getLongURL());
        if (existing != null) {
            return new GenerateResponse(existing.getShortCode());
        }

        URLMappings temp = new URLMappings();
        temp.setLongURL(body.getLongURL());
        temp.setShortCode("temp");
        URLMappings saved = dao.save(temp);

        String shortCode = encoder.encode(saved.getId());

        while (shortCode.length() < 6) {
            shortCode += "z";
        }
        saved.setShortCode(shortCode);
        dao.save(saved);

        response.setShortCode(shortCode);
        return response;
    }

}
