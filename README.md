# BSM OAuth Java
[BSM Auth](https://github.com/BSSM-BSM/BSM-Auth-Backend-V1)의 OAuth기능을 Java에서 사용하기 쉽게 만들어주는 라이브러리입니다.

## 설치
gradle로 설치
```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.leehj050211:bsm-oauth-java:1.1.0'
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
    <version>1.1.0</version>
</dependency>
```


## 사용하기
```java
// BSM OAuth 객체 초기화
BsmOauth bsmOauth = new BsmOauth(BSM_AUTH_CLIENT_ID, BSM_AUTH_CLIENT_SECRET);
// 인증코드로 토큰 발급
String token = bsmOauth.getToken(authCode);
// 토큰으로 유저 정보 가져오기
BsmUserResource resource = bsmOauth.getResource(token);
// 가져온 유저 정보 확인
System.out.println(resource.getUserCode());
```

### 예외 처리하기
```java
try {
    String token = bsmOauth.getToken(authCode);
    bsmOauth.getResource(token);
} catch (BsmOAuthCodeNotFoundException e) {
    // 인증코드를 찾을 수 없을 때
} catch (BsmOAuthCodeNotFoundException e) {
    // 토큰을 찾을 수 없을 때
} catch (BsmOAuthInvalidClientException e) {
    // 클라이언트 정보가 잘못되었을 때
}
```

### 학생, 선생님 계정 구분하기
getRole 메서드로 학생, 선생님 계정을 구분할 수 있습니다. 
```java
if (resource.getRole() == BsmUserRole.STUDENT) {
    // 학생 이름 확인
    System.out.println(resource.getStudent().getName());
}
if (resource.getRole() == BsmUserRole.TEACHER) {
    // 선생님 이름 확인
    System.out.println(resource.getTeacher().getName());
}
```

### 학생, 선생님의 고유 정보에 바로 접근하기

getRawResource 메서드로 원시 데이터를 가져올 수 있습니다.

```java
RawBsmOAuthResource rawResource = bsmOauth.getRawResource(token);
// 학생 또는 선생님의 이름 확인
System.out.println(rawResource.getName());
// 학생의 번호 확인 (만약 resource.getRole()이 TEACHER일 경우 null이 출력됩니다.)
System.out.println(rawResource.getStudentNo());
```
