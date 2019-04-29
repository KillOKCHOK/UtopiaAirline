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
	public void get_list_of_users_return_ok() throws Exception {
		User u = new User();
		u.setUserId(1);
		u.setEmail("postman@gcit.com");
		u.setActive(true);
		u.setUsername("manWhoPost");
		u.setPhone("911-119-9911");
		u.setPassword("123456");
		List<User> users = new ArrayList<>();
		users.add(u);
		
		when(service.getUserAll()).thenReturn(users);
		
		mvc.perform(get("/adm/users").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$",hasSize(1)))
		.andExpect(jsonPath("$[0].username",is("manWhoPost")))
		.andExpect(jsonPath("$[0].email",is("postman@gcit.com")))
		.andExpect(jsonPath("$[0].phone",is("911-119-9911")))
		.andExpect(jsonPath("$[0].active",is(true)));
	}
	
	@Test
	public void get_one_user_return_ok() throws Exception {
		User u = new User();
		u.setUserId(1);
		u.setEmail("postman@gcit.com");
		u.setActive(true);
		u.setUsername("manWhoPost");
		u.setPhone("911-119-9911");
		u.setPassword("123456");
		
		when(service.getUserById(1)).thenReturn(u);
		
		mvc.perform(get("/adm/user/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.username",is("manWhoPost")))
		.andExpect(jsonPath("$.email",is("postman@gcit.com")))
		.andExpect(jsonPath("$.phone",is("911-119-9911")))
		.andExpect(jsonPath("$.active",is(true)));
	}
	
	@Test
	public void create_user_return_created() throws Exception {
		User u = new User();
		u.setUserId(1);
		u.setEmail("postman@gcit.com");
		u.setActive(true);
		u.setUsername("manWhoPost");
		u.setPhone("911-119-9911");
		u.setPassword("123456");
		
		mvc.perform(post("/adm/user")
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(u))				
			    .contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated());
	}
	
	@Test
	public void update_user_return_accepted() throws Exception{
		User u = new User();
		u.setUserId(1);
		u.setEmail("postman@gcit.com");
		u.setActive(true);
		u.setUsername("manWhoPost");
		u.setPhone("911-119-9911");
		u.setPassword("123456");
		
		mvc.perform(put("/adm/user")
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(u))				
			    .contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());
	}
	
	@Test
	public void delete_user_return_accepted() throws Exception {
		mvc.perform(delete("/adm/user/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());
	}
}

