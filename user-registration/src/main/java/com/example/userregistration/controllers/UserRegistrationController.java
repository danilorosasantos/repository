package com.example.userregistration.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.userregistration.beans.GeoLocalisation;
import com.example.userregistration.beans.User;
import com.example.userregistration.exceptions.ExternalIPException;
import com.example.userregistration.services.GeoLocalisationService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
public class UserRegistrationController {

	@Autowired
	private GeoLocalisationService geoLocalisationService;
	
	private static String COUNTRY="Canada";

	@Operation(summary = "Register user into application")
	@PostMapping("/user-registration/register")
	public String registerUser(@Valid @RequestBody User user) {
		UUID uuid = UUID.randomUUID();
		GeoLocalisation geoLocalisation = geoLocalisationService.getGeoLocalisation(user.getIpAddress(), null, null,
				null);
		if (geoLocalisation.getCountry() == null || !geoLocalisation.getCountry().equals(COUNTRY)) {
			throw new ExternalIPException();
		}
		return uuid.toString().concat(": Welcome ").concat(user.getUsername()).concat(" from ").concat(geoLocalisation.getCity());
	}
}
