package com.np.urlShortener.controller;

import com.np.urlShortener.dao.URLGenerationDao;
import com.np.urlShortener.model.AnalyticsResponse;
import com.np.urlShortener.model.URLMappings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class URLAnalyticsController {

    URLGenerationDao dao;

    public URLAnalyticsController(URLGenerationDao dao) {
        this.dao = dao;
    }

    @GetMapping("/api/url/{shortCode}/analytics")
    ResponseEntity<AnalyticsResponse> getAnalytics(@PathVariable("shortCode") String shortCode){
        URLMappings mappings = dao.findByShortCode(shortCode);
        if (mappings == null){
            return ResponseEntity.notFound().build(); // 404
        }
        AnalyticsResponse response = new AnalyticsResponse(mappings.getClicks());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
