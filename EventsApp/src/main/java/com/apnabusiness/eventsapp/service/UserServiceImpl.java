package com.apnabusiness.eventsapp.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apnabusiness.eventsapp.controller.IndexController;
import com.apnabusiness.eventsapp.model.User;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	private static final AtomicLong counter = new AtomicLong();

	private static List<User> users;

	static {
		if(logger.isDebugEnabled()) {
			logger.debug("Populating dummy users");
		}
		users = populateDummyUsers();
	}

	public List<User> findAllUsers() {
		if(logger.isDebugEnabled()) {
			logger.debug("find all users");
		}
		return users;
	}

	public User findById(long id) {
		if(logger.isDebugEnabled()) {
			logger.debug("Find user By Id : " + id);
		}
		for (User user : users) {
			if (user.getId() == id) {
				if(logger.isDebugEnabled()) {
					logger.debug("User found returning user");
				}
				return user;
			}
		}
		if(logger.isDebugEnabled()) {
			logger.debug("User not found returning user");
		}
		return null;
	}

	public User findByName(String name) {
		if(logger.isDebugEnabled()) {
			logger.debug("Find user by name : " + name);
		}
		for (User user : users) {
			if (user.getUsername().equalsIgnoreCase(name)) {
				if(logger.isDebugEnabled()) {
					logger.debug("User found returning user");
				}
				return user;
			}
		}
		if(logger.isDebugEnabled()) {
			logger.debug("User not found returning user");
		}
		return null;
	}

	public void saveUser(User user) {
		if(logger.isDebugEnabled()) {
			logger.debug("Save new User");
		}
		user.setId(counter.incrementAndGet());
		users.add(user);
	}

	public void updateUser(User user) {
		if(logger.isDebugEnabled()) {
			logger.debug("Update existing User with id : " + user.getId());
		}
		int index = users.indexOf(user);
		users.set(index, user);
	}

	public void deleteUserById(long id) {
		if(logger.isDebugEnabled()) {
			logger.debug("Delete existing User with id : " + id);
		}
		for (Iterator<User> iterator = users.iterator(); iterator.hasNext();) {
			User user = iterator.next();
			if (user.getId() == id) {
				if(logger.isDebugEnabled()) {
					logger.debug("Deleted existing User with id : " + id);
				}
				iterator.remove();
			}
		}
	}

	public boolean isUserExist(User user) {
		if(logger.isDebugEnabled()) {
			logger.debug("checking user exists or not for id : " + user.getId() + " and by name : " + user.getUsername());
		}
		return findByName(user.getUsername()) != null;
	}

	public void deleteAllUsers() {
		if(logger.isDebugEnabled()) {
			logger.debug("Delete all users");
		}
		users.clear();
	}

	private static List<User> populateDummyUsers() {
		List<User> users = new ArrayList<User>();
		users.add(new User(counter.incrementAndGet(), "Sam", "NY", "sam@abc.com"));
		users.add(new User(counter.incrementAndGet(), "Tomy", "ALBAMA", "tomy@abc.com"));
		users.add(new User(counter.incrementAndGet(), "Kelly", "NEBRASKA", "kelly@abc.com"));
		return users;
	}

}
