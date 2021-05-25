package com.weDealAdmin.TestsUserManagement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weDealAdmin.TestsUserManagement.model.User;
import com.weDealAdmin.TestsUserManagement.service.UserCreateDto;
import com.weDealAdmin.TestsUserManagement.service.UserService;
import com.weDealAdmin.TestsUserManagement.service.UserUpdateDto;

@WebMvcTest
class UserManagementControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
    private WebApplicationContext context;
	
	@MockBean
	private UserService userService;
	
	@BeforeEach
	public void setUp() {
		this.mockMvc = webAppContextSetup(context).build();
	}

	@Test
	public void testGetUsers_httpMethod() throws Exception 
	{
		MockHttpServletRequestBuilder request = get("/users");
		
		mockMvc.perform(request)
			.andExpect(status().isOk());
	}
	
	@Test
	public void testGetUsers_outputSerialization() throws Exception
	{
		List<User> users = new ArrayList<User>();
		
		MockHttpServletRequestBuilder request = get("/users");
		
		MvcResult result = mockMvc.perform(request)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andReturn();
		
		String resultBody = result.getResponse().getContentAsString();
		String expectedResponse = objectMapper.writeValueAsString(users);
		
		assertThat(resultBody).isEqualToIgnoringWhitespace(expectedResponse);
	}
	
	@Test
	public void testGetUser_success() throws Exception 
	{
		User user = new User();
		user.setId(9999L);
		user.setUsername("AtAb");
		user.setFirstName("Nicolas");
		user.setLastName("Fasano");
		user.setEmail("adresse@email.com");
		user.setPassword("password");
		
		MockHttpServletRequestBuilder request = get("/users/{username}", "NFLorD");
		
		when(userService.findByUsername(
			ArgumentMatchers.anyString()
		))
			.thenReturn(user);
		
		mockMvc.perform(request)
			.andExpect(status().isOk());
	}
	
	@Test
	public void testGetUser_notFound() throws Exception 
	{
		MockHttpServletRequestBuilder request = get("/users/{username}", "NFLorD");

		when(userService.findByUsername(
			ArgumentMatchers.anyString()
		))
			.thenReturn(null);
		
		mockMvc.perform(request)
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void testGetUser_serviceCall() throws Exception 
	{	
		MockHttpServletRequestBuilder request = get("/users/{username}", "NFLorD");
		
		mockMvc.perform(request);
		
		ArgumentCaptor<String> usernameCaptor = ArgumentCaptor.forClass(String.class);
		
		verify(userService, times(1)).findByUsername(usernameCaptor.capture());
		assertThat(usernameCaptor.getValue()).isEqualTo("NFLorD");
	}
	
	@Test
	public void testGetUser_outputSerialization() throws Exception 
	{
		String username = "NFLorD";
		
		User foundUser = new User();
		foundUser.setUsername(username);
		
		when(userService.findByUsername(
			ArgumentMatchers.anyString()
		))
			.thenReturn(foundUser);
		
		MockHttpServletRequestBuilder request = get("/users/{username}", username);
		
		mockMvc.perform(request)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.username", is(username)));
	}
	
	@Test
	public void testCreate_created() throws Exception 
	{
		UserCreateDto userCreateDto = new UserCreateDto();
		userCreateDto.setUsername("NFLorD");
		userCreateDto.setFirstName("Nicolas");
		userCreateDto.setLastName("Fasano");
		userCreateDto.setEmail("adresse@email.com");
		userCreateDto.setPassword("password");
		
		MockHttpServletRequestBuilder request = post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userCreateDto));
		
		mockMvc.perform(request)
			.andExpect(status().isCreated());
	}
	
	@Test
	public void testCreate_usernameTooLong() throws Exception 
	{
		UserCreateDto userCreateDto = new UserCreateDto();
		userCreateDto.setUsername("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopq");
		userCreateDto.setFirstName("Nicolas");
		userCreateDto.setLastName("Fasano");
		userCreateDto.setEmail("adresse@email.com");
		userCreateDto.setPassword("password");
		
		MockHttpServletRequestBuilder request = post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userCreateDto));
		
		mockMvc.perform(request)
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testCreate_missingUsername() throws Exception 
	{
		UserCreateDto userCreateDto = new UserCreateDto();
		userCreateDto.setFirstName("Nicolas");
		userCreateDto.setLastName("Fasano");
		userCreateDto.setEmail("adresse@email.com");
		userCreateDto.setPassword("password");
		
		MockHttpServletRequestBuilder request = post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userCreateDto));
		
		mockMvc.perform(request)
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testCreate_usernameTooShort() throws Exception 
	{
		UserCreateDto userCreateDto = new UserCreateDto();
		userCreateDto.setUsername("");
		userCreateDto.setFirstName("Nicolas");
		userCreateDto.setLastName("Fasano");
		userCreateDto.setEmail("adresse@email.com");
		userCreateDto.setPassword("password");
		
		MockHttpServletRequestBuilder request = post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userCreateDto));
		
		mockMvc.perform(request)
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testCreate_blankEmail() throws Exception 
	{
		UserCreateDto userCreateDto = new UserCreateDto();
		userCreateDto.setUsername("NFLorD");
		userCreateDto.setFirstName("Nicolas");
		userCreateDto.setLastName("Fasano");
		userCreateDto.setEmail("  ");
		userCreateDto.setPassword("password");
		
		MockHttpServletRequestBuilder request = post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userCreateDto));
		
		mockMvc.perform(request)
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testCreate_nullField() throws Exception 
	{
		UserCreateDto userCreateDto = new UserCreateDto();
		userCreateDto.setUsername("NFLorD");
		userCreateDto.setFirstName("Nicolas");
		userCreateDto.setLastName("Fasano");
		userCreateDto.setEmail("adresse@email.com");
		userCreateDto.setPassword(null);
		
		MockHttpServletRequestBuilder request = post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userCreateDto));
		
		mockMvc.perform(request)
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testCreate_serviceCall() throws Exception 
	{
		UserCreateDto userCreateDto = new UserCreateDto();
		userCreateDto.setUsername("NFLorD");
		userCreateDto.setFirstName("Nicolas");
		userCreateDto.setLastName("Fasano");
		userCreateDto.setEmail("adresse@email.com");
		userCreateDto.setPassword("password");
		
		MockHttpServletRequestBuilder request = post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userCreateDto));
		
		mockMvc.perform(request);
		
		ArgumentCaptor<UserCreateDto> userCreateDtoCaptor = ArgumentCaptor.forClass(UserCreateDto.class);
		verify(userService, times(1)).create(userCreateDtoCaptor.capture());
		assertThat(userCreateDtoCaptor.getValue().getUsername()).isEqualTo("NFLorD");
		assertThat(userCreateDtoCaptor.getValue().getEmail()).isEqualTo("adresse@email.com");
	}
	
	@Test
	public void testUpdate_success() throws Exception 
	{
		UserUpdateDto userUpdateDto = new UserUpdateDto();
		userUpdateDto.setId(9999L);
		userUpdateDto.setUsername("AtAb");
		userUpdateDto.setFirstName("Nicolas");
		userUpdateDto.setLastName("Fasano");
		userUpdateDto.setEmail("adresse@email.com");
		userUpdateDto.setPassword("password");
		
		MockHttpServletRequestBuilder request = put("/users/{id}", 9999L)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(userUpdateDto));
		
		when(userService.update(
			ArgumentMatchers.any(UserUpdateDto.class)
		))
			.thenReturn(true);
		
		mockMvc.perform(request)
			.andExpect(status().isNoContent());
	}
	
	@Test
	public void testUpdate_notFound() throws Exception 
	{
		UserUpdateDto userUpdateDto = new UserUpdateDto();
		userUpdateDto.setId(9999L);
		userUpdateDto.setUsername("AtAb");
		userUpdateDto.setFirstName("Nicolas");
		userUpdateDto.setLastName("Fasano");
		userUpdateDto.setEmail("adresse@email.com");
		userUpdateDto.setPassword("password");
		
		MockHttpServletRequestBuilder request = put("/users/{id}", 9999L)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(userUpdateDto));
		
		when(userService.update(
			ArgumentMatchers.any(UserUpdateDto.class)
		))
			.thenReturn(false);
		
		mockMvc.perform(request)
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void testUpdate_usernameTooLong() throws Exception 
	{
		UserUpdateDto userUpdateDto = new UserUpdateDto();
		userUpdateDto.setId(9999L);
		userUpdateDto.setUsername("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopq");
		userUpdateDto.setFirstName("Nicolas");
		userUpdateDto.setLastName("Fasano");
		userUpdateDto.setEmail("adresse@email.com");
		userUpdateDto.setPassword("password");
		
		MockHttpServletRequestBuilder request = put("/users/{id}", 9999L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userUpdateDto));
		
		mockMvc.perform(request)
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testUpdate_emptyUsername() throws Exception 
	{
		UserUpdateDto userUpdateDto = new UserUpdateDto();
		userUpdateDto.setId(9999L);
		userUpdateDto.setUsername("");
		userUpdateDto.setFirstName("Nicolas");
		userUpdateDto.setLastName("Fasano");
		userUpdateDto.setEmail("adresse@email.com");
		userUpdateDto.setPassword("password");
		
		MockHttpServletRequestBuilder request = put("/users/{id}", 9999L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userUpdateDto));
		
		mockMvc.perform(request)
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testUpdate_blankEmail() throws Exception 
	{
		UserUpdateDto userUpdateDto = new UserUpdateDto();
		userUpdateDto.setId(9999L);
		userUpdateDto.setUsername("AtAb");
		userUpdateDto.setFirstName("Nicolas");
		userUpdateDto.setLastName("Fasano");
		userUpdateDto.setEmail(" ");
		userUpdateDto.setPassword("password");
		
		MockHttpServletRequestBuilder request = put("/users/{id}", 9999L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userUpdateDto));
		
		mockMvc.perform(request)
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testUpdate_nullField() throws Exception 
	{
		UserUpdateDto userUpdateDto = new UserUpdateDto();
		userUpdateDto.setId(9999L);
		userUpdateDto.setUsername("AtAb");
		userUpdateDto.setFirstName("Nicolas");
		userUpdateDto.setLastName("Fasano");
		userUpdateDto.setEmail("adresse@email.com");
		userUpdateDto.setPassword(null);
		
		MockHttpServletRequestBuilder request = put("/users/{id}", 9999L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userUpdateDto));
		
		mockMvc.perform(request)
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testUpdate_serviceCall() throws Exception 
	{
		UserUpdateDto userUpdateDto = new UserUpdateDto();
		userUpdateDto.setId(9999L);
		userUpdateDto.setUsername("AtAb");
		userUpdateDto.setFirstName("Nicolas");
		userUpdateDto.setLastName("Fasano");
		userUpdateDto.setEmail("adresse@email.com");
		userUpdateDto.setPassword("password");
		
		MockHttpServletRequestBuilder request = put("/users/{id}", 9999L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userUpdateDto));
		
		mockMvc.perform(request);
		
		ArgumentCaptor<UserUpdateDto> userUpdateDtoCaptor = ArgumentCaptor.forClass(UserUpdateDto.class);
		verify(userService, times(1)).update(userUpdateDtoCaptor.capture());
		assertThat(userUpdateDtoCaptor.getValue().getUsername()).isEqualTo("AtAb");
		assertThat(userUpdateDtoCaptor.getValue().getEmail()).isEqualTo("adresse@email.com");
	}
	
	@Test
	public void testDelete_success() throws Exception 
	{
		MockHttpServletRequestBuilder request = delete("/users/{id}", 9999L);
		
		when(userService.delete(
			ArgumentMatchers.anyLong()
		))
			.thenReturn(true);
		
		mockMvc.perform(request)
			.andExpect(status().isNoContent());
	}
	
	@Test
	public void testDelete_notFound() throws Exception 
	{
		MockHttpServletRequestBuilder request = delete("/users/{id}", 9999L);
		
		when(userService.delete(
			ArgumentMatchers.anyLong()
		))
			.thenReturn(false);
		
		mockMvc.perform(request)
			.andExpect(status().isNotFound());
	}
}
