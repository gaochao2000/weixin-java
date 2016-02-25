#基本信息#
>本项目是在做一个在线点餐的一个软件项目的过程中，将微信服务号开发相关功能分离出来形成的。可以支持使用 java 语言开发微信服务号相关功能。本项目只是实现了部分微信公众号的功能。

#实现功能列表#

 - 微信授权服务。实现生成两种网页授权回调的 url 
 - 微信用户管理。实现通过网页授权回调获取的 code 获取用户的 openid 基本信息等
 - 微信支付服务。实现订单的统一下单。生成 JSAPI 支付信息
 - 微信消息服务。实现对微信用户发送客服消息的服务
 - 微信JSAPI服务。实现生成 JSAPI config 信息和 JSAPI 签名功能
 - 微信菜单服务。未完善

##用法##

 - 可以使用 Spring 初始化服务的接口，并且进行调用
 - 初始化之前需要配置 [weixin.properites][1] 文件。该文件主要配置微信相关信息
参考
 - 如果使用 redis 作为 token 的缓存机制，则需要配置 [cache.prpperties][2] 文件。该文件指定 redis 服务器信息
 - 完整的 Spring 配置样例参考  [weixing_service.xml][3] 文件
 - 完整的 api 调用样例参考[测试用例][4]目录中的实现 

#作者联系方式#
>邮箱: gaocao2000@sina.com


  [1]: https://github.com/gaochao2000/weixin-java/blob/master/src/test/resource/weixing.properties
  [2]: https://github.com/gaochao2000/weixin-java/blob/master/src/test/resource/cache.properties
  [3]: https://github.com/gaochao2000/weixin-java/blob/master/src/test/resource/weixing_service.xml
  [4]: https://github.com/gaochao2000/weixin-java/tree/master/src/test/java/com/x97/weixin