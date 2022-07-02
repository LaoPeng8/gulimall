# gulimall problem record

> ***作者: LaoPeng***
>
> ***2022/5/6 18:30 始***
> 
> ***2022/7/2 02:00 基础篇结束***



# 错误: application.yml 修改 port 不生效

```
修改 server: port: 8000 不生效 端口依旧是 8080 不管是重启idea还是 Rebulid project 还是刷新maven都没用
后发现 项目是使用 http://start.aliyun.com 创建的 自带一个 application.properties 中配置了 8080 虽然删除了，
但是我感觉它还在的样子因为 properties 比 yml 优先级高 我就像会不会是 properties 中的8080 覆盖了 yml中的 8000 
但是properties 已经删除了，但是我感觉可能是没有删干净，重写新建一个 properties文件修改端口 8000 果然成功了，
我明白了 肯定是没删干净，后 target 中发现 properties果然还在，删除后，yml 配置的8000端口生效。
```


# 错误: java.lang.NoClassDefFoundError: ConfigurationBeanFactoryMetadata

```
Exception encountered during context initialization - cancelling refresh attempt: 
org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'configurationPropertiesBeans' 
defined in class path resource [org/springframework/cloud/autoconfigure/ConfigurationPropertiesRebinderAutoConfiguration.class]: 
Bean instantiation via factory method failed; nested exception is org.springframework.beans.BeanInstantiationException: 
Failed to instantiate [org.springframework.cloud.context.properties.ConfigurationPropertiesBeans]: 
Factory method 'configurationPropertiesBeans' threw exception; nested exception 
is java.lang.NoClassDefFoundError: org/springframework/boot/context/properties/ConfigurationBeanFactoryMetadata

百度后发现是 springboot 与 springcloud 版本不兼容导致, 将 springboot版本由 2.6.6 修改为 2.3.7.RELEASE 后，成功
```




# 记录: 2022/5/25 今天啥也没干

```
今天有点抑郁，啥也没干，主要就是昨天做谷粒商城时配置gateway得一个 filters: RewritePath=路径 重写路径感觉有点看不太懂，
gateway配置跨域时，虽然是固定配置直接可以从以前的项目copy过来但是感觉面试问的话可能我也答不上来。
其次就是抖音经常刷到的一些面试，感觉问的东西都挺高级的，我都不太会的样子很理论感觉我也是根本记不住的样子，
自己简历上写的一些东西 都是很久以前学的 比如快排 都是2020年学的了，现在看记得大概理论但是写估计也写不出来，
就是感觉拿捏不住还要jvm sql优化啥的，感觉会但是说又说不出来什么，以前学过的东西感觉自己忘的很快。 
能不能找到实习哟。不过不管怎么说明天继续Java吧！
```


# 记录: 2022/5/26

```
今天编写 删除分类时老师说 需要判断该分类是否被其他地方使用, 醍醐灌顶, 以前居然写谷粒学院时 删除分类就是 判断 当前分类下如果还有子分类就不让删除, 只能删除没有子分类的分类,
这完全就是说从后台管理系统的角度看的, 如果前台系统 有商品是属于这个分类的, 就算这个分类没有子分类 删除了, 那这些属于该分类的商品怎么办?
```


# 记录: 2022/5/27
```
发送请求获取当前节点最新的数据 (有人可能会问 点击edit后, 不是通过参数data将被点击的节点数据传入此方法了吗, 直接使用data的数据回显不行吗?)
会发生一种这样的问题, 如果两个管理员同时在操作这个分类, 一个修改了分类, 另一个也修改分类但是此时回显的数据就是有问题的数据
回显的是 老数据, 也就是没有被修改的数据, 而真正的分类已经被修改了, 只是自己没有刷新而已, 所以需要根据id请求最新数据
```


