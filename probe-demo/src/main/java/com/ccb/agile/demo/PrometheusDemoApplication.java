package com.ccb.agile.demo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class PrometheusDemoApplication {
	public static void main(String[] args) throws NoSuchAlgorithmException {
		SpringApplication application = new SpringApplication(PrometheusDemoApplication.class);
		application.addListeners(new ApplicationPidFileWriter("/tmp/pid.file"));
		application.run(args);
		//digest();
	}

	public static void digest() throws NoSuchAlgorithmException {
		String usernameAndPassword = "super:Dvop_2981";
		byte digest[] = MessageDigest.getInstance("SHA1").digest(usernameAndPassword.getBytes());
		Base64 base64 = new Base64();
		String encodeToString = base64.encodeAsString(digest);
		System.out.println(encodeToString);
	}

}
