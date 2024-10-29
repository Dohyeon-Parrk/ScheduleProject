package com.sparta.scheduledevelope.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sparta.scheduledevelope.domain.user.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByEmail(String email);

	default void findUserByEmail(String email){
		Optional<Member> checkMember = findByEmail(email);
		if(checkMember.isEmpty()){
			throw new IllegalArgumentException("중복된 Email 입니다.");
		}
	}
}
