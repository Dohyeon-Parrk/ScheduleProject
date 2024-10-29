package com.sparta.scheduledevelope.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sparta.scheduledevelope.domain.user.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
