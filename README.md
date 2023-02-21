# BSM OAuth Java
[BSM Auth](https://github.com/BSSM-BSM/BSM-Auth-Backend-V1)의 OAuth기능을 Java에서 사용하기 쉽게 만들어주는 라이브러리입니다.

## 사용하기
```java
// BSM OAuth 객체 초기화
BsmOauth bsmOauth = new BsmOauth(BSM_AUTH_CLIENT_ID, BSM_AUTH_CLIENT_SECRET);
```
```java
BsmUserResource resource;
try {
    // 인증코드로 토큰 발급
    String token = bsmOauth.getToken(authCode);
    // 토큰으로 유저 정보 가져오기
    resource = bsmOauth.getResource(token);
} catch (BsmOAuthCodeNotFoundException e) {
    // 인증코드를 찾을 수 없을 때
} catch (BsmOAuthCodeNotFoundException e) {
    // 토큰을 찾을 수 없을 때
} catch (BsmOAuthInvalidClientException e) {
    // 클라이언트 정보가 잘못되었을 때
}
// 가져온 유저 정보 확인
System.out.println(resource.getUserCode());
```
### 학생, 선생님 계정 구분하기
getRole 메서드로 학생, 선생님 계정을 구분할 수 있습니다. 
```java
switch (resource.getRole()) {
    case STUDENT -> {
        // 학생 이름 확인
        System.out.println(resource.getStudent().getName());
    }
    case TEACHER -> {
        // 선생님 이름 확인
        System.out.println(resource.getTeacher().getName());
    }
};
```



## 설치
gradle로 설치
```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.leehj050211:bsm-oauth-java:1.0.1'
}
```
maven으로 설치
```maven
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
<dependency>
    <groupId>com.github.leehj050211</groupId>
    <artifactId>bsm-oauth-java</artifactId>
    <version>1.0.1</version>
</dependency>
```
