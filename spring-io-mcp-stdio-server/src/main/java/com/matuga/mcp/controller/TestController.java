package com.matuga.mcp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping(path="/test")
    public String testMcpServer(){
        return "MCP-SERVER-TEST";
    }
}
