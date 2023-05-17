package com.leaf.controller;

import com.leaf.annotation.SystemLog;
import com.leaf.domain.ResponseResult;
import com.leaf.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api(tags = "文件上传",description = "文件上传相关接口")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @ApiOperation(value = "图片上传")
    @SystemLog(businessName = "图片上传")
    @PostMapping("/upload")
    public ResponseResult upload(MultipartFile img){
        return uploadService.uploadImg(img);
    }
}
