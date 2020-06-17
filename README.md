# Java 学习示例
## 证书生成
```
几个主要的参数是：
keyalg：指定RSA加密算法；
sigalg：指定SHA1withRSA签名算法；
validity：指定证书有效期3650天；
alias：指定证书在程序中引用的名称；
dname：最重要的CN=www.sample.com指定了Common Name，如果证书用在HTTPS中，这个名称必须与域名完全一致。
```
## 具体命令
```
keytool -storepass 123456 -genkeypair -keyalg RSA -keysize 1024 -sigalg SHA1withRSA -validity 3650 -alias mycert -keystore my.keystore -dname "CN=www.csfrez.com, OU=csfrez, O=csfrez, L=BJ, ST=BJ, C=CN"
```

## Java函数式编程
* [函数式编程思想概论](https://www.ibm.com/developerworks/cn/java/j-understanding-functional-programming-1/index.html)

* [函数式编程中的重要概念](https://www.ibm.com/developerworks/cn/java/j-understanding-functional-programming-3/index.html)

* [Java 8 的 Lambda 表达式和流处理](https://www.ibm.com/developerworks/cn/java/j-understanding-functional-programming-2/index.html)

* [使用 Vavr 进行函数式编程](https://www.ibm.com/developerworks/cn/java/j-understanding-functional-programming-4/index.html)

* [深入解析 Monad](https://www.ibm.com/developerworks/cn/java/j-understanding-functional-programming-5/index.html)
