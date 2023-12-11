package com.example.userregistration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.userregistration.beans.GeoLocalisation;
import com.example.userregistration.beans.User;
import com.example.userregistration.controllers.UserRegistrationController;
import com.example.userregistration.services.GeoLocalisationService;

@RunWith(SpringRunner.class)
@WebMvcTest(UserRegistrationController.class)
public class UserRegistrationApplicationTests {
	@Autowired
	private MockMvc mvc;

	@MockBean
	private GeoLocalisationService geoLocalisationService;

	private User user;

	@Autowired
	private MessageSource messageSource;

	@BeforeEach
	public void before() {
		when(geoLocalisationService.getGeoLocalisation(any(String.class), any(), any(), any()))
				.thenReturn(new GeoLocalisation("Montreal", "Canada"));
		user = new User();
		user.setUsername("Danilo");
		user.setPassword("ss%1ssA*sssss");
		user.setIpAddress("24.48.0.1");
	}

	@Test
	void registerUserWithSucess() throws Exception {
		MvcResult result = mvc
				.perform(
						MockMvcRequestBuilders.post("/user-registration/register").contentType("application/json")
								.content("{\"username\":\"".concat(user.getUsername()).concat("\",\"password\":\"")
										.concat(user.getPassword()).concat("\",\"ipAddress\":\"").concat(user.getIpAddress()).concat("\"}")))
				.andReturn();
		int indexRandomUUID = result.getResponse().getContentAsString().indexOf(":");
		String randomUUID = result.getResponse().getContentAsString().substring(0, indexRandomUUID);
		String expectedResponse = messageSource.getMessage("register_user.welcome_message", new Object[] { randomUUID },
				Locale.CANADA);
		assertEquals(expectedResponse, result.getResponse().getContentAsString());
	}

	@Test
	void registerUserReturnsInvalidPassword() throws Exception {
		user.setPassword("ss1ssA*sssss");
		MvcResult result = mvc
				.perform(
						MockMvcRequestBuilders.post("/user-registration/register").contentType("application/json")
								.content("{\"username\":\"".concat(user.getUsername()).concat("\",\"password\":\""
										).concat(user.getPassword()).concat("\",\"ipAddress\":\"").concat(user.getIpAddress()).concat( "\"}")))
				.andReturn();
		String expectedResponse = messageSource.getMessage("register_user.invalid_password", null, Locale.CANADA);
		assertEquals(expectedResponse, result.getResponse().getContentAsString());
	}

	@Test
	void registerUserReturnsInvalidPasswordSize() throws Exception {
		user.setPassword("ss1A%ss");
		MvcResult result = mvc
				.perform(
						MockMvcRequestBuilders.post("/user-registration/register").contentType("application/json")
								.content("{\"username\":\"".concat(user.getUsername()).concat("\",\"password\":\""
										).concat(user.getPassword()).concat("\",\"ipAddress\":\"").concat(user.getIpAddress()).concat("\"}")))
				.andReturn();
		String expectedResponse = messageSource.getMessage("register_user.invalid_password_size", null, Locale.CANADA);
		assertEquals(expectedResponse, result.getResponse().getContentAsString());
	}

	@Test
	void registerUserReturnsPasswordBlankOrNull() throws Exception {
		MvcResult result = mvc.perform(
				MockMvcRequestBuilders.post("/user-registration/register").contentType("application/json").content(
						"{\"username\":\"".concat(user.getUsername()).concat("\",\"ipAddress\":\"").concat(user.getIpAddress()).concat("\"}")))
				.andReturn();
		String expectedResponse = messageSource.getMessage("register_user.password_blank_or_null", null, Locale.CANADA);
		assertEquals(expectedResponse, result.getResponse().getContentAsString());
	}

	@Test
	void registerUserReturnsUsernameBlankOrNull() throws Exception {
		user.setUsername("");
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/user-registration/register")
				.contentType("application/json").content("{\"username\":\"\",\"password\":\"".concat(user.getPassword()
						).concat("\",\"ipAddress\":\"").concat(user.getIpAddress()).concat("\"}")))
				.andReturn();
		String expectedResponse = messageSource.getMessage("register_user.username_blank_or_null", null, Locale.CANADA);
		assertEquals(expectedResponse, result.getResponse().getContentAsString());
	}

	@Test
	void registerUserReturnsIpAddressBlankOrNull() throws Exception {
		user.setIpAddress("");
		MvcResult result = mvc
				.perform(
						MockMvcRequestBuilders.post("/user-registration/register").contentType("application/json")
								.content("{\"username\":\"".concat(user.getUsername()).concat("\",\"password\":\"").concat(
										user.getPassword()).concat("\",\"ipAddress\":\"").concat(user.getIpAddress() + "\"}")))
				.andReturn();
		String expectedResponse = messageSource.getMessage("register_user.ip_address_blank_or_null", null,
				Locale.CANADA);
		assertEquals(expectedResponse, result.getResponse().getContentAsString());
	}

}
