EmptiedEditText
====

简介：
-------
一个可清空删除的EditText</br>
附带必选项功能</br>
****
示例：
-------
![](https://github.com/Macsags/EmptiedEditText/blob/master/ohho.gif)
</br>
****
日志
-------
2020/6/22
* 第一次上传
* 删除按钮改为16px
* 必选项背景色可修改
****
如何使用How to：
-------
To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

gradle
maven
sbt
leiningen
Add it in your root build.gradle at the end of repositories:

```
allprojects { 
		repositories { 
			... 
			maven { url 'https://www.jitpack.io' } 
		} 
	}  		
```

Step 2. Add the dependency<br> 

```
allprojects { 
	dependencies {
	        implementation 'com.github.Macsags:EmptiedEditText:1.0.1'
	      } 
	} 
```	
****
使用方法：
-------
```
        ConstraintLayout constraintLayout = findViewById(R.id.cl);//外层布局
        EmptiedEditText emptiedEditText = findViewById(R.id.ee);//清空输入框
        emptiedEditText.layout(constraintLayout,0xffFFF1F1);//设置外层布局变颜色，动态更改颜色
```
****
请关注
-------
  [我的博客](https://blog.csdn.net/qq_32368129)
  
