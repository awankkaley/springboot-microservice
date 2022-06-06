package com.example.apigateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class GatewayController {

    @GetMapping("/fallback")
    public String fallback() {
        return "Server sedang sibuk, permintaan mungkin terjadi";
    }
}
