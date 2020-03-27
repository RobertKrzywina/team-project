package com.letswork.api.user.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UserConfiguration {

    @Bean
    UserFacade facade(UserRepository repository) {
        UserService service = new UserService(repository, new UserFactory());
        return new UserFacade(service);
    }
}
