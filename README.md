# kotlinjpa
Kotlin是一门很新的语言，它的设计和一些语法糖使它开发起来比Java要简洁很多，效率自然也提高了不少，性能方面，跟java也是不相上下的。
没有接触过Kotlin的朋友可以先到官网看详细教程
>https://www.kotlincn.net/docs/reference/server-overview.html

众所周知SpringBoot是一个对新手极其友好的框架，它使开发者省去了很多的重复且复杂的配置，让开发者轻而易举地享受到Spring的好处。Jpa是国外很流行和Spring官方支持的一个全自动ORM框架，开发者甚至不需要写一句SQL即可进行数据库操作（包括建表）。
Kotlin，SpringBoot和Kotlin这三把利剑合起来会产生什么样的效果呢，我们接着往下看吧

## **一、使用项目生成器**
访问这个网址https://start.spring.io/#!language=kotlin
![这里写图片描述](http://img.blog.csdn.net/20180313103411262?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXFfMzIxOTgyNzc=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
1. 选择使用Maven构建
2. 选择使用Kotlin作为编程语言
3. 选择SpringBoot版本为2.0.0
4. 依赖输入Web，Jpa，MySQL

下载后解压用Idea打开即可，之后就是等Idea帮你自动构建了
## **二、配置数据库**
笔者比较喜欢yml那种层次分明的格式，所以配置使用yml，文件名为application.yml

```
spring:
    datasource:
        url: jdbc:mysql://127.0.0.1:3306/test
        username: root
        password: yourpassword
        driverClassName: com.mysql.jdbc.Driver
    jpa:
      properties:
        hibernate:
          hbm2ddl:
            auto: create
```
jpa最多智能帮你生成数据表，不能创建数据库，所以要检查数据库的url是否正确

## **三、新建实体类**

```
import javax.persistence.*

@Entity
@Table(name = "test_student")
data class Student(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id:Long?, var name:String?) {
    constructor() : this(null,null) {
    }
}
```
kotlin中的data类可以省略setter和getter但要求有非空的构造函数，而Jpa的Entity注解要求有空构造函数，所以实体类需要这样写，必须指定主键的生成策略，否则会报错

## **四、新建自定义Repository**

```
import org.springframework.data.jpa.repository.JpaRepository

interface StudentRepository:JpaRepository<Student,Long> {
    fun findByName(name: String): List<Student>
}
```
自定义Repository必须继承自Jpa里的Repository，如JpaRepository
熟悉Jpa的人都知道，查询单表的时候是多么的简单，不需要写一句SQL
 
## **五、新建Controller**

```
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/studnet")
class StudentController{
    @Autowired
    lateinit var studentRepository:StudentRepository
    @RequestMapping("findByName")
    fun finByName(name:String):List<Student>{
        return studentRepository.findByName(name);
    }
    @RequestMapping("add")
    fun add(name:String):String{
        val student=Student(id=null,name = name)
        studentRepository.save(student)
        return "success"
    }
}
```
这个Controller也是Spring常见的Controller的写法，特别的地方在于使用@Autowired注解的变量必须使用lateinit修饰，表示延迟初始化

## **六、启动程序**
运行KotlinjpaApplication的main方法
若访问以下网址返回正确的结果则表明项目没有问题

http://localhost:8080/studnet/add?name=jj

http://localhost:8080/studnet/findByName?name=jj
