#Jangod是Django模板语言的JAVA实现版本

# 历史 #

Jangod是Django模板语言的JAVA实现版本。为了凸显JAVA特性，故把“D”拉到后面，取名Jangod。刚接触Django模板就被其继承机制和简便的语法所吸引，费尽千辛万苦终于搜到了基于JAVA的[Templatext](http://code.google.com/p/templatext/)，虽然bug很多，修修补补总算正常运行。但由于个人一些挑剔的需求和其难扩展性，决定自行编写实现，慢慢的Jangod就成型了。


# 现实 #

实现javax.script的脚本语言规范，支持ScriptEngineManager和普通模板引擎两种调用方式。

内建常用标签：
  * Extends  当前模板扩展的父模板
  * Block  模板继承的可替代单元
  * Include  载入其它模板作为本模板的一个片段
  * If…else  条件判断逻辑求值
  * For  按特定的顺序循环一个数据集合
  * Cycle  轮流输出一组变量的其中一个
  * Ifchanged  检查变量当前值与上次存储的值是否发生变化
  * Set  把一个语法运算结果保存到一个新的变量中
内建过滤器：
  * Default  设置空值的默认输出内容
  * Contain  集合或字符串是否包含某个元素或子串
  * Length  取集合元素个数或字符串长度
  * Reverse  倒序排列集合元素（如果集合有序的）
  * Random  从集合中随机取一个元素
  * Equal  逻辑相等判断
  * And  逻辑与运算
  * Or  逻辑或运算
  * Not  真假值取反
  * Date  格式化时间变量
  * Abs  取变量的绝对值
  * Divisible  判断变量是否被整除
  * Add  进行变量的加法运算
  * Multiply  进行变量的乘法运算
  * Divide  进行变量的除法运算
  * Escape  html特殊字符转义
  * Lower  把字符串都转换成小写。
  * Upper  把字符串都转换成大写
  * Truncate  按一定长度截取字符串
  * Cut  去除字符串中的特定子串


# 表象 #

  * 智能的强容错模板解析
> 四大模板文本解析分隔符{{，{%，{#，{!（{\{，{\%，{\#，{\!来转义），可以在模板中任意使用{，%，}}等，只要没有被匹配到，都作为一般的字符处理。
  * 巧妙使用过滤器处理各种复杂的语法运算
> 要简单还是要复杂自己说的算，适合各种开发人员。<br />
> {% ifnotequal a b %} => {% if a|equal:b|not %} ，<br />
> {% if (a and b and c) or d %} => {% if a|and:b,c|or:d %}
  * 各种特性的数据容器
> 数据容器是模板变量的数据来源，目前提供常规的、缓存的、延迟加载的三种容器，也可自定义数据容器在引擎中使用。
  * 自定义标签和过滤器
> 实现提供的接口就可以编写自己的标签或过滤器，并且可重载内建的标签或过滤器，简便的导入功能。

# 外域 #
[jvalog](http://jlog.asfun.net)上的模板[应用](http://code.google.com/p/jvalog/source/browse/#svn/trunk/war/themes/default)