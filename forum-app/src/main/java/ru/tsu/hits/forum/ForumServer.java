package ru.tsu.hits.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication()
@ConfigurationPropertiesScan("ru.tsu.hits.forum")
public class ForumServer {

	public static void main(String[] args) {
		SpringApplication.run(ForumServer.class, args);
	}

}
