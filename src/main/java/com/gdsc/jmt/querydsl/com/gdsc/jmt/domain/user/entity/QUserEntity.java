package com.gdsc.jmt.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserEntity is a Querydsl query type for UserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserEntity extends EntityPathBase<UserEntity> {

    private static final long serialVersionUID = -931307185L;

    public static final QUserEntity userEntity = new QUserEntity("userEntity");

    public final com.gdsc.jmt.global.entity.QBaseTimeEntity _super = new com.gdsc.jmt.global.entity.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedTime = _super.modifiedTime;

    public final StringPath nickname = createString("nickname");

    public final StringPath profileImageUrl = createString("profileImageUrl");

    public final EnumPath<com.gdsc.jmt.domain.user.entity.common.RoleType> roleType = createEnum("roleType", com.gdsc.jmt.domain.user.entity.common.RoleType.class);

    public final EnumPath<com.gdsc.jmt.domain.user.entity.common.SocialType> socialType = createEnum("socialType", com.gdsc.jmt.domain.user.entity.common.SocialType.class);

    public final EnumPath<com.gdsc.jmt.domain.user.entity.common.Status> status = createEnum("status", com.gdsc.jmt.domain.user.entity.common.Status.class);

    public QUserEntity(String variable) {
        super(UserEntity.class, forVariable(variable));
    }

    public QUserEntity(Path<? extends UserEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserEntity(PathMetadata metadata) {
        super(UserEntity.class, metadata);
    }

}

