package com.atguigu.gmall.product;

import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import io.minio.errors.MinioException;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class MinioTest {

    @Test
    public void uploadTest() throws Exception {
        try {
            //1、创建客户端：使用MinIO服务的URL，端口，Access key和Secret key创建一个MinioClient对象
            MinioClient minioClient = new MinioClient
                    ("http://192.168.200.100:9000",
                            "admin",
                            "admin123456");

            //2、检查桶是否存在
            boolean isExist = minioClient.bucketExists("mall");
            if(isExist) {
                System.out.println("桶存在.可以上传...");
            } else {
                // 3、不存在可以创建
                minioClient.makeBucket("mall");
            }

            //3、上传：使用putObject上传一个文件到存储桶中。
            /**
             * String bucketName,
             * String objectName,
             * InputStream stream,
             * PutObjectOptions options: 上传参数
             */
            FileInputStream fis = new FileInputStream("C:\\Users\\53409\\Desktop\\尚品汇\\资料\\03 商品图片\\品牌\\oppo.png");
            //上传参数  long objectSize, long partSize
            PutObjectOptions options = new PutObjectOptions(fis.available(),-1L);
            minioClient.putObject("mall","1.png",fis,options);
            System.out.println("上传成功");
        } catch(MinioException e) {
            System.err.println("上传失败: " + e);
        }
    }

}
