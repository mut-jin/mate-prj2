package com.example.demo.config;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.annotation.*;
import jakarta.servlet.*;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.regions.*;
import software.amazon.awssdk.services.s3.*;

@Configuration
@EnableMethodSecurity
public class CustomConfig {
	
	   @Value("${aws.accessKeyId}")
	   private String accessKey;

	   @Value("${aws.secretAccessKey}")
	   private String secretKey;

	   @Value("${aws.s3.bucketUrl}")
	   private String bucketUrl;

	   @Autowired
	   private ServletContext application;

	   // 빈이 만들어지자마자 바로 실행해라
	   @PostConstruct
	   public void init() {
	      application.setAttribute("bucketUrl", bucketUrl);
	   }

	   @Bean
	   public S3Client s3client() {

	      AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
	      AwsCredentialsProvider provider = StaticCredentialsProvider.create(credentials);

	      S3Client s3client = S3Client.builder()
	            .credentialsProvider(provider)
	            .region(Region.AP_NORTHEAST_2)
	            .build();

	      return s3client;

	   }
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		
		http.authorizeHttpRequests().anyRequest().permitAll();
		
		return http.build();
		
		
	}
	

	
}
