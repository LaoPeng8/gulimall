# gulimall problem record

> ***作者: LaoPeng***
>
> ***2022/5/6 18:30 始***



# application.yml 修改 port 不生效

修改 server: port: 8000 不生效 端口依旧是 8080 不管是重启idea还是 Rebulid project 还是刷新maven都没用

后发现 项目是使用 http://start.aliyun.com 创建的 自带一个 application.properties 中配置了 8080 虽然删除了，但是我感觉它还在的样子因为 properties 比 yml 优先级高 我就像会不会是 properties 中的8080 覆盖了 yml中的 8000 但是properties 已经删除了，但是我感觉可能是没有删干净，重写新建一个 properties文件修改端口 8000 果然成功了，我明白了 肯定是没删干净，后 target 中发现 properties果然还在，删除后，yml 配置的8000端口生效。