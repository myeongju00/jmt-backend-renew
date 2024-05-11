package com.gdsc.jmt.domain.user.apple;

import lombok.Getter;

@Getter
public class Key {
    private String kty;
    private String kid;
    private String use;
    private String alg;
    private String n;
    private String e;
}
