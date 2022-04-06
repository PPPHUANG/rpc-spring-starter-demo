# rpc-spring-starter-demo

### demoservice

服务接口

### demoserver

实现服务接口

### democlient

服务调用

### 本地运行

1. 克隆`rpc-spring-starter`到本地install
   ```shell
   git clone git@github.com:PPPHUANG/rpc-spring-starter.git
   mvn clean install -DskipTests=true
   ```
2. 启动ZK（默认地址是127.0.0.1:2128，其他ZK地址可在项目配置文件中修改）
3. 启动`demoserver`
4. 启动`democlient`
5. 调用`democlient`服务中的HTTP接口即可
   ```
   http://localhost:8090/test
   http://localhost:8090/test2
   http://localhost:8090/test3
   ```