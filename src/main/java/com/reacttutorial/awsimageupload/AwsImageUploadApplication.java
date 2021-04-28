package com.reacttutorial.awsimageupload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
//@ComponentScan("com/reacttutorial/awsimageupload")
public class AwsImageUploadApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwsImageUploadApplication.class, args);
	}

}
