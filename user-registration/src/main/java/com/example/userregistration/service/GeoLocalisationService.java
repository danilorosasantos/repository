package com.example.userregistration.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.userregistration.model.GeoLocalisation;

@Service("geoLocalisationService")
public class GeoLocalisationService implements IGeoLocalisationService {

	@Autowired
	private RestTemplate clienteRest;
	
	private static String SERVICE_IP_ADDRESS="http://ip-api.com/json/{query}";

	public GeoLocalisation getGeoLocalisation(String ipAddress, String fields, String lang,
			String callback) {
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("query", ipAddress);
		pathVariables.put("fields", fields);
		pathVariables.put("lang", lang);
		pathVariables.put("callback", callback);

		GeoLocalisation geoLocalisation = clienteRest.getForObject(SERVICE_IP_ADDRESS,
				GeoLocalisation.class, pathVariables);
		return geoLocalisation;
	}

}