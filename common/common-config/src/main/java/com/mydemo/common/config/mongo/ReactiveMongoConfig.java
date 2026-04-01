package com.mydemo.common.config.mongo;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.convert.NoOpDbRefResolver;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * Reactive MongoDB 配置
 * <p>通过配置 app.mongo.reactive.enabled=true 启用</p>
 */
@Configuration
@ConditionalOnClass(ReactiveMongoDatabaseFactory.class)
@ConditionalOnProperty(prefix = "app.mongo.reactive", name = "enabled", havingValue = "true")
public class ReactiveMongoConfig {

    @Bean
    public MappingMongoConverter reactiveMappingMongoConverter(
            MongoMappingContext context,
            MongoCustomConversions conversions) {
        MappingMongoConverter converter = new MappingMongoConverter(NoOpDbRefResolver.INSTANCE, context);
        converter.setCustomConversions(conversions);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return converter;
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate(
            ReactiveMongoDatabaseFactory factory,
            MappingMongoConverter reactiveMappingMongoConverter) {
        return new ReactiveMongoTemplate(factory, reactiveMappingMongoConverter);
    }
}
