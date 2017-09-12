package com.debbie.beltexam.controllers;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.debbie.beltexam.models.User;
import com.debbie.beltexam.services.UserService;
import com.debbie.beltexam.validators.UserValidator;

@Controller
public class UserController {
	private UserService userService;
	private UserValidator userValidator;
	
	public UserController(UserService userService, UserValidator userValidator) {
		this.userService = userService;
		this.userValidator = userValidator;
	}
	
	@RequestMapping("/main")
	public String login(@RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, Model model, @ModelAttribute("userObject") User user) {
		if(error != null) {
			model.addAttribute("errorMessage", "Invalid Credentials, please try again.");
		}
		if(logout != null) {
			model.addAttribute("logoutMessage", "Logout Successful!");
		}
		return "login";
	}
	
	@PostMapping("/registration")
	public String registration(@Valid @ModelAttribute("userObject") User user, BindingResult result) {
		userValidator.validate(user, result);
		
		if(result.hasErrors()) {
			return "login";
		} else {
			userService.saveUserWithRole(user);
			return "redirect:/professional_profile";
		}
	}
	
	@RequestMapping(value= {"/", "/professional_profile"})
	public String profilePage(Principal principal, Model model) {
		String email = principal.getName();
		User currentUser = userService.findByEmail(email);
		model.addAttribute("currentUser", currentUser);
		
		List<User> invitations = currentUser.getInvites_to_me();
		model.addAttribute("invitations", invitations);
		
		List<User> friends = currentUser.getFriends_i_added();
		model.addAttribute("friendsList", friends);
		return "profile";
	}
	
	@RequestMapping("/users")
	public String displayUsers(Principal principal, Model model) {
		String email = principal.getName();
		User currentUser = userService.findByEmail(email);
		model.addAttribute("currentUser", currentUser);
		
		List<User> allUsers = userService.findAllUsers();
		model.addAttribute("allUsers", allUsers);
		return "users";
	} 
	
	@RequestMapping("/users/{friend_id}/connect")
	public String sendInvite(Principal principal, Model model, @PathVariable("friend_id") Long id) {
		String email = principal.getName();
		User currentUser = userService.findByEmail(email);
		User newFriend = userService.findUserById(id);
		
//		List<User> friendInvitations = newFriend.getInvites_to_me();
//		friendInvitations.add(currentUser);
//		userService.updateUser(newFriend);
		
//		List<User> myInvites = currentUser.getInvites_i_sent();
//		myInvites.add(newFriend);
//		userService.updateUser(currentUser);
		
		userService.sendInvite(currentUser, newFriend);
		
		return "redirect:/users";
	}
	
	@RequestMapping("/users/{friend_id}/accept")
	public String acceptInvite(Principal principal, Model model, @PathVariable("friend_id") Long id) {
		User currentUser = userService.findByEmail(principal.getName());
		User friendIWant = userService.findUserById(id);
		
//		List<User> myInvites = currentUser.getInvites_to_me();
//		myInvites.remove(friendIWant);
//		List<User> myFriends = currentUser.getFriends_i_added();
//		myFriends.add(friendIWant);

//		List<User> theirInvites = friendIWant.getInvites_to_me();
//		theirInvites.remove(currentUser);
//		List<User> theirFriends = friendIWant.getFriends_i_added();
//		theirFriends.add(currentUser);

//		userService.updateUser(currentUser);
		
		userService.acceptInvite(currentUser, friendIWant);
		
		return "redirect:/professional_profile";
	}
	
	@RequestMapping("/users/{friend_id}/ignore")
	public String ignoreInvite(Principal principal, Model model, @PathVariable("friend_id") Long id) {
		User currentUser = userService.findByEmail(principal.getName());
		User friendIDontWant = userService.findUserById(id);

//		List<User> myInvites = currentUser.getInvites_to_me();
//		myInvites.remove(friendIDontWant);
//		
//		List<User> theirInvitations = friendIDontWant.getInvites_i_sent();
//		theirInvitations.remove(currentUser);
//		userService.updateUser(friendIDontWant);
//		
//		userService.updateUser(currentUser);
		
		userService.ignoreInvite(currentUser, friendIDontWant);
		return "redirect:/professional_profile";
	}
	
	@RequestMapping("/users/{user_id}")
	public String showUser(Model model, @PathVariable("user_id") Long id) {
		User user = userService.findUserById(id);
		model.addAttribute("user", user);
		return "showUser";
	}
	
}
