### Volatile
reference: 
1. [volatile关键字](https://ifeve.com/java-volatile%E5%85%B3%E9%94%AE%E5%AD%97/)
2. [嗅探](https://blog.csdn.net/djrm11/article/details/87884657)   
- - - 
#### Scenario:
```
private int years;
private int months
private volatile int days;

year = 2022
month = 3
day = 3
```
1. 可以理解Volatile为一个触发刷新的操作
2. 线程A写入volatile变量后，则在线程A写入volatile之前的所有可见变量也会被写入主存。
   1. 写入数据时，volatile变量放最后写（触发刷新，让可见变量写入主存）
   2. 读取数据时，volatile变量放最先读（触发刷新，防止可见变量从cpu缓存读取）
3. 使其他线程缓存在cpu中的数据失效，重新从主存读取数据。（怎么失效，嗅探）