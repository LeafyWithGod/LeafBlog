package com.leaf;

import com.google.gson.Gson;
import com.leaf.constants.ResultStatus;
import com.leaf.domain.entity.Article;
import com.leaf.mapper.ArticleMapper;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.util.List;

@SpringBootTest
public class LeafBlogApplicationTest {
    @Value("${oss.aKey}")
    String aKey;
    @Value("${oss.sKey}")
    String sKey;
    @Value("${oss.bucket}")
    String bucket;

    @Autowired
    private ArticleMapper articleMapper;

    @Test
    public void articleList(){
        List<Article> articleList = articleMapper.hotArticleList(ResultStatus.ArticleZero);
        System.out.println(articleList);
    }

    @Test
    public void articleOne(){
        Article articleList = articleMapper.selectArticleOne(1L,ResultStatus.ArticleZero);
        System.out.println(articleList);
    }

    @Test
    public void qiniuyun(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
//...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
//        String accessKey = "Xgu6nMtLr_aiS8asK3wwQI54Ao8ti0t33eDdYDzZ";
//        String secretKey = "Ue7c9rd3NYMceobffTgPQX_ChPmoFQPsYewui71F";
//        String bucket = "leaf-blog";

//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;

        try {
//            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
//            ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);
            FileInputStream fileInputStream = new FileInputStream("D:\\idea\\Java\\LeafBlog\\blog-vue\\sg-blog-vue\\static\\img\\tou.jpg");
            Auth auth = Auth.create(aKey, sKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(fileInputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }

    }

}
