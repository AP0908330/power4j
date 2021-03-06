定义：
      提供一个统一的接口，用来访问子系统的一群接口
      定义一个高层接口，让子系统更容易使用
优点：
    简化调用过程，无需了解深入子系统、防止带来风险
    减少系统依赖、松散耦合
    更好的划分访问层次
    符合迪米特法则，即最少知道原则
缺点：
    增加子系统、拓展子系统行为容易引入风险
    不符合开闭原则
使用场景：
    子系统越来越复杂、增加外观模式提供简单调用接口
    构建多层系统结构，利用外观对象作为每一层的入口，简化层间调用（MVC三层架构）
    后台接口版本升级

源码：
    jdk: 
    spring：
    spring JDBC：JdbcUtils
    mybatis：Configuration
    tomcat：RequestFacade、ResponseFacade、StandardSessionFacade、DisposableConnectionFacade