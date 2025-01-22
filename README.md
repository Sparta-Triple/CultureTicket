# 🎫Culture Ticket
<img src="https://github.com/user-attachments/assets/99280765-13cd-463c-b60e-f60ec33a9d32" width=600; width=500 />
<br>

## ❇️ [프로젝트 개요](https://github.com/project-gongsimchae/gongsimchae/wiki)
#### 대규모 트래픽을 효과적으로 처리하여 안정적이고 확장 가능한 '문화생활 티케팅 서비스'
- [개발과정](https://github.com/Sparta-Triple/CultureTicket/wiki/%EA%B0%9C%EB%B0%9C%EA%B3%BC%EC%A0%95)
- [ERD](https://github.com/user-attachments/assets/3ed7a77c-ba7e-4ec0-99f8-1a4837895466)
- [Convention](https://github.com/Sparta-Triple/CultureTicket/wiki/Convention)
- [그라운드 룰](https://github.com/Sparta-Triple/CultureTicket/wiki/GroundRule)

</br>

## 👨‍👩‍👧‍👦 팀원 소개
| <div align="center">[홍예석](https://github.com/yshong1998)</div>                         | <div align="center">[김우진](https://github.com/kwj0605)</div>                                                                                                           | <div align="center">[성은정](github.com/SEJ123)</div>                      | 
| :---------------------------------------------------------------------------- |:---------------------------------------------------------------------------------------------------------------------------------------------------------------------| :---------------------------------------------------------------------------- | 
| <div align="center"><img src="https://github.com/user-attachments/assets/e4bb1ea5-ac3a-464d-aacc-c28159a61b31" width=150 /></div> | <div align="center"><img src="https://github.com/user-attachments/assets/2550cf81-f2db-4f4d-9243-9a91c66ca8f7" width=150 /></div>                                    | <div align="center"><img src="https://github.com/user-attachments/assets/c7f702fe-95ff-4a4e-8f6a-2b751f63a12d" width=150 /></div> 
| <div align="center"> `공통 기능` <br>이미지 CRUD<br/>`공구`<br>아이템 데이터 활용 전반, <br> 카테고리별 조회, 정렬 기능 <br> `관리자 페이지` <br> 이벤트, 쿠폰, 주문, <br> 상품, 관리 기능,  </div>          | <div align="center"> `공통`<br> 서버 배포 및 관리<br> 젠킨스 빌드 연동<br> NGINX 무중단 배포<br> HTTPS 인증 <br> `소분` <br> 소분글 CRUD <br> 소분 상세 페이지 <br> `공구` <br> 공동구매 성공 <br> 실패 로직 </div> | <div align="center"> `공구` <br> 아이템 & 이벤트 CRUD <br> 아이템 옵션 <br> `소분` <br> 메인페이지 <br> 지역 태그 및 내용 검색 <br> `공통` <br> 찜 </div>                     | 

  
## ✨프로젝트 핵심 목표
대규모 트래픽을 효과적으로 처리하여 안정적이고 확장 가능한 '문화생활 티켓팅 서비스'를 구축하여 사용자들에게 원활한 티켓 구매 경험을 제공

- 대규모 트래픽 상황에 알맞은 메시징 시스템 Kafka or RabbitMQ
- 모니터링 툴을 통해 병목 구간 파악 및 성능 개선
- SAGA패턴을 사용한 자동 롤백과 데이터 일관성 유지
- 알맞는 모니터링 시스템 Grafana, Prometheus or ELK Stack
- Mock MVC를 이용한 테스트 코드
- Database Replication 구성에 따른 Read / Write 분리
    - Redis로 우선 처리, DB에 백업 저장
- Cache를 사용한 DB 복제 지연 문제 해결





## ✔️KEY SUMMARY





## 📁인프라 아키텍처
<img src="https://github.com/user-attachments/assets/8eb580bb-fd78-41a6-9f34-daaad359a21e" width=600; width=500 />




## 🚀 기술 스택

OS | Stack
--- | --- |
Language | ![Java](https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white) ![Spring](https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white) ![HTML5](https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white) ![CSS3](https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white) ![JavaScript](https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)
IDE | ![intellij-idea](https://img.shields.io/badge/intellij%20idea-000000?style=for-the-badge&logo=intellijidea&logoColor=white) 
Framework | ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
Build Tool | ![gradle](https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)
Database | ![MySQL](https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white) ![Mongo DB](https://img.shields.io/badge/mongodb-47A248?style=for-the-badge&logo=mongodb&logoColor=white) ![Redis](https://img.shields.io/badge/redis-FF4438?style=for-the-badge&logo=redis&logoColor=white)
Library | ![Spring Security](https://img.shields.io/badge/spring%20security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white) ![Thymeleaf](https://img.shields.io/badge/thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white) ![OAuth 2.0 Client](https://img.shields.io/badge/OAuth%202.0%20Client-4b4b4b?style=for-the-badge) ![JUnit5](https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=junit5&logoColor=white) ![JPA](https://img.shields.io/badge/JPA-6DB33F?style=for-the-badge&logo=jpa&logoColor=white) ![Java Mail](https://img.shields.io/badge/Java%20Mail-3a75b0?style=for-the-badge) ![apache kafka](https://img.shields.io/badge/apache%20kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white) ![elastic search](https://img.shields.io/badge/elastic%20search-005571?style=for-the-badge&logo=elasticsearch&logoColor=white) ![Query dsl](https://img.shields.io/badge/query%20dsl-007396?style=for-the-badge&logo=querydsl&logoColor=white) ![Spring Batch](https://img.shields.io/badge/spring%20batch-6DB33F?style=for-the-badge&logo=springbatch&logoColor=white) ![Web Socket](https://img.shields.io/badge/web%20socket-F7DF1E?style=for-the-badge&logo=websocket&logoColor=white) ![Spring AI](https://img.shields.io/badge/spring%20ai-6DB33F?style=for-the-badge&logo=springai&logoColor=white)
API | ![PortOne Payment](https://img.shields.io/badge/portone-c1272d?style=for-the-badge) ![Font Awesome](https://img.shields.io/badge/Font%20Awesome-528DD7?style=for-the-badge&logo=fontawesome&logoColor=white) ![Bootstrap](https://img.shields.io/badge/Bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white) ![google](https://img.shields.io/badge/google-4285F4?style=for-the-badge&logo=google&logoColor=white) ![naver](https://img.shields.io/badge/naver-03C75A?style=for-the-badge&logo=naver&logoColor=white) ![kakao](https://img.shields.io/badge/kakao-FFCD00?style=for-the-badge&logo=kakao&logoColor=white) ![jQuery](https://img.shields.io/badge/jQuery-0769AD?style=for-the-badge&logo=jquery&logoColor=white)
DevOps | ![Amazon Web Services](https://img.shields.io/badge/amazon%20aws-232F3E?style=for-the-badge&logo=Amazon%20Web%20Services&logoColor=white) ![amazon s3](https://img.shields.io/badge/amazon%20s3-569A31?style=for-the-badge&logo=amazon%20s3&logoColor=white) ![amazon rds](https://img.shields.io/badge/amazon%20rds-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white) ![jenkins](https://img.shields.io/badge/jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white) ![nginx](https://img.shields.io/badge/nginx-009639?style=for-the-badge&logo=nginx&logoColor=white) ![letsencrypt](https://img.shields.io/badge/letsencrypt-003A70?style=for-the-badge&logo=letsencrypt&logoColor=white) ![gabia](https://img.shields.io/badge/gabia-4285F4?style=for-the-badge&logo=gabia&logoColor=white) ![Elastic Cache](https://img.shields.io/badge/amazon%20elasticache-C925D1?style=for-the-badge&logo=amazonelasticache&logoColor=white) ![amazon ec2](https://img.shields.io/badge/amazon%20ec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white)
Tools | ![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white) ![git](https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white) ![slack](https://img.shields.io/badge/slack-4A154B?style=for-the-badge&logo=slack&logoColor=white) ![notion](https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white) ![jira](https://img.shields.io/badge/jira-0052CC?style=for-the-badge&logo=jira&logoColor=white) ![confluence](https://img.shields.io/badge/confluence-172B4D?style=for-the-badge&logo=confluence&logoColor=white) ![discord](https://img.shields.io/badge/discord-5865F2?style=for-the-badge&logo=discord&logoColor=white) ![kibana](https://img.shields.io/badge/kibana-005571?style=for-the-badge&logo=kibana&logoColor=white) ![dbeaver](https://img.shields.io/badge/dbeaver-382923?style=for-the-badge&logo=dbeaver&logoColor=white) ![figma](https://img.shields.io/badge/figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white) 


## 🗣️ 기술적 의사결정
서비스 | 요구 사항 | 기술명 | 선택 근거/목적
--- | --- |--- | --- |
공연 서비스 | 공연 조회 | Redis Cache | 공연 정보와 같은 자주 조회되는 데이터는 매번 DB에서 직접 조회하는 것보다 Redis 캐시 시스템을 활용하여 응답 속도를 개선
공연 서비스 | 공연 랭킹 | Redis Sorted Set| 공연 랭킹과 같은 실시간으로 자주 업데이트, 조회 되는 데이터를 빠르게 처리하기 위해
공연 서비스 | 공연 조회수 | Redis Sorted Set | 공연 조회수와 같은 실시간으로 자주 업데이트, 조회 되는 데이터를 빠르게 처리하기 위해
공연 서비스 | 공연 조회 대기열 | Redis Sroted Set | 많은 사용자가 동시에 하나의 공연에 대해 조회하는 상황에 서버 과부하가 발생할 가능성 존재, 놀이동산 방식의 대기열을 구현하여 서버가 감당할 수 있는 사용자를 주기적으로 처리
쿠폰 서비스 | 쿠폰 발급 | Distributed Lock | 동시성 문제, 중복 발급 방지, 쿠폰 수량 차감의 정확성을 보장하고, 시스템의 안정성과 데이터 일관성을 유지하기 위해
티켓 서비스| 티켓 발급 | Kafka | 결제 완료 후, 티켓 발급을 비동기적으로 처리하고, 확장 가능한 방식으로 시스템을 운영하기 위해, 티켓 발급 시스템의 안정성을 확보하고 대규모 트래픽에 효율적
티켓 서비스| 대기열 | Kafka | 많은 사용자가 동시에 티켓을 구매하려는 상황에 동시성 문제와 서버 과부하가 발생할 가능성 존재, 실시간으로 요청을 처리하는 대신 대기열에 요청을 저장하여 순차적으로 처리

<details>
  <summary><strong> Distribution Lock</strong></summary>
    <div markdown="1">     
      
   ### 문제 상황
- 스프링은 멀티 쓰레드 방식으로 동작하기 때문에, 동시에 여러 쿠폰 발급 요청이 들어올 경우 하나의 쿠폰 데이터에 대해 여러 쓰레드에서 변경을 요청하게 되고 이 때 `Race Condition` 문제가 발생할 수 있음.
- 쿠폰이 100개라고 했을 때 하나의 쓰레드에서 요청을 완료하기 전에 다른 쓰레드에서 재고 데이터를 조회하는 상황이 있을 수 있고 이 경우 쿠폰이 100개 이상 발급되는 문제가 발생할 수 있다.

    ### 선택 가능한 방안
1. **프로세스 Lock(Synchronized), `채택 X`**
> 프로세스에서 한 데이터를 쓰레드가 사용하면 다른 쓰레드가 사용하지 못하도록 막는 방식
> 
- 채택하지 않은 이유
    대용량 트래픽을 고려한 MSA 아키텍처 서비스에서, 단일 서버로 동작해야만 하는 프로세스 Lock은 `scale-out` 의 상황에 유연하게 대처하지 못하는 방식이기 때문
>
2. **DB Lock `채택 X`**
> 한 트랜잭션이 데이터베이스의 특정 데이터에서 작업을 하고 있다면 다른 트랜잭션이 접근하지 못하도록 막는 방식
> 
- 채택하지 않은 이유
    DB Lock의 경우 해당 자원에 대한 접근 자체를 막기 때문에 쿠폰 발급 뿐만 아니라 조회의 경우에도 Lock이 발생하고 이는 의도하지 않은 `side effect`이기 때문에 채택 불가
>
3. **Distribution Lock `채택 O`**
> 하나의 공유 자원에 대한 경쟁 상황에서 데이터에 접근할 때, 데이터의 결함이 발생하지 않도록 원자성(atomic)을 보장하는 방식
> 
- 채택 이유
    Process Lock의 경우와 달리, redis가 요청 순서대로 lock을 반환해 주기 때문에 `scale-out`의 상황에서도 동시성 문제를 해결할 수 있음
    
    DB Lock과 달리, 자원 자체에 대한 Lock이 아니기 때문에 의도치 않은 `side effect`가 발생하지 않음.
  - 분산락 구현 방식
    - **Zookeeper**
        - 분산 서버 관리시스템으로 분산 서비스 내 설정 등을 공유해주는 시스템.
        - 추가적인 인프라 구성이 필요하고 성능 튜닝을 위한 러닝커브가 존재.
        - Kafka에서 활용중이긴 하지만, 오버엔지니어링이라 판단.
    - **Redis `채택 O`**
        - **Key, Value** 구조의 비정형 데이터를 저장하고 관리하기 위한 NoSQL DB
        - 추가적인 인프라 구성 필요하지만 러닝 커브가 낮음.
        - 현재 이미 공연 조회 데이터 캐싱을 위해 사용 중
        - 인메모리 DB로 속도가 빠름.(초당 100,000 QPS 의 속도)
        - 싱글스레드 방식으로 동시성 문제 해결 가능
  </details> 



      
##  🛠 주요기능
```
👨‍👨‍👧 유저 : 로그인 | 회원가입 |
🎬 공연 : 검색 | 카테고리 | 타임테이블 | 좌석 | redis 활용 빠른 조회 |
💸 결제 : 결제 | 예약 |
🎫 티켓 : Kafka 활용 비동기 생성 | 
🎊 이벤트: 쿠폰발급 | 분산락 활용 쿠폰재고 감소 | 
```
<details>
  <summary><strong>1️⃣ 채팅</strong></summary>
  <img src="https://github.com/user-attachments/assets/30bfe101-6760-490b-9429-316fef17ac99" width=500; width=300 />
  <img src="https://github.com/user-attachments/assets/ee495c15-7c91-4b79-adb9-b50882b695cb" width=500; width=300 />
  <br>

  - [x] 다수와 채팅을 할 수 있도록 기능을 만들었습니다
  - [x] 채팅방에 입장하면 지난 채팅들을 볼 수 있고 사진도 보낼 수 있습니다.
  - [x] 채팅방에 입장한 유저들을 확인할 수 있습니다. 
</details>

<details>
  <summary><strong>2️⃣ 검색 및 정렬</strong></summary>
  <img src="https://github.com/user-attachments/assets/f43cbf21-cb15-4927-8310-f22807c9be57" width=500px; height=300px; />
  <img src="https://github.com/user-attachments/assets/f9556903-e4d1-46fb-bdc8-04b276ceef4c" width=500px; height=300px; />
  <br>

  - [x] 엘라스틱 서치를 활용해 검색 및 정렬기능을 만들었습니다
  - [x] 복잡한 쿼리는 QueryDSL을 이용해 검색 및 정렬기능을 합니다.
</details>

<details>
  <summary><strong>3️⃣ 알림</strong></summary>
  <img src="https://github.com/user-attachments/assets/13702c28-0e9f-42f5-838b-1b5cec3619d5" width=500px; height=300px; />
  <img src="https://github.com/user-attachments/assets/08ce9941-6b44-443c-bb79-7bea9d4a40d7" width=500px; height=300px; />
  <br>
  
  - [x] 1:1문의에서 답변이 온다면 알림이 오도록 구현했습니다.
  - [x] 알림키워드에 등록된 글이 올라온다면 알림이 오도록 구현했습니다.
  - [x] 채팅방에 참여한 유저가 채팅방에서 나간후에 다른 유저가 채팅을 친다면 한번만 알림이 가도록 구현했습니다
  - [x] 소분 거래가 끝나면 채팅방에 있는 유저들에게 후기를 작성할 수 있는 글로 이동할 수 있는 알림이 가도록 구현했습니다
</details>

<details>
  <summary><strong>4️⃣ 결제</strong></summary>
   <img src="https://github.com/user-attachments/assets/feb69896-f7c0-469e-9f78-b8a4fed87e9c" width=500px; height=300px; />
   <img src="https://github.com/user-attachments/assets/0e277f2b-6ac1-4c74-9ee6-cd75934ab24d" width=500px; height=300px; />
   <img src="https://github.com/user-attachments/assets/77873906-390f-4923-99c5-c46277952d81" width=500px; height=300px; />
   <img src="https://github.com/user-attachments/assets/554abbb0-72a8-48fe-a6ca-77eb5965d779" width=500px; height=300px; />
   <img src="https://github.com/user-attachments/assets/bea8862e-bfd4-4efa-b019-7cd1aac35abc" width=500px; height=300px; />
  <br>

  - [x] 포트원을 활용해 테스트 결제를 할 수 있도록 했습니다.
  - [x] 토스, 카카오페이를 이용할 수 있습니다.
  - [x] 결제가 완료되면 주문내역에서 확인할 수 있고 아이템들을 확인할 수 있습니다.
  - [x] 결제가 완료되면 공구진행도도 올라갑니다
</details>

<details>
  <summary><strong>5️⃣ 이메일 발송</strong></summary>
    <img src="https://github.com/user-attachments/assets/1c2a32db-e303-4b35-bc22-a44eea096c9e" width=500px; height=300px; />
    <img src="https://github.com/user-attachments/assets/ab22cfe6-76cf-410d-837c-cf64d8644b2f" width=500px; height=300px; />
    <img src="https://github.com/user-attachments/assets/c206c8fb-9fa6-400a-88bf-1f3a20b0614c" width=500px; height=300px; />
    <br>

  - [x] Java Mail을 활용해 이메일을 보낼 수 있습니다
  - [x] 레디스에 유효기간이 5분인 인증코드를 저장하고 5분이내로 인증이 안되면 인증이 실패되고 회원가입이 안됩니다
  - [x] 아이디찾기, 비밀번호 찾기를 클릭하면 이메일로 해당 정보를 보내고, 임시 비밀번호를 보내게 됩니다
</details>

<details>
  <summary><strong>6️⃣ 배치</strong></summary>
  <img src="https://github.com/user-attachments/assets/839199e0-70a4-4da0-9ce4-a45e3031aaa2" width=500px; height=300px; />
  <img src="https://github.com/user-attachments/assets/cf61e7ee-6af8-48fa-90fa-8000f7bf6776" width=500px; height=300px; />
  <br>
  
  - [x] 레디스로 조회수를 저장하고 일정 시간이 되면 RDB와 동기화합니다
  - [x] 읽은 알림들을 일괄적으로 삭제처리합니다
  - [x] 배달 상태를 일괄적으로 변경합니다
</details>

<details>
  <summary><strong>7️⃣ AI 채팅</strong></summary>
  <img src="https://github.com/user-attachments/assets/3b492a7c-41d1-42c5-9380-90c44618dfd4" width=500px; height=300px; />
  <br>

  - [x] 스프링 AI를 이용해서 Open AI를 통해 인공지능과 채팅을 할 수 있도록 구현하고 학습시켰습니다.
</details>


<details>
  <summary><strong>8️⃣ 알림 키워드</strong></summary>
  <img src="https://github.com/user-attachments/assets/1be3cab9-60e7-4a8e-99ec-b097a7d70dee" width=500px; height=300px; />
  <br>

  - [x] 알림키워드를 등록할 수 있습니다.
  - [x] 유저가 글을 올리면 글의 제목과 주소를 분석하여 관련있는 유저들에게 알림이 갑니다
</details>

<details>
  <summary><strong>9️⃣ 매너포인트</strong></summary>
  <img src="https://github.com/user-attachments/assets/a9d08331-2420-4051-a04a-4bee0d494ba3" width=500px; height=300px; />
  <br>
  
   - [x] 신고를 많이 받은 글의 작성자는 매너포인트가 하락됩니다
   - [x] 매너포인트는 다른 유저가 확인할 수 있습니다.
   - [x] 소분 거래가 완료되면 후기를 작성할 수 있으며, 후기에 일정 개수 이상의 '최고에요' 평가를 받으면 매너포인트가 올라가는 기능을 구현했습니다
</details>

<details>
  <summary><strong>🔟 카카오 맵 API</strong></summary>
  <img src="https://github.com/user-attachments/assets/975d48a4-f703-4235-abd1-7efed1e818dd" width=500px; height=300px; />
  <img src="https://github.com/user-attachments/assets/e5c38c86-4248-461f-b3e4-0a848d2b81ed" width=500px; height=300px; />
  <img src="https://github.com/user-attachments/assets/8dd72788-333c-4210-b82f-2a5fd8198b6d" width=500px; height=300px; />
  <br>

   - [x] 카카오 맵을 활용해 위치 검색 기능을 구현했습니다.
   - [x] 카카오 맵에서 마우스 클릭한 위치에 마커를 표시하고, 해당 위치의 주소를 표시하는 기능을 구현했습니다
   - [x] 사용자가 주소를 검색하여 원하는 주소를 입력할 수 있으며, 해당 주소를 기본 배송지로 지정할 수 있는 기능을 구현했습니다
</details>
<br>

## ⭐ CI-CD
<details>
  <summary><strong> 젠킨스</strong></summary>
    <div markdown="1"> 
      <h3>메인에서 Push 하면 자동으로 젠킨스에서 빌드되고 성공 후, Jar 파일을 배포 서버로 보내고 서버 설정하는 스크립트 파일을 실행시켜서 블루그린 무중단 배포를 시작합니다..</h3>
      <img width="1470" alt="image" src="https://github.com/user-attachments/assets/be4e397d-93e3-4ab6-bd92-9231f0ad261a" />
      <p></p>
      <img width="1470" alt="image" src="https://github.com/user-attachments/assets/4d2c32df-5905-4a0c-a066-d7202c0b2ae1" />
      <p></p>

</details>
<details>
  <summary><strong> NGINX</strong></summary>
    <div markdown="1"> 
        <h3>8080 포트와 8081 포트를 번갈아가며 블루그린 무중단 배포를 시도합니다.</h3>
        <p>nginx가 8080포트 연결되어 있다면 빌드 및 배포 성공 시 새로 8081 포트로 연결하고, 반대로 8081 포트일 때도 마찬가지 입니다.</p>

</details>

</br>

## 🐞 Trouble Shooting
<details>
  <summary><strong> Redis Cache를 적용한 공연 조회</strong></summary>
    <div markdown="1"> 
    - 문제: 자주 조회하는 공연 정보를 DB에서 직접 조회하면 평균 응답 속도가 847ms가 나왔다. <br>
    - 해결: Redis Cache를 활용하여 DB 부하를 줄였다.  <br>
    - 결과: Redis Cache 조회시 847ms보다 약 141배 빠른 6ms가 나왔다. DB 부하를 줄이고 성능을 크게 향상시켰다.
    <img width="1470" alt="image" src="https://github.com/user-attachments/assets/53535619-8f8d-4c0d-8b3b-bdf557594d50" />
    <img width="1470" alt="image" src="https://github.com/user-attachments/assets/fc28bfef-0fd2-47ed-91fd-148675b8b082" />
</details>
<details>
  <summary><strong> 쿠폰 개수의 올바른 차감</strong></summary>
    <div markdown="1"> 
  - 문제: 쿠폰 발급이 이루어질 때, 쿠폰 개수가 올바르지 않게 차감됐다. <br>
  - 해결: Lock을 획득한 요청이 완료될 때까지 다른 요청이 대기하게 하여 트랜잭션 간 stock 조회를 방지 <br>
  - 결과: 쿠폰 발급이 이루어질 때, 1개씩 쿠폰 개수가 차감됐다.
    <img width="1470" alt="image" src="https://github.com/user-attachments/assets/86ff8714-083a-42c3-b844-6194af56d6f9" />
    <img width="1470" alt="image" src="https://github.com/user-attachments/assets/d2c7aa51-c305-477d-a554-6a7d14f8ac33" />
</details>
<details>
  <summary><strong> 분산 Lock을 적용한 쿠폰 발급 - 처리 속도 목표</strong></summary>
    <div markdown="1"> 
- 문제: 기대값인 하나의 요청 당 처리 시간이 1ms를 넘기지 않는 것이지만 5ms가 나왔다. <br>
- 해결: 동시 접근과 경쟁 상태를 방지하기 위해 한 번에 하나의 요청만 자원에 접근할 수 있도록 분산 Lock 적용 <br>
- 결과: 기대값인 1ms보다 약 11배 빠른 0.09ms 처리시간이 나왔다.
    <img width="1470" alt="image" src="https://github.com/user-attachments/assets/ffb5ecae-054b-4b64-a36a-c46721018d29" />
    <img width="1470" alt="image" src="https://github.com/user-attachments/assets/96ce90f8-b63f-4441-befe-e5d8b4fa26df" />
</details>
