package com.odontoApp.api.infra.springdoc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SpringDocConfigurations {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
			.components(new Components()
				.addSecuritySchemes("bearer-key",
				new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
				.info(new Info()
					.title("OdontoApp API")
					.description("API Rest da aplicação odontoApp, contendo as funcionalidades de CRUD de dentistas e pacientes, de informações sobre convênios, procedimentos e histórico clínico, além do agendamento e cancelamento de consultas")
					.contact(new Contact()
							.name("Diego Henrique")
							.email("diegohenriquedev@gmail.com"))
 				.license(new License()
					.name("Apache 2.0")
					.url("http://odontoApp/api/licenca")));
	}
}
