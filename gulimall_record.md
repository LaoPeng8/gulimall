# gulimall problem record

> ***作者: LaoPeng***
>
> ***2022/5/6 18:30 始***



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