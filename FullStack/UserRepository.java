package com.crud.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crud.demo.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
		
	@Query("SELECT u FROM UserEntity u WHERE "+
	       "LOWER(u.username) LIKE :keyword OR "+
		   "LOWER(STR(u.contact)) LIKE :keyword")
	
	List<UserEntity> searchUsers(@Param("keyword") String keyword);
}
