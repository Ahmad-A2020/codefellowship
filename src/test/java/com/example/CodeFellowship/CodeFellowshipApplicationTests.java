package com.example.CodeFellowship;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc

class CodeFellowshipApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Test
	void indexTest() throws Exception {
		mockMvc.perform(get("/home"))
				.andExpect(status().isOk());
	}

	@Test
	void loginTest() throws Exception {
		mockMvc.perform(formLogin("/auth").user("Anas").password("asd"))
				.andExpect(unauthenticated());

	}

	@Test
	void signUpTest() throws Exception {
		mockMvc.perform(post("/signup")
		.param("username","ahmad")
		.param("password","1234")
		.param("firstName","ahmad")
		.param("lastName","alsayed")
		.param("dateOfBirth", String.valueOf(new Date()))
		.param("bio","akjsh ash ")

		)
		.andExpect(redirectedUrl("/home")).andExpect(status().isFound());
	}

}
