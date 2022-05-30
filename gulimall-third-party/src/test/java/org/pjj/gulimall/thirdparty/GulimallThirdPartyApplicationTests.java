package org.pjj.gulimall.thirdparty;

import com.aliyun.oss.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@SpringBootTest
class GulimallThirdPartyApplicationTests {

    @Autowired
    OSSClient ossClient; // 注入 ossClient对象 来完成文件上传

    @Test
    void contextLoads() {
    }

    /**
     * 使用 阿里云 OSS 的原生依赖  上传文件的方式
     *
     * 1. 引入依赖
     *         <dependency>
     *             <groupId>com.aliyun.oss</groupId>
     *             <artifactId>aliyun-sdk-oss</artifactId>
     *             <version>3.10.2</version>
     *         </dependency>
     *
     * 2. 引入依赖后 根据 accessKeyId, accessKeySecret, bucketName 构建出 ossClient 对象
     * 使用 ossClient对象通过putObject完成文件上传
     *
     * @throws Exception
     */
    @Test
    void test04() throws Exception {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "";
        String accessKeySecret = "";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "gulimall-2022-5-29";
        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
        String objectName = "2022/5/30/咪咪.JPG";
        // 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
        String filePath= "C:/Users/PengJiaJun/Desktop/咪咪.JPG";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        //文件上传之后的url 那么文件URL的格式为https://BucketName.Endpoint/ObjectName。
        // https://gulimall-2022-5-29.oss-cn-beijing.aliyuncs.com/2022/5/30/咪咪.JPG

        try {
            InputStream inputStream = new FileInputStream(filePath);

            // 创建PutObject请求。
            ossClient.putObject(bucketName, objectName, inputStream);

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    /**
     * 使用 与 springboot集成的 阿里云OSS 来完成文件上传
     * (这种是 阿里云对象存储-普通上传方式: 也就是 前端上传图片到 我们自己的服务端(springboot), 后端再拿着 accessKey, secretKey 把图片上传, 最后返回图片url )
     * (这样很耗费资源)
     *
     * 1. 引入依赖
     *         <dependency>
     *             <groupId>com.alibaba.cloud</groupId>
     *             <artifactId>spring-cloud-alicloud-oss</artifactId>
     *         </dependency>
     *
     * 2. 配置
     * spring:
     *   cloud:
     *     alicloud:
     *       # 配置 阿里云OSS 的access-key, secret-key, endpoint
     *       access-key: xxx
     *       secret-key: xxx
     *       oss:
     *         endpoint: oss-cn-beijing.aliyuncs.com
     *
     * 3. @Autowired
     *    OSSClient ossClient;
     *
     * 4. 使用 ossClient对象通过putObject完成文件上传
     *
     * 对比原生的上传方式
     * 使用 与 springboot集成的 阿里云OSS 的方式就是 相当于简化了 ossClient 的创建
     * 原生的方式需要自己构建出 ossClient
     * springboot方式就是 可以使用 @Autowired 注入 OSSClient 对象 从而使用
     *
     */
    @Test
    void test5() throws FileNotFoundException {
        String bucketName = "gulimall-2022-5-29";
        String objectName = "CS2.jpg";
        String filePath= "C:/Users/PengJiaJun/Desktop/cs2.jpg";

        InputStream inputStream = new FileInputStream(filePath);

        // 创建PutObject请求。
        this.ossClient.putObject(bucketName, objectName, inputStream);
    }

}
