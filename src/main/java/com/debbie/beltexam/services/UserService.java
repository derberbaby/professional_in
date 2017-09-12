package com.debbie.beltexam.services;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.debbie.beltexam.models.User;
import com.debbie.beltexam.repositories.RoleRepo;
import com.debbie.beltexam.repositories.UserRepo;

@Transactional
@Service
public class UserService {
	private UserRepo userRepo;
	private RoleRepo roleRepo;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public UserService(UserRepo userRepo, RoleRepo roleRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepo = userRepo;
		this.roleRepo = roleRepo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	public void saveUserWithRole(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles(roleRepo.findByName("ROLE_USER"));
		userRepo.save(user);
	}
	
	public User findByEmail(String email) {
		return userRepo.findByEmail(email);
	}
	
	public List<User> findAllUsers() {
		return userRepo.findAllUsers();
	}
	
	public User findUserById(Long id) {
		return userRepo.findOne(id);
	}
	
//	public void updateUser(User user) {
//		Date now = new Date();
//		userRepo.updateUser(user.getName(), user.getEmail(), user.getPassword(), user.getDescription(), now, user.getId());
//	}
	
	public void sendInvite(User currentUser, User newFriend) {
		List<User> myInvites = currentUser.getInvites_i_sent();
		
		myInvites.add(newFriend);
		
		userRepo.save(currentUser);
	}

	public void acceptInvite(User currentUser, User friendIWant) {
		  List<User> myFriends = currentUser.getFriends_i_added(); 
		  List<User> myInvites = currentUser.getInvites_to_me();
		  List<User> theirFriends = friendIWant.getFriends_i_added();
		  List<User> theirInvites = friendIWant.getInvites_i_sent();
		  
		  theirFriends.add(currentUser);
		  myFriends.add(friendIWant);
		  myInvites.remove(friendIWant);
		  theirInvites.remove(currentUser);
		  
		  userRepo.save(friendIWant);		  
		  userRepo.save(currentUser);
		 }
	
	public void ignoreInvite(User currentUser, User friendIDontWant) {
		List<User> myInvites = currentUser.getInvites_to_me();
		List<User> theirInvitations = friendIDontWant.getInvites_i_sent();
		
		myInvites.remove(friendIDontWant);
		theirInvitations.remove(currentUser);
		
		userRepo.save(currentUser);
	}
}