# 错误: Long不能使用==, 需要使用equals
```
问题描述: 返回三级分类树形结构接口, 前十几个一级分类很正常(一级分类中包含二级分类, 二级分类中包含三级分类)数据都很正常, 后面几个分类, 只有一级分类二级分类, 没有三级分类
刚开始挺纳闷 看了几遍代码没有什么头绪, 后来观察数据发现前面都是挺正常的 一直到 “运动健康”分类的 第三个二级分类时, 数据就不对了没有三级分类
于是去找这个 “运动健康” 下到底有没有 三级分类 SELECT * FROM `pms_category` where parent_cid = '128'
到这里我看着这个 id 128 忽然就感觉明白了 之前看过 Integer 与 Integer 与 int 之间比较的题目 知道这个 Integer 与 Integer之间使用 == 比较时 -128 ~ 127 之间 是 true
因为 Integer 内有缓存, 是同一个对象 所以 == 是true, Integer之间比较需要使用equals,  Long 同理
即修改
List<CategoryEntity> entityThree = all.stream()
    .filter((allEntity) -> allEntity.getParentCid() == entityTwo.getCatId()) //过滤出二级分类的三级分类 (修改前)
    .filter((allEntity) -> allEntity.getParentCid().equals(entityTwo.getCatId())) //过滤出二级分类的三级分类 (修改后)
```

# 错误: JSR303校验不生效
```
问题描述: 给Bean添加校验注解 如 给属性 name 加上注解 @NotBlank 表示该name属性 不能为空字符串
后在 需要使用校验处加上 @Valid注解 如 public R save(@Valid @RequestBody BrandEntity brand)
然后使用 postman测试 不知为何 @NotBlank 不生效 查看部分文章后发现 https://blog.csdn.net/numbbe/article/details/118711371 (只是根据文章中引入了 hibernate-validator依赖, 并没有使用 hibernate下的 @NotBlank, 还是使用的 java.validation中的 @NotBlank)
加入 依赖 原本只依赖了 jakarta.validation-api 但是不生效, 后来又引入了 hibernate-validator 即可成功执行
但是和老师的 携带空字符串 请求接口被@NotBlank拦下来后返回测错误信息不一样(老师的更详细), 而且老师也不需要引入这两个依赖, 很奇怪
        <!-- JSR 303 校验 -->
        <dependency>
            <groupId>jakarta.validation</groupId>
            <artifactId>jakarta.validation-api</artifactId>
        </dependency>
        <!-- JSR 303 校验 可能要搭配 该依赖使用 否则 添加了 @NotNull 与 @Valid 都不生效啊, 加入该依赖后生效了 -->
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-validator</artifactId>
            <version>6.2.0.Final</version>
        </dependency>
```

# 记录: 2022/5/31
```
昨天关电脑后, 今天开启啥也没干 gulimall-gateway出现注册不到nacos, gulimall-third-party出现读取不到nacos配置文件, 服务都起不来
经过一顿排查, 意外在右侧Maven按钮中发现 guliamll-gateway 与 gulimall-third-party 依赖全部爆红, 不知道为什么, 刷新也没用, Rebulid Project也没用
后来我将每个爆红的依赖注释了再放开注释就好了???? 依赖不报红了, 启动服务也可以注册到nacos, nacos的配置文件也可以读取了????
```

# 记录: 2022/6/4
```
电商系统中性能很重要, 所以在数据库设计中会有很多冗余字段, 比如在 pms_category_brand_relation 也就是 分类与品牌关联表中就不是单纯的存储 分类id 与 品牌id
还存储了 分类名称 与 品牌名称, 这就是冗余的字段, 这样就是避免了很多 多表查询, 提高了性能, 但是这样会造成一个问题: 就是分类或品牌在自己的表中name被修改了,
但是 分类与品牌关联表中的数据就不一致了, 所以这就是需要从业务中保证它们的一致性. 比如在修改分类名后 需要根据被修改的分类 把分类与品牌关联表中的分类进行同步
```

