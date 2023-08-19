package com.soundify;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.soundify.aws_S3.AWSS3Service;

@SpringBootApplication(scanBasePackages = "com.soundify")
public class SpringBootTemplateApplication {
	


	public static void main(String[] args) {
		//SpringApplication.run(SpringBootTemplateApplication.class, args);
		
	ApplicationContext context = SpringApplication.run(SpringBootTemplateApplication.class, args);
	
	AWSS3Service aws =context.getBean(AWSS3Service.class);
	
		aws.getSongFileNames();
	}
	
	// Configure ModelMapper(any 3rd party object) Bean in Spring boot application class(since this class
		// is
		// implicitly annotated with @Configuration)
		// Meaning -You can add @Bean methods ONLY in such config classes
		@Bean
		public ModelMapper modelMapper() {
			ModelMapper mapper = new ModelMapper();
			//Strict mode => While mapping , src prop names n data types MUST with dest type
			mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			return mapper;
		}
		
	

}
