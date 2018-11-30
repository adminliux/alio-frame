dependencies {
    implementation("javax.servlet:javax.servlet-api:+")
    implementation("org.springframework:spring-webmvc:+")
    implementation("com.google.zxing:core:3.3.3")
    implementation("org.apache.commons:commons-lang3:+")
    implementation("javax.persistence:persistence-api:1.0.2")
    implementation("tk.mybatis:mapper:+")
    implementation("commons-io:commons-io:+")
    implementation("commons-codec:commons-codec:+")
    implementation("ml.alio:base64-common:1.0.1")
    implementation("org.apache.velocity:velocity:+")
    implementation("commons-beanutils:commons-beanutils:1.9.3")
    // https://mvnrepository.com/artifact/org.aspectj/aspectjrt
    compileOnly("org.aspectj:aspectjrt:+")
    implementation("io.swagger:swagger-core:1.5.21")
    // https://mvnrepository.com/artifact/com.google.code.gson/gson
    implementation("com.google.code.gson:gson:+")
    compileOnly("log4j:log4j:1.2.17")
    // https://mvnrepository.com/artifact/org.springframework/spring-tx
    compileOnly("org.springframework:spring-tx:+")
    // https://mvnrepository.com/artifact/com.github.pagehelper/pagehelper
    compileOnly("com.github.pagehelper:pagehelper:+")
    compileOnly("org.springframework.cloud:spring-cloud-starter-config:+")
    implementation("com.alibaba:fastjson:1.2.54")
}