# 记录: 2022/6/5
```
大表之间不要做连接查询, 比如说: 商品属性表中有100万条数据(商品很多), 属性分组就算只有1000条数据, 在极端条件下做笛卡尔乘积就会生成10亿的数据
我是这样理解的: 连接查询都是需要where条件的, 如果没有where条件就会生成笛卡尔乘积, 相当于每次连接查询都是会产生很大的数据量的, 效率真的不高
可以从业务的方面通过多次单表查询来完成, 可以参考 AttrServiceImpl 中的方法 public PageUtils queryBaseAttrPage(Long catelogId, Map<String, Object> params)
```

# 记录: 2022/6/9 VirtualBox无法启动
```
因为准备期末考试好几天没有打开idea所以也没有打开BirtualBox, 现在考查课完事了, 一个星期后考试课, 准备上来写两个接口, 发现VirtualBox启动报错

报错记录: 
创建 VirtualBoxClient COM 对象失败.  应用程序将被中断.    没有注册类  .  组件:  界面: {00000000-0000-0000-0000-000000000000} 被召者 RC: REGDB_E_CLASSNOTREG (0x80040154)

解决: 总结起来就一句话：兼容性害死人不偿命呐。
此时需要运行下面两个程序: VBoxSVC.exe /ReRegServer    regsvr32.exe VBoxC.dll

运行方法：打开控制台（Win + R  -> cmd），然后 cd 到 VirtualBox 的安装目录，
首先运行 VBoxSVC.exe /ReRegServer ，接着再输入 regsvr32.exe VBoxC.dll 运行。运行结束后，关闭控制台。
例如: 
D:\eatMeal\Linux\LinuxSoftware\VirtualBox VBoxSVC.exe /ReRegServer
D:\eatMeal\Linux\LinuxSoftware\VirtualBox regsvr32.exe VBoxC.dll

此时，运行完两个指令之后，返回桌面，再次双击VirtualBox打开，可能你现在还会遇到之前报的这个错误，怎么办捏？
这个问题是你的权限不够，在VirtualBox桌面图标上点击鼠标右键执行“以管理员权限运行”打开就可以了，下次再使用的时候就只需要双击左键即可。
OK！问题解决！
```

# 记录: 2022/6/23
```
考试结束了, 回家了, 虽然发生了很多不好得事情而且影响也挺大得, 不过祸福相依嘛, 看开点。
现在感觉就是谷粒商城做不完了, 我得赶紧把基础篇搞完(还有20p), 然后去准备面试了。
```

# 记录: 2022/6/27
```
之前使用的windwos版的nacos感觉太麻烦了, 每次都要手动打开一个黑框框, 于是搬迁到Linux中了
参考 https://www.jb51.net/article/201223.htm 操作的, 
docker network connect命令用于将容器连接到网络。可以按名称或ID连接容器。 一旦连接，容器可以与同一网络中的其他容器通信。
docker network connect common-network 2c05287f844c  # 将一个容器(2c05287f844c)加入到网络common-network中
```

# 记录 2022/7/2
```
谷粒商城基础篇完结了, 因为要准备面试了打算看一看 集合底层实习方式 ArrayList HashMap HashSet List Map TreeMap TreeSet LinkedHashSet LinkedHashMap 红黑树 双向链表 集合等内容
https://www.bilibili.com/video/BV1YA411T76k?p=1&vd_source=620666d44aeeacda9c4cec0e1487afcc 韩顺平老师得Java集合专题, 之前看过韩老师得 数据结构与算法 觉得还不错

谷粒商城基础篇总结:
1. 分布式基础概念
微服务、注册中心、配置中心、远程调用、Feign、网关

2. 基础开发
SpringBoot、SpringCloud、MyBatis-Plus、Vue组件化、阿里云Oss

3. 环境
Vagrant、Linux、Docker、MySQL、Redis、逆向工程&人人开源

4. 开发规范
数据校验JSR303、全局异常处理、全局统一返回、全局跨域处理、枚举类型、业务状态码、VO与TO与PO划分、逻辑删除、Lombok: @Data、@Slf4j

```