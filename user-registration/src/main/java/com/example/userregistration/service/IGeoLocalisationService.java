package com.example.userregistration.service;

import com.example.userregistration.model.GeoLocalisation;

public interface IGeoLocalisationService {
	public GeoLocalisation getGeoLocalisation(String ipAddress, String fields, String lang,
			String callback);
}
