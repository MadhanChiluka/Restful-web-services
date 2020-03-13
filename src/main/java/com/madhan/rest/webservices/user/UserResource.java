package com.madhan.rest.webservices.user;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserResource {
	private static final String USER_NAME = "Admin";

	@Autowired
	private UserDaoService userDaoService;

	// GET /users
	// Retrieve All Users
	@GetMapping("/users")
	public List<User> retrieveAllUsers() {
		List<User> users =  userDaoService.findAll();
		if(users.isEmpty()) {
			throw new UserNotFoundException("No users found");
		}
		return users;
	}

	// Get /user
	// Retrieve User
	@GetMapping("/users/{id}")
	public Resource<User> retrieveUser(@PathVariable int id) {
		User user = userDaoService.findOne(id);
		
		if(user == null) {
			throw new UserNotFoundException("id -" +id);
		}
		
		//Irfan start here
		if(user.getName().equalsIgnoreCase(USER_NAME)) {
			HashMap<Integer, User> usermap = new HashMap<Integer, User>();
			usermap.put(1, user);
			List list = new ArrayList();
			list.add(usermap);
		}
		//Irfan end here
		Resource<User> resource = new Resource<User>(user);
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		resource.add(linkTo.withRel("all-users"));
		return resource;
	}

	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) throws Exception {
		User savedUser = userDaoService.save(user);
		
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("users/{id}")
	public void deleteUser(@PathVariable int id) {
		User user = userDaoService.deleteById(id);
		
		if(user == null)
			throw new UserNotFoundException("id-"+id);
	}

}
