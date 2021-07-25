package com.example.CodeFellowship.web;

import com.example.CodeFellowship.Infrastructure.UserRepository;
import com.example.CodeFellowship.domain.ApplicationUser;
import com.example.CodeFellowship.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import com.example.CodeFellowship.domain.ApplicationUser;

import java.util.ArrayList;

@Controller
public class HomeController {
    @Autowired
    UserRepository userRepository;
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
        Post post =new Post(postText);

        return new RedirectView("/home");
    }



}
