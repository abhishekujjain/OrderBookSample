package com.ujjain.trade.api.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

//    @ApiOperation(value = "Index page")
//    @GetMapping(value = "/")
    public String index() {
        return "Congratulations from Trade app";
    }
}
