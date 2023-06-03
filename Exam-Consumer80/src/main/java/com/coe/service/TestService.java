package com.coe.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@FeignClient(value = "EXAMPROVIDER")
public interface TestService {
    @RequestMapping("/testSession")
    public void testSession();
}
