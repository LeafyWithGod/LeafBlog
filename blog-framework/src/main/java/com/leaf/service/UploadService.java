package com.leaf.service;

import com.leaf.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    public ResponseResult uploadImg(MultipartFile img);
}
