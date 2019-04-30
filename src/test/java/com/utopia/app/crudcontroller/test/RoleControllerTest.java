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
import com.utopia.app.curdcontroller.RoleController;
import com.utopia.app.model.Role;
import com.utopia.app.service.RoleService;

@RunWith(SpringRunner.class)
@WebMvcTest(RoleController.class)
public class RoleControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private RoleService service;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Test
	public void get_list_of_roles_return_ok() throws Exception {
		Role r = new Role();
		r.setRoleId((long) 1);
		r.setRoleName("Agent");
		List<Role> roles = new ArrayList<>();
		roles.add(r);
		
		when(service.getRoleAll()).thenReturn(roles);
		
		mvc.perform(get("/adm/roles").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$",hasSize(1)))
		.andExpect(jsonPath("$[0].roleName",is("Agent")));
	}
	
	@Test
	public void get_one_role_return_ok() throws Exception {
		Role r = new Role();
		r.setRoleId((long) 1);
		r.setRoleName("Agent");
		
		when(service.getRoleById(1)).thenReturn(r);
		
		mvc.perform(get("/adm/role/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.roleName",is("Agent")));
	}
	
	@Test
	public void create_role_return_created() throws Exception {
		Role r = new Role();
		r.setRoleName("Agent");
		
		mvc.perform(post("/adm/role")
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(r))				
			    .contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated());
	}
	
	@Test
	public void update_role_return_accepted() throws Exception{
		Role r = new Role();
		r.setRoleId((long) 1);
		r.setRoleName("Agent");
		
		mvc.perform(put("/adm/role")
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(r))				
			    .contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());
	}
	
	@Test
	public void delete_role_return_accepted() throws Exception {
		mvc.perform(delete("/adm/role/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());
	}
}
