package org.laboys.better.spring.autoconfigure.mongo;

import com.mongodb.client.MongoClient;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(MongoClient.class)
@EnableConfigurationProperties(MongoProperties.class)
@AutoConfigureBefore(MongoDataAutoConfiguration.class)
public class MongoAutoConfiguration {

    /**
     * 用于去除MongoDB默认保存的_class
     *
     * 有一组类Apple继承于Fruit, 当使用如下方法
     *      fruitRepository.findByName("xx")
     * 获取mongo中的apple文档时, 如果有_class字段存在, 就可以自动进行*子类映射*返回Apple类,
     * 如果没有_class字段存在, 只根据ODM属性映射返回Fruit类
     */
    @Bean
    @ConditionalOnMissingBean(MongoConverter.class)
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @ConditionalOnBean({MongoDatabaseFactory.class, MongoMappingContext.class})
    @ConditionalOnProperty(prefix = MongoProperties.PREFIX, name = "remove-class-mapper", havingValue = "true")
    public MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory factory, MongoMappingContext context,
                                                       MongoCustomConversions conversions) {
        DbRefResolver refResolver = new DefaultDbRefResolver(factory);
        MappingMongoConverter converter = new MappingMongoConverter(refResolver, context);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        converter.setCustomConversions(conversions);

        return converter;
    }

}
