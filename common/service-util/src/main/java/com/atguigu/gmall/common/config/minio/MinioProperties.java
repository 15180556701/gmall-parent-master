package com.atguigu.gmall.common.config.minio;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "app.minio")
@Component
@Data
public class MinioProperties {

    String secretKey;
    String accessKey;
    String endpoint;
    String bucketName;
}
