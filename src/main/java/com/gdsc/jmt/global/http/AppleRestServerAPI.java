package com.gdsc.jmt.global.http;

import com.gdsc.jmt.domain.user.apple.Keys;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "apple", url = "https://appleid.apple.com/")
public interface AppleRestServerAPI {
    @GetMapping(value = "auth/keys/", produces = "application/json")
    Keys sendAPI();
}
