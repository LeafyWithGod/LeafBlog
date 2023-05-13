package com.leaf.service.impl;

import com.google.gson.Gson;
import com.leaf.domain.ResponseResult;
import com.leaf.enums.AppHttpCodeEnum;
import com.leaf.exception.SystemException;
import com.leaf.service.UploadService;
import com.leaf.utils.PathUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class UploadServiceImpl implements UploadService {

    @Value("${oss.aKey}")
    String aKey;
    @Value("${oss.sKey}")
    String sKey;
    @Value("${oss.bucket}")
    String bucket;

    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        //判断文件类型
        //获取原始文件名进行判断
        String filename = img.getOriginalFilename();
        if(!filename.endsWith(".png")&&!filename.endsWith(".jpg")){
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
        //上传
        String url = qiniuyun(img);
        return ResponseResult.okResult(url);
    }

    /**
     * 七牛云上传
     * @param img
     */
    public String qiniuyun(MultipartFile img){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);

        //调用方法生成路径+文件名
        String key = PathUtils.generateFilePath(img.getOriginalFilename());

        try {
            InputStream fileInputStream = img.getInputStream();
            Auth auth = Auth.create(aKey, sKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(fileInputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                return "http://rul7claz9.hn-bkt.clouddn.com/"+key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    System.out.println(ex2);
                }
            }
        } catch (Exception ex) {
            //ignore
            System.out.println(ex);
        }
        throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
    }
}
