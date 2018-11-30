plugins {
    id("org.springframework.boot") version "2.1.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
}
dependencies {
    compile("org.springframework.boot:spring-boot-starter")
    compile("org.springframework.boot:spring-boot-configuration-processor")
    project(":spring-boot")
    compile("org.apache.zookeeper:zookeeper:3.5.4-beta")
}
springBoot {
    buildInfo {
        properties {
            mainClassName = "ml.alio.frame.spring.boot.starter.zookeeper.ZookeeperAutoConfiguration"
        }
    }
}