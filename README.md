## hellowx

java服务器可以跟微信客户端交互

2018.04.16创建

* controller
    *   BaseController：定义了请求成功方法和请求失败方法
    *   UserController：定义用户的登录目前还没有写完

* bean
  *  ApiResult:请求返回类
  
  
  
  
  提示：
     <p>1:重定向(redirect)默认是跳转到当前controller的
     例如：</p>  
     ``redirect:list //跳转到当前list``
     <p>
          想要跳转到其他的controller需要"../"开头表示跳向上一级
     <p>
     <p>
         如果是以"/"开始则表示根目录        
     </p>