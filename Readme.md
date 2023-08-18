# I.	개요
## 1.	프로젝트 개요
건식 사료도, 습식 사료도 먹지 않는 개
간식은 먹는데 건강에 좋은지 모름
직접 만들어주고 싶은데 요리를 잘 못함
쿠킹 클래스를 가보려고 해도 너무 본격적임. 

## 2.	프로젝트 사용 도구 및 버전
+ OS: Windows 10
+ IDE: IntelliJ, VSCode
+ Build Automation Tool: Gradle(v8.1.1)
+ Frontend: Node.js(v18.17.0), React(v9.6.7)
+ Backend: Spring boot(v3.1.1), Spring data JPA(v3.1.1), Spring Security(v6.1.1), Flask(v2.3.2)
+ Database: h2(develop, v1.4.2), postgreSQL(deploy, v.15.3)
+ JVM: azul-17
+ WS: nginx(reverse proxy, v1.18.0)
+ WAS : tomcat(spring-boot)
+ WebRTC : OpenVidu(v2.28.0)
+ CI/CD : Jenkins
+ 서버: AWS EC2 Ubuntu 20.04 LTS
+ Cloud Storage : Amazon S3
+ 이슈 관리: Jira
+ 형상 관리: GitLab
 
# II.	빌드 
## 1.	빌드 환경 변수
a)	`VERSION=$(curl—silent https://api.github.com/repos/docker/compose/releases/latest | jq .name -r)` : Docker-compose의 최신 릴리즈 정보

b)	`DESTINATION=/usr/bin/docker-compose` : docker-compose 실행 파일이 저장될 위치

c)	`Docker-compse –p frontend build` : Docker-compose는 보통 docker-compose.yml이 저장 된 디렉토리 명을 환경변수로 설정하는데 본 프로젝트에서는 docker-compose.yml이 root에 있어 이러한 자동 환경변수 설정이 되지 않기 때문에 –p 를 이용해 frontend라고 수동으로 명명해주었다

## 2.	배포 순서
a)	`sudo su`   
b)	`cd /opt/openvidu`   
c)	`./openvidu start`   
d)	`cd /`   
e)	`sudo docker-compose –p frontend build`   
f)	`sudo docker-compose –p frontend up –d`   
g)	`sudo service nginx restart`   

## 3.	배포 시 특이사항
a)	Docker 사용: Backend, Frontend, Nginx, Jenkins, YoloV5, OpenVidu, DB를 각각의 Docker Container로 관리하여 독립성을 보장시켰다.    
b)	Docker-compose 사용: Docker-compose를 사용하여 각각의 Dockerfile을 통해 여러 개의 컨테이너를 build 후 생성하여 다중 컨테이너 관리, 설정의 일관성, 개발 효율성을 증대시켰다.   
c)	Jenkins pipeline 사용: jenkins pipeline으로 한 프로젝트에 있는 backend단과 frontend단을 동시에 자동 배포 하여 배포 환경 테스트 효율성을 증가시켰다.   
d)	Nginx의 reverse proxy 사용: 가비아에서 구입한 doggy-yummy 도메인 뒤에 /, /api, /v1 경로로 reverse proxy 하여 각각 front, back, object 
detection 단으로 보내주었다.   
e)  배포 시에는 `application-secure.properties`의 `current.front`, `current.back`을 `${deploy.front}`, `${deploy.back}`으로 바꿔주어야 한다.   

# III.	외부 서비스 정보
## 1.	소셜 인증
### a)	구글
-	https://console.cloud.google.com/apis/dashboard
-	로그인 후 사용자 인증 정보 – Oauth2.0 클라이언트 ID에 추가
-	앱 이름 등록
-	승인된 자바스크립트 원본 추가
-	승인된 리디렉션 URI추가
+ 배포 환경: https://doggy-yummy.site/api/login/oauth2/code/google
+ 로컬 환경: http://localhost:8083/api/login/oauth2/code/google


### b)	네이버
-	https://developers.naver.com/products/login/api/api.md
-	로그인 후 앱 추가
-	Application-내 애플리케이션-개요-애플리케이션 정보에서 Client Id, Client Secret을 복사해 application-secure.properties의 spring.security.oauth2.client.registration.naver.client-id, spring.security.oauth2.client.registration.kakao.client-secret에 붙여넣음
-	내 애플리케이션-API 설정에서 필요한 회원 정보를 체크
-	아래의 로그인 오픈 API 서비스 환경에 서비스 URL 및 Callback URL을 설정
+ https://doggy-yummy.site/api/login/oauth2/code/naver
+ http://localhost:8083/api/login/oauth2/code/naver

### c)	카카오
-	https://developers.kakao.com/product/kakaoLogin
-	로그인 후 애플리케이션 추가
-	앱 설정-앱 키-REST API키를 application-secure.properties의 
spring.security.oauth2.client.registration.kakao.client-id에 추가
-	제품 설정-카카오 로그인-보안-Client Secret-코드를 spring.security.oauth2.client.registration.kakao.client-secret에 추가
-	제품 설정-카카오 로그인-동의 항목에서 필요한 정보들을 체크
-	제품 설정-카카오 로그인에서 Redirect URI를 지정
+ 로컬 환경에서는 http://localhost:8083/api/login/oauth2/code/kakao
+ 배포 환경에서는 https://doggy-yummy.site/api/login/oauth2/code/kakao



