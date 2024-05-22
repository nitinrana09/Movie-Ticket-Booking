package com.nitinrana.movieticketbooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nitinrana.movieticketbooking.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	/* gives roles by role id list */
	List<Role> findByRoleIdIn(List<Integer> roleIdList);
}
