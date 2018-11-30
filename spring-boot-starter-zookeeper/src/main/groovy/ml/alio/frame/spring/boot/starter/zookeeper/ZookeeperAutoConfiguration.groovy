package ml.alio.frame.spring.boot.starter.zookeeper

import org.apache.zookeeper.ZooKeeper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(value = ZKConfig.class)
@ConditionalOnProperty(prefix = "alio", value = "enable", matchIfMissing = true)
class ZookeeperAutoConfiguration {
    @Autowired
    ZKConfig zKConfig;

    @Bean
    @ConditionalOnMissingBean(ZooKeeper.class)
    ZooKeeper zooKeeper() {
        return new ZooKeeper(zKConfig.hostPort, zKConfig.timeout, null);
    }
}