package com.nitinrana.movieticketbooking.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nitinrana.movieticketbooking.dto.RoleDto;
import com.nitinrana.movieticketbooking.dto.UserRequestDto;
import com.nitinrana.movieticketbooking.dto.UserResponseDto;
import com.nitinrana.movieticketbooking.exception.MtbException;
import com.nitinrana.movieticketbooking.model.Role;
import com.nitinrana.movieticketbooking.model.User;
import com.nitinrana.movieticketbooking.repository.RoleRepository;
import com.nitinrana.movieticketbooking.repository.UserRepository;
import com.nitinrana.movieticketbooking.security.JwtHelper;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	@Lazy
	private PasswordEncoder passwordEncoder;

	@Autowired
	@Lazy
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private JwtHelper helper;

	/* gets users */
	public List<UserResponseDto> getUsers() {
		return userRepository.findAll().stream().map((user) -> this.modelMapper.map(user, UserResponseDto.class))
				.collect(Collectors.toList());
	}

	/* gets user by email */
	public UserResponseDto getUser(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new MtbException(String.format(MtbException.USER_NOT_EXIST, email)));

		return this.modelMapper.map(user, UserResponseDto.class);
	}

	/* creates user */
	public UserResponseDto createUser(UserRequestDto userRequestDto) {

		/*
		 * check if user doesn't exist with email
		 */
		if (userRepository.existsByEmail(userRequestDto.getEmail()))
			throw new MtbException(String.format(MtbException.USER_ALREADY_EXIST, userRequestDto.getEmail()));

		/* check if roles exist */
		List<Role> roles = roleRepository.findByRoleIdIn(userRequestDto.getRoles());

		List<Integer> roleNotExistList = new ArrayList<>();
		for (int roleId : userRequestDto.getRoles()) {
			boolean isExist = false;
			for (Role role : roles) {
				if (role.getRoleId() == roleId) {
					isExist = true;
					break;
				}
			}
			if (!isExist)
				roleNotExistList.add(roleId);
		}

		if (!roleNotExistList.isEmpty())
			throw new MtbException(String.format(MtbException.ROLES_NOT_EXIST, roleNotExistList));

		/* create user object */
		User user = modelMapper.map(userRequestDto, User.class);
		user.setRoles(roles);
		user.setIsEnabled(true);
		user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

		User newUser = userRepository.save(user);
		return this.userToUserResponseDto(newUser);
	}

	/* updates user */
	public UserResponseDto updateUser(UserRequestDto userRequestDto) {

		User existingUser = (User) getUserDetails();

		/* check if user is enabled */
		if (!existingUser.getIsEnabled())
			throw new MtbException(String.format(MtbException.USER_NOT_ENABLED, userRequestDto.getEmail()));

		/* check if roles exist */
		List<Role> roles = roleRepository.findByRoleIdIn(userRequestDto.getRoles());

		List<Integer> roleNotExistList = new ArrayList<>();
		for (int roleId : userRequestDto.getRoles()) {
			boolean isExist = false;
			for (Role role : roles) {
				if (role.getRoleId() == roleId) {
					isExist = true;
					break;
				}
			}
			if (!isExist)
				roleNotExistList.add(roleId);
		}

		if (!roleNotExistList.isEmpty())
			throw new MtbException(String.format(MtbException.ROLES_NOT_EXIST, roleNotExistList));

		/* updates user object */
		existingUser.setName(userRequestDto.getName());
		existingUser.setRoles(roles);
		existingUser.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

		User updatedUser = userRepository.save(existingUser);
		return this.userToUserResponseDto(updatedUser);

	}

	/* disable user */
	public void disableUser(String password) {

		User existingUser = (User) getUserDetails();

		/* authenticate user */
		doAuthenticate(existingUser.getEmail(), password);

		/* check if user is enabled */
		if (!existingUser.getIsEnabled())
			throw new MtbException(String.format(MtbException.USER_NOT_ENABLED, existingUser.getEmail()));

		existingUser.setIsEnabled(false);
		userRepository.save(existingUser);
	}

	/* login user */
	public Map<String, String> loginUser(UserRequestDto userRequestDto) {

		/* authenticate user */
		doAuthenticate(userRequestDto.getEmail(), userRequestDto.getPassword());

		UserDetails userDetails = userDetailsService.loadUserByUsername(userRequestDto.getEmail());
		String token = helper.generateToken(userDetails);

		Map<String, String> map = new HashMap<>();
		map.put("jwt", token);

		return map;
	}

	/* converts User to UserResponseDto */
	private UserResponseDto userToUserResponseDto(User user) {
		UserResponseDto userResponseDto = modelMapper.map(user, UserResponseDto.class);

		userResponseDto.setRoles(user.getRoles().stream().map(role -> modelMapper.map(role, RoleDto.class))
				.collect(Collectors.toList()));

		return userResponseDto;
	}

	/* authenticates user */
	private void doAuthenticate(String email, String password) {

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);

		manager.authenticate(authentication);
	}

	/* overriden method of UserDetailsService */
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		return userRepository.findByEmail(userName)
				.orElseThrow(() -> new UsernameNotFoundException(String.format(MtbException.USER_NOT_EXIST, userName)));
	}

	/* get user details. This method only be called when logged in. */
	public UserDetails getUserDetails() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return userDetails;
	}

}
