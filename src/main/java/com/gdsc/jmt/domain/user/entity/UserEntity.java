package com.gdsc.jmt.domain.user.entity;

import com.gdsc.jmt.domain.user.entity.common.RoleType;
import com.gdsc.jmt.domain.user.entity.common.Status;
import com.gdsc.jmt.global.entity.BaseTimeEntity;
import com.gdsc.jmt.domain.user.entity.common.SocialType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity @Table(name = "tb_user")
public class UserEntity  extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String profileImageUrl;

    @Column(unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    public UserEntity(String email, SocialType socialType,
                      RoleType roleType, Status status) {
        this.email = email;
        this.socialType = socialType;
        this.roleType = roleType;
        this.status = status;
    }

}
