package com.example.userregistration.services;

import com.example.userregistration.beans.GeoLocalisation;

public interface IGeoLocalisationService {
	public GeoLocalisation getGeoLocalisation(String ipAddress, String fields, String lang,
			String callback);
}
