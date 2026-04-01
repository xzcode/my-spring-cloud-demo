package com.mydemo.common.config.mongo;

import com.mongodb.ReadPreference;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

/**
 * Secondary MongoDB 配置
 * <p>ReadPreference.secondary() 读偏好，用于读多写少的查询分流</p>
 * <p>通过配置 app.mongo.secondary.enabled=true 启用</p>
 */
@Configuration
@ConditionalOnProperty(prefix = "app.mongo.secondary", name = "enabled", havingValue = "true")
public class SecondaryMongoConfig {

    @Bean
    @Qualifier("secondaryMongoTemplate")
    public MongoTemplate secondaryMongoTemplate(MongoDatabaseFactory factory, MappingMongoConverter converter) {
        MongoTemplate template = new MongoTemplate(factory, converter);
        template.setReadPreference(ReadPreference.secondary());
        return template;
    }
}
