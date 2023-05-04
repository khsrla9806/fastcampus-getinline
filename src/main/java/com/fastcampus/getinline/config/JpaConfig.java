package com.fastcampus.getinline.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // Auditing 기능을 추가
@Configuration
public class JpaConfig {

}
