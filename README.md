[TOC]



# 智慧饭堂
每个一级目录都建议创建简介文件README.md，说明包内的内容


## 代码规范

### 命名规范

- 包名必须小写
- 抽象类命名使用Abstract开头，异常是Exception结尾，枚举是Enum做后缀。
- 常量必须全大写
- 针对布尔类型的变量，命名不允许加is前缀



### 魔法值

- 魔法值，是指在代码中直接出现的数值，只有在这个数值记述的那部分代码中才能明确了解其含义。
- 不允许使用魔法值，可以根据常量的作用域，分为类内的常量和全局常量，使用final static修饰（注意在interface中属性默认为public final static）



### 日志

	#### 日志级别

- DEBUG 级别日志 记录对调试程序有帮助的信息
- INFO级别日志 
- WARN级别日志
- ERROR级别日志
- FATAL级别日志

#### 打印规约

避免无效日志打印，不允许使用System.out.print()这种方式打印日志

生产环境禁止输出DEBUG日志且有选择的输出INFO日志

ERROR级别记录系统逻辑错误、异常或者违反重要的业务规则，其他错误写入WARN级别

当不知怎么选级别时思考一下：日志是否有人看、看到日志能干嘛、能不能提升问题排查效率



### 注释

- 注释需要精简
- 拒绝废注释，好的代码是自解释的
- 修改了代码也必须加上修改注释
- 类、类属性和类方法的注释必须遵循javadoc规范，使用文档注释（/** */），仅在方法内才允许使用//。
- 对于一些过时的代码不应该注释整段代码而是标注其已过时并在注释中加入原因



### 包结构

- aspect 存放切面类
- common 存放公共资源
  - constant 全局静态变量
  - enums 枚举
  - mqtt 和mqtt相关的配置和处理
  - rabbitmq 和rabbitmq相关的配置和处理
  - redis 和redis相关的配置和处理
- config 存放配置变量，一般从配置文件获取
- controller 存放接口
  - api 存放所有对外开放的api接口
    - open 无需权限即可访问的api
    - lock 需要前端带上身份信息才能访问
  - utils 存放util工具（考虑废除）
- entity          存放实体
  - converter        封装一些实体之间互相转换的操作
  - dto                   这里的dto和vo我已经觉得有点混乱，设计dto之初可能是用来和管理员后台传输那块信息用的。
  - from                接收前端信息，里面需要对信息做校验
  - pojo                 对应数据库表的实体
  - vo                     返回给前端的数据集（不应该和pojo一样）        
- exception     存放自定义异常和异常的处理
  - handler 	  异常处理
- filter               拦截
- init                 初始化信息和定时任务（混乱）
- interceptor   谷歌翻译：拦截器
- mapper         存放mybatis的mapper接口
  - exp                     不知道是要干嘛的
- schedule       这里又有一个存放定时任务的包
- shiro              存放shiro的Realm和一个拦截（被废弃）
- utils                混乱的utils，异曲同工的灾难区



## api接口目录

### 食堂模块

```java
ApiCanteen /api/v1.1/canteen
	- list 查询营业中的食堂列表

ApiMode /api/v1.1/mode
	- listMode 获取所有模式列表给学生端
```

### 菜品模块

```java
ApiBuyerProductInfo /api/productInfo
	-list 根据食堂id返回前端商品接口

ApiProduct /api/v1.1/products
	- check 检验库存
	- getStock 询规格库存信息
```

### 用户模块

```java
CustomerLogin /api/v1.1/login
	-getheadimgurl 根据token获取用户头像
	-showInfo 根据Token获取用户信息
	-check 身份验证入口（获取微信信息必须）
	-checkInfo 判断用户状态的具体实现
	-bindInfo 查询当前用户的登录状态
	-bind 用户绑定
	-unbind 用户解绑
	-updatepwd 修改有用户密码
	-checkbytoken 根据token判断用户身份信息
	
Address /api/v1.1/customeaddress
	-showAllArea 分页显示所有可用区域信息
	-list 根据学号查看用户的收货地址
	-add 添加用户地址
	-update 根据主键更新用户信息-》地址信息
	-delete 根据主键删除地址
```

### 订单模块

```java
ApiOrder /api/v1.1/orders
	-add 用户下单进行扣库存和分配保温箱
	-waitpaids 检查用户是否存在待支付，如果存在待支付，则不允许再次下单
	-lists 查询用户订单预下单链信息列表
	-binning 根据订单微信支付单号查询订单分箱链
	*订单开锁部分*
	-change 接口类型Boolean ,传主订单号和箱子号,把子订单状态改为已取
	-getOrderTimeOut 前端获取所剩时间
	-check 传主订单号号,判断子订单是否都已取,若是修改为交易完成
	
V2ApiOrder /api/v1.2/orders/
    -list_order 获取订单详情
    
*微信支付回调接口*
ApiWechatPay /api/v1.1/pay
	-unifiedOrder 统一下单接口，接收订单号进行微信下单操作
	-notify 微信服务器回调接口
	-notifyTemp
	-refund_notify 退款回调
	-refund 退款
	-refundQuery 查询退款
	-refundHandle 退款处理，即客户端点击退款后，订单状态修改成正在退款，稍后由工作人员进行退款操作
	-closeOrder 由于订单需要5分钟才允许关闭，因此在这里进行库存的恢复和箱子的释放，将请求微信平台关闭支付订单操作放在消息队列上

用户订单评价
ApiOrdercomment /api/v1.1/ordercomment
	-add 插入评价
	-findonebyid 根据微信订单号获取评价
	
	
```

### 配置模块

```java
ServiceTimer /api/v1.1/server
	-getServerTime 获取服务器时间
	
CustomerLogin /api/v1.1/login
	-encryption jssdk签名
```

### 开箱模块

```java
Unlock 开锁接口
	-unlocking 二维码开锁
```

