# kotlin-spring-rest-api-boilerplate

## 요구사항

### 1. 회원가입 (`/v1/users`)

- [x] 중복된 이메일을 사용할 수 없다.
- [x] User 는 이메일 인증 전 상태(`BEFORE_EMAIL_CHECK`)가 된다.
- [x] userId 와 인증 토큰을 포함한 url 링크를 이메일로 발송한다.

### 2. 이메일 인증 완료 (`/v1/users/{userId}/email/verification`)

- [ ] url 링크를 클릭하면 이메일 인증이 완료된다.
- [ ] 이메일은 인증은 url 링크가 발송되고 1시간 이내에 진행해야 된다.
- [ ] 여러 번 링크가 발송된 경우, 마지막으로 보낸 토큰을 기준으로 확인한다.
- [ ] User 는 이메일 인증 완료 상태(`EMAIL_CHECK_COMPLETED`)가 된다.
- [ ] User 에 저장된 인증 토큰이 없으면 404 리턴
- [ ] 이미 인증된 토큰으로 다시 요청을 하면 400 리턴

### 3. 로그인 (`/v1/users/login`)

### 4. 인증 메일 재발송 (`/v1/users/email/resend`)

- [ ] 로그인한 사용자는 인증 메일을 다시 발송할 수 있다.

### 5. refreshToken 갱신 (`/v1/users/refresh`)

### 6. 내 정보 조회 (`/v1/users/me`)
