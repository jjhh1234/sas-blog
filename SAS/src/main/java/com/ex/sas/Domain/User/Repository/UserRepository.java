package com.ex.sas.Domain.User.Repository;

import com.ex.sas.Domain.User.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Boolean existsByUserId(String userId);

    //userId를 받아 DB 테이블에서 회원을 조회하는 메소드 작성
    UserEntity findByUserId(String userId);

}
