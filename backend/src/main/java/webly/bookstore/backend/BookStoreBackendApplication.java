package webly.bookstore.backend;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BookStoreBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookStoreBackendApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer(){
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry){
				registry.
						addMapping("/**").
						allowedHeaders("Authorization", "Content-Type").
						allowedMethods("POST", "GET").
						allowedOrigins("http://localhost:8000");
			}
		};
	}
}
