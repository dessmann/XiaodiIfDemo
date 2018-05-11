XiaodiIfDemo
====  
小嘀管家SDK第三期DEMO

# 一.请引入相关的库文件  
```java  
    compile 'com.yolanda.nohttp:nohttp:1.1.0'
    compile 'no.nordicsemi.android:dfu:0.6.2'
    compile 'no.nordicsemi.android.support.v18:scanner:0.1.1'
    compile(name:'biz_xiaodi_sdk-release-v3.4.2', ext:'aar')
    compile(name: 'lib_base-release-v3.4.4', ext: 'aar')
    compile(name:'lib_bluetoothle-release-v3.4.1', ext:'aar')
```
完整的引 可能像这样  

![XiaodiIfDemo](https://github.com/dessmann/XiaodiIfDemo/raw/master/docs/img/gradlec.png)  

# 二.初始化
## 1.调用sdk初始化的方法
```java
  XiaodiSdkLibInit.init(context, oemId)
```
&nbsp;&nbsp;&nbsp;&nbsp;context为应用上下文对象  
&nbsp;&nbsp;&nbsp;&nbsp;oemId为厂家标识，请参考4中的厂家标识说明，比如厂家为金凯德，oemId参数为15
## 2.启用正式服务器访问
&nbsp;&nbsp;&nbsp;&nbsp;如果是测试阶段，完成1中的初始化之后，就可以开始使用sdk ，此时sdk访问的是小嘀管家的测试服务器  
&nbsp;&nbsp;&nbsp;&nbsp;如果需要访问小嘀管家正式服务器，请继续完成如下配置
```java
  ServerUnit.getInstance().enableOnlineServer();
```
# 三.关于api文档
&nbsp;&nbsp;&nbsp;&nbsp;docs/api文件夹下的apidocs.zip文件描述了sdk的具体功能，主要分为服务器api(serverapi)与设备api(seniorapi)
## 1.服务器api
&nbsp;&nbsp;&nbsp;&nbsp;serverapi里是单一的服务器请求接口，接口定义为IServerUnit文件，其中描述了服务器接口的功能列表。  
&nbsp;&nbsp;&nbsp;&nbsp;该类接口(IServerUnit)的调用实现类为ServerUnit，其中提供了一个单例实现，获取方式为ServerUnit.getInstance()，如实现用户登录的调用过程可能像下面这样：   
```java
    //模拟调用服务器接口API，用户登录 
    ServerUnit.getInstance().login(account, pwd, null, new ServerUnit.OnServerUnitListener() {
  			@Override
  			public void success(final List data, final String msg) {
            Log.i(“登录成功”);
      	}
				@Override
  			public void failure(final String error, final int loglever){
				  Log.i(“登录失败”);                                                             
			  }
	  }
```
&nbsp;&nbsp;&nbsp;&nbsp;其他服务器请求是类似的，都由该实现类的单例发起调用
## 2.设备api
&nbsp;&nbsp;&nbsp;&nbsp;seniorapi里是关于设备的高级设置接口，一般都是需要先跟设备进行蓝牙通讯，然后将信息备案到服务器，在这一类的接口里经常需要用到serverapi里相关的接口请求返回的数据，比如注销设备的接口，就需要先请求服务器获取设备的详情，然后将设备mac地址、固件版本号、设备类型等相关信息传入注销设备的接口，才能完成整个接口的功能，请用户使用之前先预览整个接口列表的功能，大致了解接口数据的基本交互过程。

# 四.其他
## 1.关于厂家标识
&nbsp;&nbsp;&nbsp;&nbsp;由于sdk提供给多个厂家，为了区分不同厂家的请求记录，也方便后期的数据统计，特提供的oemId参数，请求初始化的时候出入该参数。  
&nbsp;&nbsp;&nbsp;&nbsp;目前已知的oem厂家编号为：
>>dsm 官方  
>>11 安居智云  
>>12 滨江智慧社区  
>>13 威固   
>>14 松下   
>>15 金凯德   
>>16 控客   
>>17 国美   
>>18 捷信安保   
>>19 标仓科技   
>>20 云聚科技  

&nbsp;&nbsp;&nbsp;&nbsp;如未查询到编号或新增厂家，请联系德施曼官方确认。
## 2.关于牙连接超时的问题
&nbsp;&nbsp;&nbsp;&nbsp;遇到这个问题，请先检查app有位置权限并且手机定位功能开启，因为anroid 6.0以上蓝牙扫描需要这两个条件，如果在使用过程中偶尔出现蓝牙无法连接的情况，请引导用户手动重启手机蓝牙适配器然后重试，这在大部分android蓝牙低功耗的手机app上，都是很常见的一句话，因为目前android底层蓝牙低功耗的协议栈仍然不完善，加上android手机的多样性和深度个性化定制，这种性能问题很难彻底解决。
## 3.关于问题排查
&nbsp;&nbsp;&nbsp;&nbsp;如果在使用sdk的过程中遇到一些不好排查的问题，sdk默认记录了操作日志，请调用日志上传接口，将日志信息上传到小嘀管家服务器，调用的api请参考服务器api文档中uploadOperatorLog接口，或参考demo，然后联系yiyeshu1015@vip.qq.com，邮件中注明厂家标识和记录日志上传的时间，我们会尽快回复您，谢谢使用！

# 五.更新历史
&nbsp;&nbsp;&nbsp;&nbsp;最新的的更新历史请参考项目Release标记

&nbsp;&nbsp;&nbsp;&nbsp;版本: v3.4.6  
&nbsp;&nbsp;&nbsp;&nbsp;更新日期：2018/05/11 16:54  
&nbsp;&nbsp;&nbsp;&nbsp;更新内容：  
&nbsp;&nbsp;&nbsp;&nbsp;去掉网络接口与页面请求地址中的端口信息 

&nbsp;&nbsp;&nbsp;&nbsp;版本: v3.4.3  
&nbsp;&nbsp;&nbsp;&nbsp;更新日期：2017/12/12 10:53  
&nbsp;&nbsp;&nbsp;&nbsp;更新内容：  
&nbsp;&nbsp;&nbsp;&nbsp;修复服务器token加载异常问题  

&nbsp;&nbsp;&nbsp;&nbsp;版本: v3.4.2  
&nbsp;&nbsp;&nbsp;&nbsp;更新日期：2017/7/28 17:45  
&nbsp;&nbsp;&nbsp;&nbsp;更新内容：  
&nbsp;&nbsp;&nbsp;&nbsp;优化上传操作日志时日志名称在服务器的显示格式  

&nbsp;&nbsp;&nbsp;&nbsp;版本: v3.4.1  
&nbsp;&nbsp;&nbsp;&nbsp;更新日期：2017/7/28 15:06  
&nbsp;&nbsp;&nbsp;&nbsp;更新内容：  
&nbsp;&nbsp;&nbsp;&nbsp;修复base库引用报错的问题  

&nbsp;&nbsp;&nbsp;&nbsp;版本: v3.4  
&nbsp;&nbsp;&nbsp;&nbsp;更新日期：2017/7/27 16:59  
&nbsp;&nbsp;&nbsp;&nbsp;更新内容：  
&nbsp;&nbsp;&nbsp;&nbsp;发布oemsdk通用版本  

&nbsp;&nbsp;&nbsp;&nbsp;版本: v3.3.3  
&nbsp;&nbsp;&nbsp;&nbsp;更新日期：2017/7/26 11:29  
&nbsp;&nbsp;&nbsp;&nbsp;更新内容：  
&nbsp;&nbsp;&nbsp;&nbsp;修复指纹报警删除之后时效失效的问题  
