# kotlin-spring-rest-api-boilerplate

## 요구사항

### 1. 회원가입

- [x] 중복된 이메일을 사용할 수 없다.
- [x] User 는 이메일 인증 전 상태(`BEFORE_EMAIL_CHECK`)가 된다.
- [x] userId 와 인증 토큰을 포함한 url 링크를 이메일로 발송한다.

### 2. 이메일 인증

- [x] url 링크를 클릭하면 이메일 인증이 완료된다.
- [x] 이메일은 인증은 url 링크가 발송되고 1시간 이내에 진행해야 된다.
- [x] 여러 번 링크가 발송된 경우, 마지막으로 보낸 토큰을 기준으로 확인한다.
- [x] User 는 이메일 인증 완료 상태(`EMAIL_CHECK_COMPLETED`)가 된다.

### 3. 로그인

- [ ] 회원가입시 입력했던 이메일과 패스워드가 일치하면 로그인은 성공한다.
- [ ] 로그인 성공시 accessToken 과 refreshToken 을 발급한다.
- [ ] accessToken 은 발급 후 30분 뒤 만료된다.
- [ ] refreshToken 은 발급 후 2주 뒤 만료된다.
- [ ] accessToken 을 request header 에 전달하면 인증이 필요한 API 를 호출할 수 있다.

### 4. accessToken 갱신

- [ ] 만료된 accessToken 과 로그인시 발급했던 refreshToken 을 전달하면 새로운 accessToken 을 발급한다. 

### 5. 인증 메일 재발송 

- [ ] 로그인한 사용자의 메일 인증 메일을 다시 발송한다.
- [ ] 메일 인증이 완료되지 않은 경우에만 재발송할 수 있다.

### 6. 내 정보 조회

- [ ] 로그인한 사용자의 정보를 조회한다.

## API

### 0. 공통 스펙

#### Response

##### 에러

예시

```json
{
  "errorCode": "ERR-901",
  "message": "잘못된 요청입니다."
}
```

### 1. 회원가입

#### Request

POST `/v1/users`

#### Response

### 2. 이메일 인증

#### Request

GET `/v1/users/{userId}/verifyEmail`

#### Response

##### 정상

body 없이 http 상태코드 200 으로 응답

##### 에러

| http 상태코드 |  에러코드   | 설명            |
|:---------:|:-------:|:-------------------|
|    404    |ERR-102| 이메일 인증 토큰을 찾을 수 없는 경우 |
|    400    |ERR-121| 이메일이 이미 인증된 경우     |
|    400    |ERR-122| 이메일 인증 토큰이 일치하지 않는 경우 |
|    400    |ERR-123| 이메일 인증 토큰이 만료된 경우 |

### 3. 로그인

#### Request

POST `/v1/users/login`

#### Response

### 4. accessToken 갱신

#### Request

POST `/v1/users/refresh`

#### Response

### 5. 인증 메일 재발송

#### Request

POST `/v1/users/email/resend`

#### Response

### 6. 내 정보 조회 

#### Request

GET `/v1/users/me`

#### Response
