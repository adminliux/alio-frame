package ml.alio.frame.spring.boot.starter.zookeeper

import org.apache.zookeeper.ZooKeeper
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "zookeeper")
class ZKConfig {
    String hostPort;
    Integer timeout;
    ZooKeeper zk = new ZooKeeper(hostPort, timeout, null);

}