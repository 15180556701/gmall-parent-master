package com.atguigu.gmall.product.controller;


import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.config.minio.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/admin/product")
@RestController
public class FileController {


    @Autowired
    FileUploadService fileUploadService;



    @PostMapping("/fileUpload")
    public Result uploadFile(@RequestPart("file")MultipartFile file) throws Exception {
        System.out.println("文件名："+file.getOriginalFilename());
        System.out.println("文件大小："+file.getSize());

        //文件上传
        String url = fileUploadService.upload(file);
        return Result.ok(url);
    }
}
