package com.np.urlShortener.service;

import com.np.urlShortener.model.GenerateRequest;
import com.np.urlShortener.model.GenerateResponse;

public interface URLGenerationService {

    GenerateResponse generateCode(GenerateRequest body);
//    Long getId(String longURL);
}
