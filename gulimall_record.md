# gulimall problem record

> ***作者: LaoPeng***
>
> ***2022/5/6 18:30 始***



# application.yml 修改 port 不生效

```
修改 server: port: 8000 不生效 端口依旧是 8080 不管是重启idea还是 Rebulid project 还是刷新maven都没用
后发现 项目是使用 http://start.aliyun.com 创建的 自带一个 application.properties 中配置了 8080 虽然删除了，
但是我感觉它还在的样子因为 properties 比 yml 优先级高 我就像会不会是 properties 中的8080 覆盖了 yml中的 8000 
但是properties 已经删除了，但是我感觉可能是没有删干净，重写新建一个 properties文件修改端口 8000 果然成功了，
我明白了 肯定是没删干净，后 target 中发现 properties果然还在，删除后，yml 配置的8000端口生效。
```


# java.lang.NoClassDefFoundError: ConfigurationBeanFactoryMetadata

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




# 2022/5/25 今天啥也没干

```
今天有点抑郁，啥也没干，主要就是昨天做谷粒商城时配置gateway得一个 filters: RewritePath=路径 重写路径感觉有点看不太懂，
gateway配置跨域时，虽然是固定配置直接可以从以前的项目copy过来但是感觉面试问的话可能我也答不上来。
其次就是抖音经常刷到的一些面试，感觉问的东西都挺高级的，我都不太会的样子很理论感觉我也是根本记不住的样子，
自己简历上写的一些东西 都是很久以前学的 比如快排 都是2020年学的了，现在看记得大概理论但是写估计也写不出来，
就是感觉拿捏不住还要jvm sql优化啥的，感觉会但是说又说不出来什么，以前学过的东西感觉自己忘的很快。 
能不能找到实习哟。不过不管怎么说明天继续Java吧！
```


# 2022/5/26

```
今天编写 删除分类时老师说 需要判断该分类是否被其他地方使用, 醍醐灌顶, 以前居然写谷粒学院时 删除分类就是 判断 当前分类下如果还有子分类就不让删除, 只能删除没有子分类的分类,
这完全就是说从后台管理系统的角度看的, 如果前台系统 有商品是属于这个分类的, 就算这个分类没有子分类 删除了, 那这些属于该分类的商品怎么办?
```

