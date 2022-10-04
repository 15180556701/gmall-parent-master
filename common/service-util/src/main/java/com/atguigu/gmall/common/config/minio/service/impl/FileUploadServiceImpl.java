package com.atguigu.gmall.common.config.minio.service.impl;

import com.atguigu.gmall.common.config.minio.MinioProperties;
import com.atguigu.gmall.common.config.minio.service.FileUploadService;
import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;



public class FileUploadServiceImpl implements FileUploadService {



    @Autowired
    MinioClient client;

    @Autowired
    MinioProperties minioProperties;

    @Override
    public String upload(MultipartFile file) throws Exception {
        //文件名唯一
        String filename = UUID.randomUUID().toString().replace("-","") +"_"+file.getOriginalFilename();
        InputStream fis = file.getInputStream();

        //1、创建MinioClient
        //2、判断桶是否存在
        //======自动配置帮我们做好了=======

        //3、上传文件
        PutObjectOptions options = new PutObjectOptions(file.getSize(),-1L);
        client.putObject(minioProperties.getBucketName(),filename,fis,options);

        //4、拿到文件的访问路径 http://192.168.200.100:9000/mall/1.png
        String url = minioProperties.getEndpoint()+"/"+minioProperties.getBucketName()+"/"+filename;
        return url;
    }
}
