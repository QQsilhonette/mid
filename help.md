##硬件能力公共服务

*  **主项目parent包**
   1. 指定整个应用的dependencyManagement
   2. 定义项目的发布的仓库地址distributionManagement
   3. 所有第三方依赖的版本号全部定义在properties下
   4. 所有内部模块依赖版本号统一使用**${project.version}**
   5. 指定所有的子模块modules

* **项目子模块pom.xml**
   1. 明确定义parent模块的`artifactId，groupId，version`
   2. 不要定义子模块的version（同parent保持一致）
   3. 子模块无需定义groupId
   5. 子模块所有的依赖包版本全部集成parent模块，即：子模块不得定义依赖包版本号
   5. 子模块需定义是否需要deploy到私服`<maven.deploy.skip>true</maven.deploy.skip>`
   6. 对于需要depoly的子模块【对外发布的，比如dubbo提供的client 二方包】不应该依赖重量级jar包(比如：spring,mybatis等)

* **子模块packaging为pom**
   1. 指定所有的子模块modules
   2. 无需定义groupId
   3. 明确定义parent模块的`artifactId，groupId，version`

以上定义规则保证了项目内部模块之间的依赖版本统一，第三方依赖包版本不冲突

### MAVEN聚合多个子模块项目版本号修改

我们可以通过maven的插件方式来升级整个项目的版本号。方案如下：

* 在项目顶层pom中添加version插件

```
 <plugin>
      <groupId>org.codehaus.mojo</groupId>
      <artifactId>versions-maven-plugin</artifactId>
      <version>2.3</version>
      <configuration>
          <generateBackupPoms>false</generateBackupPoms>
      </configuration>
  </plugin>
```

* 在项目根目录下执行以下命令修改版本号
mvn versions:set -DnewVersion=2.0.0-SNAPSHOT
```
// 设置新的版本号未2.0.0-SNAPSHOT


```
以上命令会将 parent/pom.xml版本修改为2.0.0-SNAPSHOT，且会修改所有子模块内 parent的version为1.2.0-SNAPSHOT。所以建议子模块不设置version，自动从parent继承version即可



