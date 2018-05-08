##Android SDK配置

######更新时间：2018-05-08

> Android SDK 最新版本1.0.0

> 挖矿SDK Demo, [点击这里](https://github.com/OFBank/OFBank-Android-Demo.git)

###1.创建应用

到ofbank开放平台创建应用，应用创建完成后，进入应用模块进行设置，具体操作请参见[创建APP](http://test.openapi.lingzhuworld.cn/)。

###2.SDK下载和集成

######2.1下载SDK

  - 在开放平台SDK下载页进行下载

######2.1.2添加依赖

 - 将下载好的jar包拷贝至项目的libs目录下，并在build.gradle中添加引用:
   
   ```java
   implementation files('libs/of-sdk-v1.0.0.jar')
	```
	
###3.SDK使用说明

 - Manifest配置
   
   1.接入本SDK需要使用网络权限：
   
  ```java
   <uses-permission android:name="android.permission.INTERNET" />

   2.在application节点内配置service：
   
  <service
             android:name="com.ofbank.work.services.MiningService"
            android:exported="false" />
  ```
  
#### 3.1基本信息获取 
 
 - SDK初始化     
 请参照以下代码完成SDK的初始化，appKey/appSecret 的获取参考[创建App](http://test.openapi.lingzhuworld.cn/)
 
 ###### 参数

 	- appKey   	appKey
 	- appCode  	appCode
 	- application 
 
 ```java
  MinerSDK.init("your appKey", "your appCode", this);  
 ```
 
 在自定义的application类中增加如下代码：
 
 ```java
 // SDK初始化
  MinerSDK.init("your appKey", "your appCode", this);
  
 // 打开调试日志
 MinerSDK.setDebugModel(true);
 
 ```
 
 #####打开调试日志

 - 打开 SDK 日志;
 - SDK日志可通过TAG “ofSDK”进行检索;
 - 测试时可选择打开, App 上线后建议关闭;
 - 默认情况下不打印日志。
 
 ```java
 MinerSDK.setDebugModel(true);
 ```
 
 
#####获取SDK版本号

 - MinerSDK.getSDKVersion()

 
#### 3.2账号API

>如无特殊说明，SDK中的方法均可在主线程中调用;

>RequestObserver的回调方法全部在主线程。

#####注册账号

######参数

 - uAccount    用户名
 - uPassword   密码
 - observer    回调接口

```java
Miner.register(String uAccount,
               String uPassword,
               RequestObserver observer);
```

#####登录账号

######参数

 - uAccount             用户名
 - uPassword            密码
 - tokenExpireTime      token有效期设置，默认为永久不失效
 - observer             回调接口

```java
Miner.login(String uAccount,
            String uPassword,
            String tokenExpireTime,
            RequestObserver observer);
```

#### 3.3挖矿API

> 【注意】以下方法，都需要在登录成功以后调用。
 
#####启动挖矿
- token 当前用户的token,不可为空

```java
Miner.startMining(authToken);
```

#####停止挖矿

```java
Miner.stopMining();
```

#####获取挖矿收益记录

######参数

 - page       记录页数
 - count      每页记录数
 - authToken  当前用户的token
 - observer   回调接口

```java
Miner.getRewardList(int page,
                    int count,
                    String authToken,
                    RequestObserver observer);
```

#####获取用户交易记录

######参数

 - page       记录页数
 - count      每页记录数
 - authToken  当前用户的token
 - observer   回调接口

```java
Miner.getWithDrawTransactionList(int page,
                                 int count,
                                 String authToken,
                           RequestObserver observer);
```

#####提现到指定地址

######参数

 - walletAddress      提现地址
 - coinNum            提现金额
 - authToken          当前用户的token
 - observer           回调接口 

```java
Miner.withdrawCash(String walletAddress,
                   String coinNum,
                   String authToken, 
                   RequestObserver observer);
```