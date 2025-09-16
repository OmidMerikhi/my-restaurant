package com.omid.auth_service;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/hello/{name}")
//    @PreAuthorize("hasAuthority('select')")
    public String test(@PathVariable("name") String name) {
        return name;
    }

    @GetMapping("/hi")
//    @PreAuthorize("hasAuthority('insert')")
    public String test2() {
        return "hi";
    }
}
