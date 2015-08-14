# 简介 #
Jangod是根据python的Django模板语言改编实现的java版本，其主要特点是模板继承和简单的语法以及灵活运用的过滤器功能。有关内容可以参看<a href='http://www.woodpecker.org.cn/obp/django/django-faq/templates.html'>Django模板说明</a>

# 基本概念 #
### 输出 ###
{ expression }}<br />
用于输出变量的值到页面中
### 标签 ###
{% tag helper %} ………… {% endtag %}<br />
标签，用于控制页面内容输出流程，有些标签不需要关闭标签符endtag。详细查看各种标签说明。
### 注释 ###
{# comments #}<br />
对中间的代码注释掉，使其不产生任何作用。
### 过滤器 ###
var|filter:arg1,arg2<br />
对变量var执行filter操作，操作带参数arg1,arg2，操作的结果将产生新的值。详细查看各种过滤器说明。
### 表达式 ###
user.id|equal:’3’<br />
由变量和各种过滤器组成，单独的变量也是表达式。

# 标签 #
### extends     继承 ###
用法：{% extends “base.html”%} 或 {% extends fileNameVar %}<br />
说明：表明当前模板继承某个模板，所谓的继承就是父模板把页面划分为几个块，而子模板只需要重新定义需要改写的块的内容既可。
### block      块 ###
用法：{% block a\_name %}<br />
说明：在父模板定义一个页面块，在子模板就可以定义同样的块名来重写该块的内容，没有被重写的块则保留原来的内容。
### include    引入 ###
用法：{% include “sidebar.html”%} 或 {% include fileNameVar %}<br />
说明：在当前模板位置嵌入另一个模板文件的内容，使得被嵌入的模板作为本模板的一个组成部分。
### if        条件判断 ###
用法： {% if expression %}……{%endif%} 或 {%if avar%}…{%else%}…{% endif %}<br />
说明：判断标签所附带的表达式的计算结果是否可判定为真，为真则输出标签后的内容，否则输出else后面的内容（如果有else的话）。变量是否为真参考php的真值规则。
### for       循环 ###
用法： {%for tempVar in users %}……{%endfor%}<br />
说明：循环输出标签里面的内容。从一个集合users里获取一个项，把该项的标识符置为tempVar以便能在标签内使用该项。循环体内自动产生loop(index,count,first,last,length,revindex,revcount)变量，用于表示当前执行位于总体循环中的地位,如{{loop.count}}输出循环的序号。如果users并非一个集合类型（Collection，Array，Map，Iterator），则只循环一次，即tempVar=users。
### ifchanged     是否变更 ###
用法： {% ifchanged tempVar %} … {% endif%}<br />
说明：主要用于循环体中，用于判断该循环项与上一项的内容是否不一致。如果不一致则输出标签体的内容，防止重复输出。
### cycle      轮循输出 ###
用法： {% cycle ‘2’,’3’,’5’%} 或 {%cycle a,b,c as out %}……{%cycle out%}<br />
说明： 主要用于循环体中，根据执行在循环中的位置依次输出cycle的项，如2,3,5,2,3…。as 用于定义一个轮循变量集合，下次则可以直接使用该变量轮循输出。
### set       设置临时变量 ###
用法： {% set varName expression %}<br />
说明： 把一个表达式的值保存到一个新的变量中去，下次则可以直接使用该变量，无需重复计算表达式。

# 过滤器 #
### default    默认值 ###
用法： expression|default:var  或 expression|default:’hello’<br />
说明： 如果expression可判定为真则结果为expression的结果，否则结果为var
### date       格式化日期 ###
用法： expression|date:”yyyy年MM月dd日”<br />
说明： 用于一个格式来格式化日期数据，返回结果为字符串，参见java日期格式化
### contain    是否包含 ###
用法：expression|contain:var<br />
说明：判断集合是否包含某个项var，或，字符串是否包含字串var,返回结果为真假值。
### length     取长度 ###
用法：expression|length<br />
说明：获取集合的项的个数，或字符串的长度，返回结果为数字。
### reverse     反序 ###
用法：expression|reverse<br />
说明：对集合进行倒序排序（集合本身必须是有序的），或，对字符串倒序。返回数组或字符串。
### random      随机 ###
用法：expression|random<br />
说明：随机取集合的一个项，或，随机取一个小于某个值的整型数字。
### equal      等值判断 ###
用法：expression|equal:var<br />
说明：过滤器左右两边的值是否相等。返回真假值。
### not       取反 ###
用法：expression|not<br />
说明：颠倒真值判定结果的真假值，如果expression为真，则返回假。返回真假值。
### and       逻辑与 ###
用法：expression|and:var1,var2,…<br />
说明：把expression和后面的所有参数（参数个数不定）进行逻辑and操作，取结果的真假值。
### or        逻辑或 ###
用法：expression|or:var1,var2,…<br />
说明：把expression和后面的所有参数（参数个数不定）进行逻辑or操作，取结果的真假值。
### abs       绝对值 ###
用法：expression|abs<br />
说明：对数值取绝对值。非数值型的则返回本身。
### divisible     整除 ###
用法：expression|divisible:var<br />
说明：判断数字能否被var整除。参数必须为数字或常规数字字符串。返回真假值。
### add        加 ###
用法：expression|add:var<br />
说明：对数值进行 加 操作。参数必须为数字或常规数字字符串。
### multiply      乘 ###
用法：expression|multiply:var<br />
说明：对数值进行 乘 操作。参数必须为数字或常规数字字符串。
### divide       除 ###
用法：expression|divide:var<br />
说明：对数值进行 除 操作。参数必须为数字或常规数字字符串。
### escape      特殊字符 ###
用法：expression|escape<br />
说明：对字符串替换<,&,>的特殊字符。非字符串则不作任何操作。
### lower       小写 ###
用法：expression|lower<br />
说明：把字符串全部转换为小写。非字符串则不作任何操作。
### upper       大写 ###
用法：expression|upper<br />
说明：把字符串全部转换为大写。非字符串则不作任何操作。
### truncate     截断 ###
用法：expression|truncate:’300’,’…..’<br />
说明：把字符串按一定的长度截断，第一个参数表示截取长度（缺省为100），第二个参数表示截取后添加的后缀（默认为…）。非字符串则不作任何操作。
### cut         消除子串 ###
用法：expression|cut:var  或 expression|cut:’abc’<br />
说明：去除字符串中的所有子串var。

# 一些例子 #
{% if a|or:b|not|and:c,d %}  ＝＝ !(a or b) and c and d<br />
{% for user in userList|reverse %}  倒序输出列表