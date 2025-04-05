package vn.eren.authenticationstructuresp.config.redis;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.redisson.jcache.configuration.RedissonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.eren.authenticationstructuresp.config.properties.CacheProperties;

import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import java.net.URI;
import java.util.concurrent.TimeUnit;

import static vn.eren.authenticationstructuresp.repository.PermissionsRepository.PERMISSION_BY_ID_CACHE;
import static vn.eren.authenticationstructuresp.repository.PermissionsRepository.PERMISSION_BY_NAME_CACHE;
import static vn.eren.authenticationstructuresp.repository.UsersRepository.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    @Autowired(required = false)
    GitProperties gitProperties;

    @Autowired(required = false)
    BuildProperties buildProperties;

    private final CacheProperties cacheProperties;

    public CacheConfiguration(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }

    @Bean
    public javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration() {
        final MutableConfiguration<Object, Object> jcacheConfig = new MutableConfiguration<>();

        final var redisUri = URI.create(cacheProperties.getServer()[0]);
        final var config = new Config();
        if (cacheProperties.getCluster()) {
            final var clusterServersConfig = config
                    .useClusterServers()
                    .setMasterConnectionPoolSize(cacheProperties.getConnectionPoolSize())
                    .setMasterConnectionMinimumIdleSize(cacheProperties.getConnectionMinimumIdleSize())
                    .setSubscriptionConnectionPoolSize(cacheProperties.getSubscriptionConnectionPoolSize())
                    .setSubscriptionConnectionMinimumIdleSize(cacheProperties.getSubscriptionConnectionMinimumIdleSize())
                    .setRetryAttempts(3)
                    .addNodeAddress(cacheProperties.getServer()[0]);
            if (redisUri.getUserInfo() != null) {
                clusterServersConfig.setPassword(redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(':') + 1));
            }
        } else {
            final var singleServerConfig = config
                    .useSingleServer()
                    .setConnectionPoolSize(cacheProperties.getConnectionPoolSize())
                    .setConnectionMinimumIdleSize(cacheProperties.getConnectionMinimumIdleSize())
                    .setSubscriptionConnectionPoolSize(cacheProperties.getSubscriptionConnectionPoolSize())
                    .setSubscriptionConnectionMinimumIdleSize(cacheProperties.getSubscriptionConnectionMinimumIdleSize())
                    .setRetryAttempts(3)
                    .setAddress(cacheProperties.getServer()[0]);
            if (redisUri.getUserInfo() != null) {
                singleServerConfig.setPassword(redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(':') + 1));
            }
        }

        jcacheConfig.setStatisticsEnabled(true);
        jcacheConfig.setExpiryPolicyFactory(
                CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, cacheProperties.getExpiration()))
        );
        return RedissonConfiguration.fromInstance(Redisson.create(config), jcacheConfig);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer(javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration) {
        return cm -> {
            createCache(cm, USER_BY_USERNAME_CACHE, jcacheConfiguration);
            createCache(cm, USER_BY_ID_CACHE, jcacheConfiguration);
            createCache(cm, USER_NAME_BY_ID_CACHE, jcacheConfiguration);
            createCache(cm, PERMISSION_BY_ID_CACHE, jcacheConfiguration);
            createCache(cm, PERMISSION_BY_NAME_CACHE, jcacheConfiguration);
        };
    }

    private void createCache(
            javax.cache.CacheManager cm,
            String cacheName,
            javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration
    ) {
        final var cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
