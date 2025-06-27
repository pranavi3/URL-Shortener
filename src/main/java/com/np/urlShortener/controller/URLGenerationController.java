package com.np.urlShortener.controller;

import com.np.urlShortener.model.GenerateRequest;
import com.np.urlShortener.model.GenerateResponse;
import com.np.urlShortener.service.URLGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class URLGenerationController {

    URLGenerationService service;

    @Autowired
    public URLGenerationController(URLGenerationService service) {
        this.service = service;
    }

    @PostMapping("/api/url/generate")
    ResponseEntity<GenerateResponse> generateCode(@RequestBody GenerateRequest body){
        GenerateResponse generateResponse = service.generateCode(body);
        return new ResponseEntity<>(generateResponse, HttpStatus.CREATED);
    }
}
