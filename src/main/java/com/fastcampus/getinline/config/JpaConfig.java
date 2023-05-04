package com.fastcampus.getinline.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // Auditing 기능을 추가
@Configuration
public class JpaConfig {
    // 지금은 Properties 설정으로 모두 되기 때문에 설정 원상복구
}
