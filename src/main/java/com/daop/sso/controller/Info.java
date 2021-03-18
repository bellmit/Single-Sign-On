package com.daop.sso.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class Info {
    @Value("${test}")
    String test;

    public String getTest() {
        return test;
    }
}
