package com.atguigu.gulimall.thirdparty;

import com.atguigu.gulimall.thirdparty.component.SmsComponent;
import com.atguigu.gulimall.thirdparty.util.HttpUtils;
import org.apache.http.HttpResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GulimallThirdPartyApplicationTests {
//    @Value("${cloud.storage.bucketName}")
//    String bucketName="gulimall";
//    String bucketName;
//    @Value("${cloud.storage.projectId}")
//    String projectId="certain-torus-315206";
//    String projectId;
//    @Test
//    public void contextLoads() throws IOException {
//        Storage storage = StorageOptions.newBuilder().setProjectId(projectId) .setCredentials(GoogleCredentials.fromStream(new
//                FileInputStream("F:\\key\\CloudStroage\\certain-torus-315206-9a2f62e3a477.json")))
//                .build().getService();;
//        File file = new File("F://excel//数据.xls");
//        FileInputStream inputStream = new FileInputStream(file);
//        MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
//                ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
//        String fileName = multipartFile.getOriginalFilename();
//        BlobId blobId = BlobId.of(bucketName, fileName);
//            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
//            Blob blob = storage.create(blobInfo, multipartFile.getBytes());
//        System.out.println(blob.getMediaLink());
//    }
//    @Resource
//    OSSClient ossClient;
//    @Test
//    public void upload() throws FileNotFoundException {
//        FileInputStream inputStream = new FileInputStream("F://excel//数据.xls");
//        ossClient.putObject("gulimall-cloudstorage","数据.xls",inputStream);
//        ossClient.shutdown();
//        System.out.println("上传完成");
//    }
    @Test
    public void sendSms(){
        String host = "https://dfsns.market.alicloudapi.com";
        String path = "/data/send_sms";
        String method = "POST";
        String appcode = "b1889deddb654657a12bffd220c4daa5";
        Map<String, String> headers = new HashMap<>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<>();
        Map<String, String> bodys = new HashMap<>();
        bodys.put("content", "code:6666,expire_at:5");
        bodys.put("phone_number", "13107737592");
        bodys.put("template_id", "TPL_0001");


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired
    SmsComponent smsComponent;

    @Test
    public void testSendCode(){
        smsComponent.sendSmsCode("13107737592","7777");
    }
}
