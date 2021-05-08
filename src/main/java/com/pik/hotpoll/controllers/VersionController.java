package com.pik.hotpoll.controllers;

import com.pik.hotpoll.services.DefaultVersionService;
import com.pik.hotpoll.services.interfaces.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/version")
public class VersionController {


    private final VersionService versionService;

    @Autowired
    public VersionController(DefaultVersionService versionService){
        this.versionService = versionService;
    }

    @GetMapping("")
    public ResponseEntity<?> getVersion() {
        return ResponseEntity.ok(versionService.getVersion());
    }

}
