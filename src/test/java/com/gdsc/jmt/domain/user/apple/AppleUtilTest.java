package com.gdsc.jmt.domain.user.apple;

import static org.junit.jupiter.api.Assertions.*;

import com.gdsc.jmt.CustomSpringBootTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@CustomSpringBootTest
class AppleUtilTest {
    @Autowired
    private AppleUtil appleUtil;

    @Test
    @DisplayName("Apple 공개키를 가져온다.")
    public void getApplePublickey() {
        assertNotNull(appleUtil.getApplePublickey());
    }

}