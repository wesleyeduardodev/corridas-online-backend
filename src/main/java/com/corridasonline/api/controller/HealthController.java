package com.corridasonline.api.controller;

import com.corridasonline.api.controller.document.HealthControllerDocument;
import com.corridasonline.api.util.DateTimeUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/public")
public class HealthController implements HealthControllerDocument {

    @Override
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "timestamp", DateTimeUtil.now().toString(),
                "service", "corridas-api"
        ));
    }

}
