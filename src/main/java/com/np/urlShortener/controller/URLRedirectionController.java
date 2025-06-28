package com.np.urlShortener.controller;

import com.np.urlShortener.dao.URLGenerationDao;
import com.np.urlShortener.model.URLMappings;
import com.np.urlShortener.service.URLRedirectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class URLRedirectionController {

    URLRedirectionService service;

    public URLRedirectionController(URLRedirectionService service) {
        this.service = service;
    }

    @GetMapping("/api/url/{shortCode}")
    ResponseEntity<Void> doRedirect(@PathVariable("shortCode") String shortCode){

        URLMappings match = service.findMapping(shortCode);

        if (match == null) {
            return ResponseEntity.notFound().build(); // 404
        }


        return ResponseEntity.status(HttpStatus.FOUND)  // 302
                .header("Location", match.getLongURL())
                .build();
    }

}
