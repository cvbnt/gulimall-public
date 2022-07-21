## 谷粒商城
[源码来源](https://www.bilibili.com/video/BV1np4y1C7Yf)

IDEA务必使用UTF-8文件编码
- 文件-设置-编辑器-文件编码-全局编码UTF-8-项目编码UTF-8-属性文件的默认编码UTF-8

MySql的DSL语句
- 项目SQL文件夹内

Nginx配置文件以及需要的静态资源
- 项目Nginx文件夹内

需要指向本地的Hosts域名
- 项目hosts文件
<details>
<summary>单点登录所需github的client_id和client_secret配置</summary>
gulimall-auth-server/src/main/resources/templates/login.html

```html
    <a href="https://github.com/login/oauth/authorize?client_id=client_id&redirect_uri=http://auth.gulimall.com/oauth2.0/github/success">
    <img style="width: 50px;height: 18px" src="/static/login/JD_img/weibo.png"/>
    <span>weibo</span>
    </a>
```
gulimall-auth-server/src/main/java/com/atguigu/gulimall/auth/controller/Oauth2Controller.java

```java
    map.add("client_id","client_id");
    map.add("client_secret","client_secret");
```
</details>
<details>
<summary>阿里云OSS配置</summary>
gulimall-third-party/src/main/resources/application.yaml

```
    spring:
        cloud:
            alicloud:
                access-key:
                secret-key:
                oss:
                    endpoint:
                    bucket:
```
gulimall-third-party/src/main/resources/application-prod.yaml同上
renren-fast-vue/src/components/upload/multiUpload.vue
```js
    action="*.aliyuncs.com"
```
renren-fast-vue/src/components/upload/singleUpload.vue同上
</details>
<details>
<summary>支付宝支付相关配置</summary>
gulimall-order/src/main/java/com/atguigu/gulimall/order/config/AlipayTemplate.java

```java
    private String app_id = "app_id";
    private String merchant_private_key = "merchant_private_key";
    private String alipay_public_key = "alipay_public_key";
    private String notify_url = "notify_url";
```
gulimall-order/src/main/resources/application.properties
```
    alipay.app_id
    alipay.notify_url
```
gulimall-order/src/main/resources/application-prod.properties同上
</details>
<details>
<summary>短信接口配置，依据自己需要的短信服务商配置</summary>
gulimall-third-party/src/main/resources/application.yaml

```
    spring:
        cloud:
            alicloud:
                sms:
                    host:
                    path:
                    skin:
                    appcode:
```
gulimall-third-party/src/main/resources/application-prod.yaml同上
</details>
<details>
<summary>Redis连接信息配置</summary>
<details>
<summary><b>gulimall-auth-server模块</b></summary>
src/main/resources/application.properties

```
    spring.redis.host
    spring.redis.port
    spring.redis.password
```
src/main/resources/application-prod.properties同上
</details>
<details>
<summary><b>gulimall-cart模块</b></summary>
src/main/resources/application.properties

```
    spring.redis.host
    spring.redis.password
```
src/main/resources/application-prod.properties同上
</details>
<details>
<summary><b>gulimall-member模块</b></summary>
src/main/resources/application.properties

```
    spring.redis.host
   spring.redis.port
   spring.redis.password
```
src/main/resources/application-prod.properties同上
</details>
<details>
<summary><b>gulimall-order模块</b></summary>
src/main/resources/application.properties

``` 
    spring.redis.host
    spring.redis.port
    spring.redis.password
```
src/main/resources/application-prod.properties同上
</details>

<details>
<summary><b>gulimall-product模块</b></summary>
src/main/resources/application.yaml

```
Spring:
    redis:
        host:
        port:
        password:
```
src/main/resources/application-prod.yaml同上
src/main/java/com/atguigu/gulimall/product/config/MyRedissonConfig.java
```java
config.useSingleServer().setAddress("redis://127.0.0.1:6379").setPassword    ("123456789");
```
</details>
<details>
<summary><b>gulimall-search模块</b></summary>
src/main/resources/application.properties

```
    spring.redis.host
    spring.redis.port
    spring.redis.password
```
src/main/resources/application-prod.properties同上
</details>
<details>
<summary><b>gulimall-seckill模块</b></summary>
src/main/resources/application.properties
``` 
    spring.redis.host
    spring.redis.port
    spring.redis.password
```
src/main/resources/application-prod.properties同上
src/main/java/com/atguigu/gulimallseckill/config/MyRedissonConfig.java

```java
    .config.useSingleServer().setAddress("redis://127.0.0.1:6379").setPassword("123456789");
```
</details>
</details>
<details>
<summary>MySql连接信息配置</summary>
<details>
<summary><b>gulimall-coupon模块</b></summary>
src/main/resources/application.properties

```
    spring.datasource.url
    spring.datasource.username
    spring.datasource.password
    spring.datasource.driver-class-name
```
src/main/resources/application-prod.properties同上
</details>
<details>
<summary><b>gulimall-member模块</b></summary>
src/main/resources/application.yaml

```
Spring:
    datasource:
        username:
        password:
        url:
        driver-class-name:  
```
src/main/resources/application-prod.yaml同上
src/main/resources/application-prod.properties
```spring.datasource.url```
</details>
<details>
<summary><b>gulimall-order模块</b></summary>
src/main/resources/application.yaml

```
Spring:
    datasource:
        username:
        password:
        url:
        driver-class-name:  
```
src/main/resources/application-prod.yaml同上
</details>
<details>
<summary><b>gulimall-product模块</b></summary>
src/main/resources/application.yaml

```
Spring:
    datasource:
        username:
        password:
        url:
        driver-class-name:  
```
src/main/resources/application-prod.yaml同上
</details>
<details>
<summary><b>gulimall-ware模块</b></summary>
src/main/resources/application.yaml

```
Spring:
    datasource:
        username:
        password:
        url:
        driver-class-name:  
```
src/main/resources/application-prod.yaml同上
</details>
<details>
<summary><b>renren-fast模块</b></summary>
renren-fast/src/main/resources/application-dev.yml

```
    spring:
        datasource:
            type:
            druid:
                driver-class-name:
                url:
                username:
                password:
```
</details>
<details>
<summary><b>renren-generator模块</b></summary>
renren-generator/src/main/resources/application.yml

```
    spring:
        datasource:
            type:
            druid:
                driver-class-name:
                url:
                username:
                password:
```
</details>
</details>
<details>
<summary>ElasticSearch连接信息配置</summary>
gulimall-search/src/main/java/com/atguigu/gulimall/gulimallsearch/config/GulimallElasticSearchConfig.java

```java
    builder=RestClient.builder(new HttpHost("127.0.0.1", 9200, "http"));
```
</details>
<details>
<summary>Nacos连接信息配置</summary>
<details>
<summary><b>gulimall-auth模块</b></summary>
src/main/resources/application.properties

```spring.cloud.nacos.discovery.server-addr```
src/main/resources/application-prod.properties同上
</details>
<details>
<summary><b>gulimall-cart模块</b></summary>
src/main/resources/application.properties

```spring.cloud.nacos.discovery.server-addr```
src/main/resources/application-prod.properties同上
</details>
<details>
<summary><b>gulimall-coupon模块</b></summary>
src/main/resources/application.properties

```spring.cloud.nacos.discovery.server-addr```
src/main/resources/application-prod.properties同上
src/main/resources/bootstrap.properties

```spring.cloud.nacos.config.server-addr```
src/main/resources/bootstrap-prod.properties同上
</details>
<details>
<summary><b>gulimall-gateway模块</b></summary>
src/main/resources/application.properties

```spring.cloud.nacos.discovery.server-addr```
src/main/resources/application-prod.properties同上
src/main/resources/bootstrap.properties

```spring.cloud.nacos.config.server-addr```
src/main/resources/bootstrap-prod.properties同上
</details>
<details>
<summary><b>gulimall-member模块</b></summary>
src/main/resources/application.yaml

```
Spring:
    cloud:
        nacos:
            discovery:
                server-addr:
```
src/main/resources/bootstrap.properties

```spring.cloud.nacos.config.server-addr```
</details>
<details>
<summary><b>gulimall-order模块</b></summary>
src/main/resources/application.properties

```spring.cloud.nacos.discovery.server-addr```
src/main/resources/application-prod.properties同上
</details>
<details>
<summary><b>gulimall-product模块</b></summary>
src/main/resources/bootstrap.properties

```spring.cloud.nacos.discovery.server-addr```
src/main/resources/bootstrap-prod.properties同上
src/main/resources/application.yaml

```
Spring:
    cloud:
        nacos:
            discovery:
                server-addr:
```
src/main/resources/application-prod.yaml同上
</details>
<details>
<summary><b>gulimall-search模块</b></summary>
src/main/resources/application.properties

```spring.cloud.nacos.discovery.server-addr```
src/main/resources/application-prod.properties同上
</details>
<details>
<summary><b>gulimall-seckill模块</b></summary>
src/main/resources/application.properties

```spring.cloud.nacos.discovery.server-addr```
src/main/resources/application-prod.properties同上
</details>
<details>
<summary><b>gulimall-third-party模块</b></summary>
src/main/resources/application.yaml

```
Spring:
    cloud:
        nacos:
            discovery:
                server-addr:
```
src/main/resources/application-prod.yaml同上
src/main/resources/bootstrap.properties

```spring.cloud.nacos.config.server-addr```
src/main/resources/bootstrap-prod.properties同上
</details>
<details>
<summary><b>gulimall-ware模块</b></summary>
src/main/resources/application.yaml

```
Spring:
    cloud:
        nacos:
            discovery:
                server-addr:
```
src/main/resources/application-prod.yaml同上
</details>
<details>
<summary><b>renren-fast模块</b></summary>
src/main/resources/application.yml

```
Spring:
    cloud:
        nacos:
            discovery:
                server-addr:
```
</details>
</details>
<details>
<summary>Sentinel连接信息配置</summary>
<details>
<summary><b>gulimall-auth-server模块</b></summary>
src/main/resources/application.properties

``` 
    spring.cloud.sentinel.transport.dashboard
    spring.cloud.sentinel.transport.port
```
src/main/resources/application-prod.properties同上
</details>
<details>
<summary><b>gulimall-cart模块</b></summary>
src/main/resources/application.properties

``` 
    spring.cloud.sentinel.transport.dashboard
    spring.cloud.sentinel.transport.port
```
src/main/resources/application-prod.properties同上
</details>
<details>
<summary><b>gulimall-coupon模块</b></summary>
src/main/resources/application.properties

``` 
    spring.cloud.sentinel.transport.dashboard
    spring.cloud.sentinel.transport.port
```
src/main/resources/application-prod.properties同上
</details>
<details>
<summary><b>gulimall-gateway模块</b></summary>
src/main/resources/application.properties

``` 
    spring.cloud.sentinel.transport.dashboard
    spring.cloud.sentinel.transport.port
```
src/main/resources/application-prod.properties同上
</details>
<details>
<summary><b>gulimall-member模块</b></summary>
src/main/resources/application.properties

``` 
    spring.cloud.sentinel.transport.dashboard
    spring.cloud.sentinel.transport.port
```
src/main/resources/application-prod.properties同上
</details>
<details>
<summary><b>gulimall-order模块</b></summary>
src/main/resources/application.properties

``` 
    spring.cloud.sentinel.transport.dashboard
    spring.cloud.sentinel.transport.port
```
src/main/resources/application-prod.properties同上
</details>
<details>
<summary><b>gulimall-product模块</b></summary>
src/main/resources/application.properties

``` 
    spring.cloud.sentinel.transport.dashboard
    spring.cloud.sentinel.transport.port
```
src/main/resources/application-prod.properties同上
</details>
<details>
<summary><b>gulimall-search模块</b></summary>
src/main/resources/application.properties

``` 
    spring.cloud.sentinel.transport.dashboard
    spring.cloud.sentinel.transport.port
```
src/main/resources/application-prod.properties同上
</details>
<details>
<summary><b>gulimall-seckill模块</b></summary>
src/main/resources/application.properties

``` 
    spring.cloud.sentinel.transport.dashboard
    spring.cloud.sentinel.transport.port
```
src/main/resources/application-prod.properties同上
</details>
<details>
<summary><b>gulimall-third-party模块</b></summary>
src/main/resources/application.properties

``` 
    spring.cloud.sentinel.transport.dashboard
    spring.cloud.sentinel.transport.port
```
src/main/resources/application-prod.properties同上
</details>
<details>
<summary><b>gulimall-ware模块</b></summary>
src/main/resources/application.properties

``` 
    spring.cloud.sentinel.transport.dashboard
    spring.cloud.sentinel.transport.port
```
src/main/resources/application-prod.properties同上
</details>
</details>
<details>
<summary>RabbitMQ连接信息配置</summary>
<details>
<summary><b>gulimall-order模块</b></summary>
src/main/resources/application.properties

```
    spring.rabbitmq.host
    spring.rabbitmq.port
    spring.rabbitmq.virtual-host
    spring.rabbitmq.publisher-confirms
    spring.rabbitmq.publisher-returns
    spring.rabbitmq.template.mandatory
    spring.rabbitmq.listener.simple.acknowledge-mode
```
src/main/resources/application-prod.properties同上
</details>
<details>
<summary><b>gulimall-seckill模块</b></summary>
src/main/resources/application.properties

```
    spring.rabbitmq.host
    spring.rabbitmq.port
    spring.rabbitmq.virtual-host
```
src/main/resources/application-prod.properties同上
</details>
<details>
<summary><b>gulimall-ware模块</b></summary>
src/main/resources/application.properties

```
    spring.rabbitmq.host
    spring.rabbitmq.port
    spring.rabbitmq.virtual-host
    spring.rabbitmq.listener.simple.acknowledge-mode
```
src/main/resources/application-prod.properties同上
</details>
</details>
<details>
<summary>Zipkin连接信息配置</summary>
<details>
<summary><b>gulimall-auth-server模块</b></summary>
src/main/resources/application.properties

```
    spring.zipkin.base-url
    spring.zipkin.discovery-client-enabled
    spring.zipkin.sender.type
```
src/main/resources/application-prod.properties同上
</details>
<details>
<summary><b>gulimall-cart模块</b></summary>
src/main/resources/application.properties

```
    spring.zipkin.base-url
    spring.zipkin.discovery-client-enabled
    spring.zipkin.sender.type
```
src/main/resources/application-prod.properties同上
</details>
<details>
<summary><b>gulimall-coupon模块</b></summary>
src/main/resources/application.properties

```
    spring.zipkin.base-url
    spring.zipkin.discovery-client-enabled
    spring.zipkin.sender.type
```
src/main/resources/application-prod.properties同上
</details>
<details>
<summary><b>gulimall-gateway模块</b></summary>
src/main/resources/application.properties

```
    spring.zipkin.base-url
    spring.zipkin.discovery-client-enabled
    spring.zipkin.sender.type
```
src/main/resources/application-prod.properties同上
</details>
<details>
<summary><b>gulimall-member模块</b></summary>
src/main/resources/application.properties

```
    spring.zipkin.base-url
    spring.zipkin.discovery-client-enabled
    spring.zipkin.sender.type
```
src/main/resources/application-prod.properties同上
</details>
<details>
<summary><b>gulimall-order模块</b></summary>
src/main/resources/application.properties

```
    spring.zipkin.base-url
    spring.zipkin.discovery-client-enabled
    spring.zipkin.sender.type
```
src/main/resources/application-prod.properties同上
</details>
<details>
<summary><b>gulimall-product模块</b></summary>
src/main/resources/application.properties

```
    spring.zipkin.base-url
    spring.zipkin.discovery-client-enabled
    spring.zipkin.sender.type
```
src/main/resources/application-prod.properties同上
</details>
<details>
<summary><b>gulimall-search模块</b></summary>
src/main/resources/application.properties

```
    spring.zipkin.base-url
    spring.zipkin.discovery-client-enabled
    spring.zipkin.sender.type
```
src/main/resources/application-prod.properties同上
</details>
<details>
<summary><b>gulimall-seckill模块</b></summary>
src/main/resources/application.properties

```
    spring.zipkin.base-url
    spring.zipkin.discovery-client-enabled
    spring.zipkin.sender.type
```
src/main/resources/application-prod.properties同上
</details>
<details>
<summary><b>gulimall-third-party模块</b></summary>
src/main/resources/application.properties

```
    spring.zipkin.base-url
    spring.zipkin.discovery-client-enabled
    spring.zipkin.sender.type
```
src/main/resources/application-prod.properties同上
</details>
<details>
<summary><b>gulimall-ware模块</b></summary>
src/main/resources/application.properties

```
    spring.zipkin.base-url
    spring.zipkin.discovery-client-enabled
    spring.zipkin.sender.type
```
src/main/resources/application-prod.properties同上
</details>
</details>