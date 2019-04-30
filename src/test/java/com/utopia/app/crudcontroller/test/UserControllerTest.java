package com.utopia.app.crudcontroller.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utopia.app.curdcontroller.UserController;
import com.utopia.app.model.User;
import com.utopia.app.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private UserService service;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Test
	public void getListOfUsersReturnOk() throws Exception {
		User user = new User();
		user.setUserId((long) 1);
		user.setEmail("postman@gcit.com");
		user.setUsername("manWhoPost");
		user.setPhone("911-119-9911");
		user.setPassword("123456");
		List<User> users = new ArrayList<>();
		users.add(user);
		
		when(service.getUserAll()).thenReturn(users);
		
		mvc.perform(get("/adm/users").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$",hasSize(1)))
		.andExpect(jsonPath("$[0].username",is(user.getUsername())))
		.andExpect(jsonPath("$[0].email",is(user.getEmail())))
		.andExpect(jsonPath("$[0].phone",is(user.getPhone())));
	}
	
	@Test
	public void getOneUserReturnOk() throws Exception {
		User user = new User();
		user.setUserId((long) 1);
		user.setEmail("postman@gcit.com");
		user.setUsername("manWhoPost");
		user.setPhone("911-119-9911");
		user.setPassword("123456");
		
		when(service.getUserById(1)).thenReturn(user);
		
		mvc.perform(get("/adm/user/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.username",is(user.getUsername())))
		.andExpect(jsonPath("$.email",is(user.getEmail())))
		.andExpect(jsonPath("$.phone",is("911-119-9911")));
	}
	
	@Test
	public void createUserReturnCreated() throws Exception {
		User user = new User();
		user.setUserId((long) 1);
		user.setEmail("postman@gcit.com");
		user.setActive(true);
		user.setUsername("manWhoPost");
		user.setPhone("911-119-9911");
		user.setPassword("123456");
		
		mvc.perform(post("/adm/user")
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(user))				
			    .contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated());
	}
	
	@Test
	public void updateUserReturnAccepted() throws Exception{
		User user = new User();
		user.setUserId((long) 1);
		user.setEmail("postman@gcit.com");
		user.setActive(true);
		user.setUsername("manWhoPost");
		user.setPhone("911-119-9911");
		user.setPassword("123456");
		
		mvc.perform(put("/adm/user")
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(user))				
			    .contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());
	}
	
	@Test
	public void deleteUserReturnAccepted() throws Exception {
		mvc.perform(delete("/adm/user/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());
	}
}

