# springStudy
#H1 22.02.04 build.gradle설정  
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
#H1 22.02.05
-단위 테스트를 진행하는 이유?
1.서버를 실행하고 테스트 도구를 통해 응답을 요청하고 받는 수행을 반복할 필요 없어짐
2.System.out.println()을 통해 일일이 확인할 필요 없음-테스트를 통과하면 검증된 것
3.새로운 기능 추가 시 기존에 있던 기능이 여전히 잘 구동하는 지 확인하기 편리함-각각의 테스트 코드를 실행해보면 됨

-내장 WAS 사용의 장점
톰캣 설치의 불필요,각각 다른 서버에서 해당 프로그램을 수행할 때 외장 WAS를 쓴다면 각 서버 컴퓨터마다 WAS의 종류,버전,설정을 일치시켜야 프로그램이 구동됨
그러나 내장 WAS를 사용한다면 언제 어디서나 같은 환경의 프로그램을 배포할 수 있게됨