package com.example.CodeFellowship.web;

import com.example.CodeFellowship.Infrastructure.PostRepository;
import com.example.CodeFellowship.Infrastructure.UserRepository;
import com.example.CodeFellowship.domain.ApplicationUser;
import com.example.CodeFellowship.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import com.example.CodeFellowship.domain.ApplicationUser;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;
//    @Autowired
//    BCryptPasswordEncoder encode;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping ("/home")
    public String home(){

        return ("index.html");
    }

    @GetMapping("/signup")
    public String newUser(){
        return "signup.html";
    }

    @GetMapping("/login")
    public String login(){
        return ("login.html");
    }

    @GetMapping("/users/{id}")
    public String profile(@PathVariable("id") Long id, Model m){
        ApplicationUser user  = userRepository.findById(id).get();
        m.addAttribute("data",user);
        return "profile.html";
    }

    @PostMapping("/signup")
    public RedirectView newUser(@RequestParam(value="username") String username,@RequestParam(value="password") String password,@RequestParam(value="firstName") String firstName,@RequestParam(value="lastName") String lastName,@RequestParam(value="dateOfBirth") String dateOfBirth,@RequestParam(value="bio") String bio  ){
        ApplicationUser newUser = new ApplicationUser(username,passwordEncoder.encode(password),firstName,lastName,dateOfBirth,bio);
        newUser=userRepository.save(newUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser,null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/home");
    }

    @GetMapping("/addPost")
    public String addPost(){
        return ("addPost.html");
    }

    @PostMapping("/post")
    public RedirectView PostHandler(@RequestParam(value = "post") String postText){
        UserDetails userDetails= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ApplicationUser currentUser= userRepository.findByUsername(userDetails.getUsername());
        Post post =new Post(postText,currentUser);
//        currentUser.setNewPost(post);
        postRepository.save(post);
        return new RedirectView("/home");
    }

    @GetMapping("/myprofile")
    public String profileHandler(Model m ){
        UserDetails userDetails= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ApplicationUser currentUser= userRepository.findByUsername(userDetails.getUsername());
        System.out.println(currentUser.getUsername());
        m.addAttribute("data",currentUser);
        return ("profile.html");


    }
    @GetMapping ("/suggest")
    public String home(Model m, Principal p){
        List<ApplicationUser> allUsers= userRepository.findAll();
        ApplicationUser currentUser= userRepository.findByUsername(p.getName());
        List<ApplicationUser> followingList= currentUser.getFollowing();
        List<ApplicationUser> notFollowedUser= new ArrayList<>();
        allUsers.forEach((element)->{
            if ((! followingList.contains(element)) && element.getUsername() != currentUser.getUsername() ){
                notFollowedUser.add(element);
            }
        });
        m.addAttribute("users",notFollowedUser);
        return ("suggest.html");
    }

    @GetMapping("/follow/{id}")
    public RedirectView addFollower(@PathVariable("id") Long id, Principal p){
        ApplicationUser usertoFollow = userRepository.findById(id).get();
        ApplicationUser currentUser = userRepository.findByUsername(p.getName());
        currentUser.setFollowing(usertoFollow);
        userRepository.save(currentUser);
        return new RedirectView("/suggest");

    }
    @GetMapping("/followList")
    public String followerContent( Principal p, Model m){
        ApplicationUser currentUser = userRepository.findByUsername(p.getName());
        List<ApplicationUser> followList= currentUser.getFollowing();

        m.addAttribute("followings",followList);
        return "feed.html";

    }

    @GetMapping("/profile/{id}")
    public String profileOfFollowing(@PathVariable("id") Long id, Model m){
        ApplicationUser following = userRepository.findById(id).get();
        m.addAttribute("data",following);
        return ("profile.html");

    }





}
