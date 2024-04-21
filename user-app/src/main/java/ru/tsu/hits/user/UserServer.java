package ru.tsu.hits.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import ru.tsu.hits.common.security.SbAuthSecurityConfig;

@ConfigurationPropertiesScan("ru.tsu.hits.user")
@SpringBootApplication()
@Import({SbAuthSecurityConfig.class})
public class  UserServer {

	public static void main(String[] args) {
		SpringApplication.run(UserServer.class, args);
	}

}
