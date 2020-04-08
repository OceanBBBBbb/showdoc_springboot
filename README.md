# showdoc_springboot
ShowDoc的Java语言版,Springboot版


### 演示地址
https://www.361shipin.com/showdoc/#/7

    springboot 2.*,maven,mysql。


<br>
  showdoc原版:https://github.com/star7th/showdoc
  <br>
  showdoc的go语言版:https://github.com/kphcdr/godoc
  <br>
      
        前端的代码就是和Go语言版本一样的。后端主要加入了redis做用户缓存.
        
        

## 说明一下

因为时间关系,这个我是清明节使用一天多假期进行go转Java的,因为我主要是写后端,所以前端的内容能不改就不会改,包括一些变量名.所以Java里的一些编码特别是命名都不是很规范..

下面是自己体验后,知道的一些bugs;

## 二次开发
现在还留着模版模块没有开发,二次开发可以先完成这个内容的RUCD来熟悉.内容都比较简单.
后端代码和前端代码都在:`https://github.com/OceanBBBBbb/showdoc_springboot`
前端是`**vue**`,代码是`web_src`目录,`build`后的在`static`里.当然你可以前后端分离部署.


## 线上禁用的功能

### 注册
   考虑到注册后,就可以不断造一些数据,所以先禁用了线上的用户注册功能,如果实在需要体验或者放点文档,可以去`https://www.showdoc.cc/`试试,不过那个是新版本,和这里有些不一样.

### 图片功能
  代码里只做到了粘贴图片上传,本地选图应该是前端还有点认证参数没加的问题.在线图片可以像MD一样的引用.
  同样线上我也禁用了图片上传.


## 一些bug
1. 文章保存后无响应;
3. 文章编辑保存这里的bug还挺多.原文的目录信息等等没有带过来..每次还要重新选目录.
