package com.example.userregistration;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class AppConfig {

	@Autowired
	private MessageSource messageSource;

	@Bean("clienteRest")
	public RestTemplate registrarRestTemplate() {
		return new RestTemplate();
	}

	
	@Bean
	 public OpenAPI openApiInformation() {
	  Server localServer = new Server()
	                      .url(messageSource.getMessage("url", null, Locale.CANADA))
	                      .description(messageSource.getMessage("url_description", null, Locale.CANADA));

	  Contact contact = new Contact()
	                    .email(messageSource.getMessage("email_contact", null, Locale.CANADA))
	                    .name(messageSource.getMessage("name_contact", null, Locale.CANADA));

	  Info info = new Info()
	              .contact(contact)
	              .title(messageSource.getMessage("service_name", null, Locale.CANADA))
	              .version(messageSource.getMessage("service_version", null, Locale.CANADA))
	              ;

	  return new OpenAPI().info(info).addServersItem(localServer);
	 }
}
