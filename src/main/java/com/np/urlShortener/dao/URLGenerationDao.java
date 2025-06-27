package com.np.urlShortener.dao;

import com.np.urlShortener.model.URLMappings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface URLGenerationDao extends JpaRepository<URLMappings, Long> {

    URLMappings findByLongURL(String longUrl);
    URLMappings save(URLMappings urlMappings);
}
