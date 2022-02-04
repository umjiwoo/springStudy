# springStudy
#H1,22.02.04 build.gradle설정  
gradle버전에 따라 플러그인 명시 방법 다름  
buildscript{~} 하니까 빌드 안됨  
<바뀐 방법>
```
//플러그인 추가
plugins{
  id '플러그인이름' version '버전'
}
```
```
//의존성 주입
dependencies{
  implementation('모듈')
}
```
----------------------------------------------------------------------------
