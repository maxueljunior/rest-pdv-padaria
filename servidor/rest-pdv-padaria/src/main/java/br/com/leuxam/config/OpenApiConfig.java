package br.com.leuxam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
	
	@Bean
	OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("PDV")
						.version("v1")
						.description("PDV voltado para comercios")
						.termsOfService("https://maxsofts.com.br/softwares")
						.license(
							new License()
								.name("Apache 2.0")
								.url("https://maxsofts.com.br/softwares")));
	}
	
}
