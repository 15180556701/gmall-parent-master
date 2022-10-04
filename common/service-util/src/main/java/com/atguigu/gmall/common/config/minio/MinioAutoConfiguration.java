package com.atguigu.gmall.common.config.minio;


import com.atguigu.gmall.common.config.minio.service.FileUploadService;
import com.atguigu.gmall.common.config.minio.service.impl.FileUploadServiceImpl;
import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//1、MinioProperties 所有属性和文件进行绑定
//2、MinioProperties 放到容器中
@EnableConfigurationProperties(MinioProperties.class)
@Configuration
public class MinioAutoConfiguration {

    @Autowired
    MinioProperties minioProperties;

    @Bean
    public MinioClient minioClient() throws Exception {
        //1、创建minioClient
        MinioClient client = new MinioClient(minioProperties.getEndpoint(),
                minioProperties.getAccessKey(),
                minioProperties.getSecretKey());

        boolean mall = client.bucketExists(minioProperties.getBucketName());
        if(!mall){
            client.makeBucket(minioProperties.getBucketName());
        }
        return client;
    }


    @Bean
    public FileUploadService fileUploadService(){
        FileUploadServiceImpl service = new FileUploadServiceImpl();
        return service;
    }

}
