package com.apnabusiness.eventsapp.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.apnabusiness.eventsapp.model.User;
import com.apnabusiness.eventsapp.service.UserService;

@RestController
public class EventsAppRestController {

	private static final Logger logger = Logger.getLogger(EventsAppRestController.class);
	
	@Autowired
	UserService userService; // Service which will do all data
								// retrieval/manipulation work

	// -------------------Retrieve All
	// Users--------------------------------------------------------

	@RequestMapping(value = "/user/", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers() {
		if(logger.isDebugEnabled()) {
			logger.debug("Getting all users");
		}
		
		List<User> users = userService.findAllUsers();
		if (users.isEmpty()) {
			if(logger.isDebugEnabled()) {
				logger.debug("No users found");
			}
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);// You
																			// many
																			// decide
																			// to
																			// return
																			// HttpStatus.NOT_FOUND
		}
		if(logger.isDebugEnabled()) {
			logger.debug("users found");
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	// -------------------Retrieve Single
	// User--------------------------------------------------------

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUser(@PathVariable("id") long id) {
		if(logger.isDebugEnabled())
			logger.debug("Fetching User with id " + id);
		User user = userService.findById(id);
		if (user == null) {
			if(logger.isDebugEnabled())
				logger.debug("User with id " + id + " not found");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	// -------------------Create a
	// User--------------------------------------------------------

	@RequestMapping(value = "/user/", method = RequestMethod.POST)
	public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
		if(logger.isDebugEnabled())
			logger.debug("Creating User " + user.getUsername());

		if (userService.isUserExist(user)) {
			if(logger.isDebugEnabled())
				logger.debug("A User with name " + user.getUsername() + " already exist");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		if(logger.isDebugEnabled())
			logger.debug("A User with name " + user.getUsername() + " doesn't exist");

		userService.saveUser(user);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a User
	// --------------------------------------------------------

	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
		if(logger.isDebugEnabled())
			logger.debug("Updating User " + id);

		User currentUser = userService.findById(id);

		if (currentUser == null) {
			if(logger.isDebugEnabled())
				logger.debug("User with id " + id + " not found");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}

		currentUser.setUsername(user.getUsername());
		currentUser.setAddress(user.getAddress());
		currentUser.setEmail(user.getEmail());

		userService.updateUser(currentUser);
		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
	}

	// ------------------- Delete a User
	// --------------------------------------------------------

	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
		if(logger.isDebugEnabled())
			logger.debug("Fetching & Deleting User with id " + id);

		User user = userService.findById(id);
		if (user == null) {
			if(logger.isDebugEnabled())
				logger.debug("Unable to delete. User with id " + id + " not found");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		if(logger.isDebugEnabled())
			logger.debug("Unable to delete. User with id " + id + " found");
		
		userService.deleteUserById(id);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Users
	// -------------------------------------------------------- 

	@RequestMapping(value = "/user/", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteAllUsers() {
		if(logger.isDebugEnabled())
			logger.debug("Deleting All Users");

		userService.deleteAllUsers();
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}
}
