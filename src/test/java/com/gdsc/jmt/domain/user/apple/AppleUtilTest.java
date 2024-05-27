package com.gdsc.jmt.domain.user.apple;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@ActiveProfiles("dev")
class AppleUtilTest {
    @Autowired
    AppleUtil appleUtil;

    @Test
    @DisplayName("Apple 공개키를 가져온다.")
    public void getApplePublickey() {
        assertNotNull(appleUtil.getApplePublickey());
    }

}