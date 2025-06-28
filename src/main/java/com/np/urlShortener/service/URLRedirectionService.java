package com.np.urlShortener.service;

import com.np.urlShortener.model.URLMappings;

public interface URLRedirectionService {
    URLMappings findMapping(String shortCode);
}
