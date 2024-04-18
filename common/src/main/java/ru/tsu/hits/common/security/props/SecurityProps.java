package ru.tsu.hits.common.security.props;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("app.security")
@Component
@Getter
@Setter
@ToString
public class SecurityProps {

    private SecurityJwtTokenProps jwtToken;

    private SecurityIntegrationsProps integrations;

}
