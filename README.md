# 🎫Culture Ticket
<img src="https://github.com/user-attachments/assets/99280765-13cd-463c-b60e-f60ec33a9d32" width=600; width=500 />
<br>

## ❇️ [프로젝트 개요](https://github.com/Sparta-Triple/CultureTicket/wiki)
#### 대규모 트래픽을 효과적으로 처리하여 안정적이고 확장 가능한 '문화생활 티케팅 서비스'
- [개발과정](https://github.com/Sparta-Triple/CultureTicket/wiki/%EA%B0%9C%EB%B0%9C%EA%B3%BC%EC%A0%95)
- [ERD](https://github.com/user-attachments/assets/3ed7a77c-ba7e-4ec0-99f8-1a4837895466)
- [Convention](https://github.com/Sparta-Triple/CultureTicket/wiki/Convention)
- [그라운드 룰](https://github.com/Sparta-Triple/CultureTicket/wiki/GroundRule)

</br>

## 👨‍👩‍👧‍👦 팀원 소개
| <div align="center">[홍예석](https://github.com/yshong1998)</div>                                                                    | <div align="center">[김우진](https://github.com/kwj0605)</div>                                                                       | <div align="center">[성은정](github.com/SEJ123)</div>                      | 
|:----------------------------------------------------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------------------| :---------------------------------------------------------------------------- | 
| <div align="center"><img src="https://github.com/user-attachments/assets/e4bb1ea5-ac3a-464d-aacc-c28159a61b31" width=150 /></div> | <div align="center"><img src="https://github.com/user-attachments/assets/2550cf81-f2db-4f4d-9243-9a91c66ca8f7" width=150 /></div> | <div align="center"><img src="https://github.com/user-attachments/assets/c7f702fe-95ff-4a4e-8f6a-2b751f63a12d" width=150 /></div> 
| <div align="center"> `공통 기능` <br>사용자 인증 / 인가<br/>`담당 도메인`<br> 유저, 공연, <br> 타임테이블, <br> 좌석, 쿠폰 <br></div>                          | <div align="center"> `분류` <br> 담당 역할 작성 </div>                                                                                    | <div align="center"> `분류` <br> 담당 역할 작성</div>                     | 

  
## ✨프로젝트 핵심 목표
대규모 트래픽을 효과적으로 처리하여 안정적이고 확장 가능한 '문화생활 티켓팅 서비스'를 구축하여 사용자들에게 원활한 티켓 구매 경험을 제공

- Kafka를 활용한 대규모 트래픽 상황에 알맞은 메시징 시스템
  - SAGA패턴을 사용한 자동 롤백과 데이터 일관성 유지
- Grafana, Prometheus를 통한 모니터링
  - 병목 구간 파악 및 성능 개선
- Mock MVC를 이용한 테스트 코드
- Database Replication 구성에 따른 Read / Write 분리
    - Redis로 우선 처리, DB에 백업 저장
- Cache를 사용한 DB 복제 지연 문제 해결





## ✔️KEY SUMMARY
<details>
  <summary><strong> 1️⃣ Redis Cache를 적용한 공연 조회</strong></summary>
    <div markdown="1"> 

#### 문제점

티켓팅 서비스는 사용자들이 공연 정보를 자주 조회하기 때문에 DB는 계속해서 쿼리를 처리해야 하기 때문에 성능 저하 및 과부하가 올 수 있다.

DB 조회 시, 평균 응답 속도가 847ms로 나타났다.

  <img src="https://github.com/user-attachments/assets/a0b7d577-e099-45ea-ac59-b6bba6f7ef18"/>

#### 해결 방법

Redis Cache를 사용하면, DB 부하를 줄이고 성능을 크게 향상 시킬 수 있다.<br>
-> 캐시 시스템은 빠른 응답 속도와 트래픽 처리 효율성을 제공하며, 대규모 트래픽을 처리하는 데 효율적

Redis Cache 조회 시, 평균 응답 속도가 6ms로 나타났다.

  <img src="https://github.com/user-attachments/assets/5f10fbcc-5584-4fee-9947-6523badf393d"/>
  <br>

#### 정리
> Redis 조회 시 평균 6ms 응답 속도가 나왔다.
> - 빠른 응답 속도 제공 <br>
    DB 조회 시 평균 847ms에서 Redis를 사용한 후 6ms로 응답 속도가 약 141배 빨라졌다. 이로 인해 사용자들이 공연 정보를 더 빠르게 조회할 수 있게 되었다.
> - 서버 부하 감소
    동일한 공연 정보를 조회할 때 DB 대신 Redis Cache에 저장된 데이터를 사용함으로써 DB의 부하를 줄이고, 서버 리소스를 효율적으로 관리할 수 있게 되었다.
> - 사용자 경험 향상
    Redis Cache를 통해 빠르고 일관된 응답을 제공함으로써, 사용자의 만족도를 향상 시켰다.

</details>
<details>
  <summary><strong> 2️⃣ 리팩톤 데이즈 요구 사항 - 대용량 트래픽 대비 안정적 선착순 쿠폰 발급</strong></summary>
    <div markdown="1">

[대용량 트래픽 대비 안정적 선착순 쿠폰 발급 구현 과정](https://www.notion.so/teamsparta/15-d47f3d1366424423af85da024c2aa8cd)
1. 쿠폰 개수의 올바른 차감
- 문제: 쿠폰 발급이 이루어질 때, 쿠폰이 중복으로 발급되어 개수가 적절하게 발급되지 않는 문제가 있었다. <br>
- 해결: Lock을 획득한 요청이 완료될 때까지 다른 요청이 대기하게 하여 트랜잭션 간 stock 조회를 방지 <br>
- 결과: 쿠폰 발급이 이루어질 때, 중복으로 쿠폰이 발급되는 문제가 해결되었다.
- 분산락 적용 이전
  <img width="1470" alt="image" src="https://github.com/user-attachments/assets/86ff8714-083a-42c3-b844-6194af56d6f9" />
- 분산락 적용 이후
  <img width="1470" alt="image" src="https://github.com/user-attachments/assets/d2c7aa51-c305-477d-a554-6a7d14f8ac33" />
2. 분산 Lock을 적용한 쿠폰 발급 - 처리 속도 목표

- 문제: 기대값인 하나의 요청 당 처리 시간이 1ms를 넘기지 않는 것이지만 5ms가 나왔다. <br>
- 해결: 동시 접근과 경쟁 상태를 방지하기 위해 한 번에 하나의 요청만 자원에 접근할 수 있도록 분산 Lock 적용 <br>
- 결과: 기대값인 1ms보다 약 11배 빠른 0.09ms 처리시간이 나왔다.
    - 분산락 적용 이전
      <img width="1470" alt="image" src="https://github.com/user-attachments/assets/ffb5ecae-054b-4b64-a36a-c46721018d29" />
    - 분산락 적용 이후
      <img width="1470" alt="image" src="https://github.com/user-attachments/assets/96ce90f8-b63f-4441-befe-e5d8b4fa26df" />
</details>
<details>
  <summary><strong>3️⃣ Redis Caching 공연 목록 조회 오류-직렬화 </strong></summary>

[공연 목록 캐시 데이터 조회 시 직렬화 오류](https://www.notion.so/teamsparta/Redis-Caching-9fba97f10cdd4296bfc621250c110d14)

</details>


## 📁인프라 아키텍처
<img src="https://github.com/user-attachments/assets/8eb580bb-fd78-41a6-9f34-daaad359a21e" width=600; width=500 />




## 🚀 기술 스택

OS | Stack
--- | --- |
Language | ![Java](https://img.shields.io/badge/java-007396?style=for-the-badge&logo=JAVA&logoColor=white) ![Spring](https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
IDE | ![intellij-idea](https://img.shields.io/badge/intellij%20idea-000000?style=for-the-badge&logo=intellijidea&logoColor=white) 
Framework | ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
Build Tool | ![gradle](https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)
Database | ![PostgreSql](https://img.shields.io/badge/postgresql-4479A1?style=for-the-badge&logo=postgresql&logoColor=white) ![Redis](https://img.shields.io/badge/redis-FF4438?style=for-the-badge&logo=redis&logoColor=white)
Library | ![Spring Security](https://img.shields.io/badge/spring%20security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white) ![Spring Cloud](https://img.shields.io/badge/spring%20cloud-6DB33F?style=for-the-badge&logo=springCloud&logoColor=white) ![JUnit5](https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=junit5&logoColor=white) ![JPA](https://img.shields.io/badge/JPA-6DB33F?style=for-the-badge&logo=jpa&logoColor=white) ![Query dsl](https://img.shields.io/badge/query%20dsl-007396?style=for-the-badge&logo=querydsl&logoColor=white) ![apache kafka](https://img.shields.io/badge/apache%20kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white) ![prometheus](https://img.shields.io/badge/prometheus-red?style=for-the-badge&logo=prometheus&logoColor=white) ![grafana](https://img.shields.io/badge/grafana-orange?style=for-the-badge&logo=grafana&logoColor=white)  
DevOps | ![Amazon Web Services](https://img.shields.io/badge/amazon%20aws-232F3E?style=for-the-badge&logo=Amazon%20Web%20Services&logoColor=white) ![amazon rds](https://img.shields.io/badge/amazon%20rds-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white) ![amazon ec2](https://img.shields.io/badge/amazon%20ec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white) ![amazon elastic Cache](https://img.shields.io/badge/elastic%20cache-blue?style=for-the-badge&logo=amazonelasticache&logoColor=white) ![Docker](https://img.shields.io/badge/docker-4285F4?style=for-the-badge&logo=docker&logoColor=white) ![gabia](https://img.shields.io/badge/gabia-skyblue?style=for-the-badge&logo=gabia&logoColor=white) 
Tools | ![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white) ![git](https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white) ![slack](https://img.shields.io/badge/slack-4A154B?style=for-the-badge&logo=slack&logoColor=white) ![notion](https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white) ![figma](https://img.shields.io/badge/figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white) 


## 🗣️ 기술적 의사결정

<details>
  <summary><strong> 대용량 트래픽에서 동시성 문제를 고려한 선착순 쿠폰 발급 처리 </strong></summary>
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
  </div>
</details> 
<details>
  <summary><strong> 대기열 기능 구현을 위한 저장소 및 구현 방식 선택</strong></summary>
<div markdown="1">

### 도입 이유

- 서비스에서 비중이 가장 큰 공연 조회, 그중에서도 특정 공연(ex. 아이돌 공연)조회에서 트래픽이 몰리면서 서비스의 과부화를 방지하기 위해 대기열 기능을 도입

### 선택 가능한 방안

1. **Kafka  `채택 X`**

---

> Kafka의 메시지 방식을 사용하여 순서대로 대기열 관리
> 
- 채택하지 않은 이유
    
    Kakfa를 사용하면 Topic에 데이터를 담아 나오는 순서대로 대기열을 구현하는데, 대기열의 정보(대기열 번호, 남은 시간) 등을 구현하기 어렵기 때문에 채택 불가
    

1. **Redis `채택 O`**

---

> Redis의 Sorted Set 자료형을 사용하여 순서대로 대기열 관리
> 
- 채택 이유
    
    
    Redis의 Sorted Set 자료형을 이용하여 대기열의 정보(대기열 번호, 남은 시간) 등을 구현하기 쉬기 때문에 채택
    

### 대기열을 구현하는 방식

1. **은행창구 방식 `채택 X`**

---

> 한 명이 처리열을 빠져아가면 대기열에서 한 명이 들어오는 방식
> 
- 채택하지 않은 이유
    
    한 명씩 대기열을 빠져나오는 방식으로 현재 처리 중인 인원 수를 정확히 카운팅하는 것이 중요한 로직에 사용되는 것이 적절
    
    따라서, 공연 조회에 필요한 방식은 아님
    

1. **놀이 동산 방식`채택 O`**

---

> 일정 시간 동안 N명을 들여보내고, M 시간이 지나면 자동으로 빠져나가는 방식
> 
- 채택 이유
    
    N명의 사람들이 대기열에서 일정한 시간 동안 주기적으로 빠져나가는 방식이 공연 조회 시 과도한 트래픽이 몰려 서비스에 과부하가 생길 수 있는 상황을 막기 위한 방식에 적절
</div>
</details>
<details>
  <summary><strong> 데이터 일관성을 위한 SAGA 패턴 </strong></summary>
<div markdown="1">

### 문제 상황

- MSA 환경에서 여러 서비스들이 메시징 시스템으로 비즈니스 프로세스를 구성한다. 이때 각 서비스에서 트랜잭션을 수행하는 과정에서 실패할 경우 이를 보상하기 위한 보상 트랜잭션을 정의해야한다.

### 선택 가능한 방안

1. **2Phase Commit 패턴(2PC 패턴)`채택 X`**

---

> 원자적 커밋 프로토콜(ACP)의 일종.
트랜잭션을 커밋할지, 아니면 롤백할지에 대해 분산 원자적 트랜잭션에 관여하는 분산 알고리즘의 하나

- 채택하지 않은 이유
    
    모든 요청을 처리할 때까지 관련한 모든 DB에 Lock이 설정됨(지연 시간 증가)
    
    서비스 간 강결합 초래(MSA 구조를 도입하는 이유인 서비스간의 느슨한 결합의 의미가 퇴색될 가능성)
    

1. **SAGA패턴`채택 O`**

---

> 관련 서비스들의 트랜잭션을 순차적으로 처리
> 
- 채택 이유
    
    모든 트랜잭션 관리 : 서비스 간의 이벤트를 통해 로컬 트랜잭션을 순차적으로 처리
    
    원자성 보장 : 트랜잭션 상태를 체크하여 처리되지 않으면 전체 트랜잭션을 롤백 ‘보상 트랜잭션’의 개념을 통해 처리
    

- Saga 패턴 종류
    1. Choreographed Saga **`채택 O`**
    
    ---
    
    > 이벤트 및 보상 트랜잭션 처리 주체가 각 마이크로 서비스인 Saga 패턴

    - 채택 이유
        
        마이크로 서비스가 적을 경우 쉽고 간단하게 구성이 가능
        
        기존 MSA 환경에서 추가적인 인프라 리소스가 필요하지 않음
        
    1. Orchestrated Saga
    
    > 이벤트 및 보상 트랜잭션 처리의 주체로 'Orchestrator'가 존재하여 중앙에서 처리

    - 채택하지 않은 이유
        
        중앙 관리 시스템인 Orchestrator 구현을 위해 추가적인 인프라 리소스가 필요
        
        Orchestrator가 전체 Flow를 관리하기 때문에 단일 장애 지점(SPOF)이 되어 장애 발생 시 모든 서비스에 장애가 전파될 수 있음
        Orchestrator 구현이 상대적으로 어려움

    > 이벤트 및 보상 트랜잭션 처리 주체가 각 마이크로 서비스인 Saga 패턴

    - 채택 이유
        
        마이크로 서비스가 적을 경우 쉽고 간단하게 구성이 가능
        
        기존 MSA 환경에서 추가적인 인프라 리소스가 필요하지 않음
</div>
</details>
<details>
  <summary><strong> 비동기 처리를 위한 메세징 시스템 선택 </strong></summary>
<div markdown="1">
### 문제 상황

- 결제 - 예약 - 티켓 발급의 과정에서 사용자 경험을 개선하기 위해, 비동기 처리 방식 도입을 결정
- 결제, 예약의 경우 즉시 데이터의 변경이 발생해야 중복 예약을 방지할 수 있기 때문에 즉시 처리
- 티켓 발급의 경우 처리에 즉각성을 요구하는 부분이 아니기 때문에 비동기로 처리하도록 결정

### 선택 가능한 방안

**RabbitMQ `채택 x`**

> 메시지 전달과 라우팅을 중점으로 한 멀티 프로토콜 메시지 브로커.

- 장점
    - **유연한 메시지 라우팅**: exchange를 사용하여 복잡한 라우팅 패턴 지원
    - **경량화**: 단일 노드 및 간단한 사용 사례에 적합
    - **낮은 지연 시간**: 소규모 메시징 시스템에서 매우 빠름
- 단점
    - **내구성**: 디스크 기반 저장이 가능하지만, Kafka에 비해 데이터 처리량에서 성능 저하 가능.
    - **대용량 처리**: 대규모 스트리밍 데이터 처리에는 부적합.
- 채택하지 않은 이유
    - 대용량 데이터 처리를 요하는 부분은 아니기 때문에, 당장의 작은 규모의 서비스에서는 RabbitMQ가 Kafka에 비해 티켓 발급과 같이 가볍고 간단한 사례에 더 적합한 것이 사실.
    - 하지만 서비스가 MSA 아키텍처로 구성되어 있고, 특히 MSA 아키텍처는 개발과 유지보수의 어려움 대신 보다 안정적인 서비스 환경과 확장성을 강점으로 하는 아키텍처인 데 반해 RabbitMQ는 Kafka에 비해 확장성의 측면에서 불리

**Kafka `채택 o`**

> 대규모 데이터 스트리밍과 분산 메시징을 위해 설계된 분산 로그 시스템

- 장점
    - **높은 처리량**: 대규모 데이터 스트리밍과 실시간 처리에 최적화
    - **파티셔닝**: 데이터를 파티션으로 나누어 분산 처리 지원
    - **확장성**: 클러스터를 통해 손쉽게 확장 가능.
    - **리텐션**: 메시지가 소비되더라도 설정된 기간 동안 저장 가능
- 단점
    - **설치 및 관리의 복잡성**: 분산 시스템 구성 및 관리가 어려움.
    - **실시간 처리 지연**: 메시지가 파티션을 거칠 때 약간의 지연 발생.
    - **기능 제한**: 메시지 라우팅이나 요청/응답 패턴 등은 RabbitMQ보다 제한적.
- 채택 이유
    - RabbitMQ에서의 내용과 동일하게, 각 서비스가 단일로만 동작하는 상황에서는 RabbitMQ가 더 적합할 수 있지만 `scale-out`의 규모가 커지면 커질수록 클러스터링을 통해 손쉽게 확장이 가능한 Kafka가 MSA 아키텍처에 더 적합하다고 판단.

</div>
</details>
<details>
  <summary><strong> 모니터링과 메트릭 수집 툴 결정 </strong></summary>
<div markdown="1">

### 문제 상황

- 서비스의 실시간 성능 모니터링을 위한 툴 선택

### 선택 가능한 방안

1. **Prometheus `채택 O`**

---

> 메트릭 기반의 모니터링 시스템, 시스템의 성능을 시계열 데이터로 수집, 저장
> 
- 채택 이유
    
    시계열 데이터 혹은 리소스 사용량 모니터링에 적합하다.
    
    결제 요청 처리 시간, 실패율, 시스템의 성능을 실시간으로 모니터링하고 경고를 설정할 수 있다. 즉, 실시간 성능 모니터링에 유리하다.
    
1. **Grafana`채택 O`**

---

> Prometheus와 통합되어 시계열 데이터를 시각화하는 데 강력한 대시보드 제공
> 
- 채택 이유
    
    서비스의 성능, 처리 시간, 성공/실패 요청 비율 등 실시간으로 시각화해서 추적할 수 있다.
    
    시스템의 상태를 직관적으로 파악할 수 있다. 즉, 실시간 성능 모니터링에 유리하다.
    

1. **ELK `채택 X`**

---

> 로그 분석에 특화, 문제 발생 시점의 세부 로그를 추적하는데 유리
> 
- 채택하지 않은 이유
    
    로그 분석을 주로 처리하므로 실시간 메트릭 수집과 시계열 모니터링의 효율성에는 적절하지 않다.

    또한 ELK는 로그 수집, 모니터링 이외에 검색 엔진으로서 함께 활용될 때 적합하지만, 이처럼 모니터링만을 위해 도입하는 것은 오버엔지니어링이라 판단.
    
</div>
</details>


      
##  🛠 주요기능
```
👨‍👨‍👧 유저 : 로그인 | 회원가입 |
🎬 공연 : 검색 | 카테고리 | 타임테이블 | 좌석 | redis 활용 빠른 조회 |
💸 결제 : 결제 | 예약 |
🎫 티켓 : Kafka 활용 비동기 생성 | 
🎊 이벤트: 쿠폰발급 | 분산락 활용 쿠폰재고 감소 | 
```

- 주요 기능 구현 방식

<details>
  <summary><strong>1️⃣ 공연 </strong></summary>

- 공연 조회 시 대기열
- [x] 공연 조회 시 sessionId를 기반으로 대기열 조회
- [x] Feign Client 방식으로 대기열의 정보 조회


- 실시간 랭킹
- [x] 공연마다 조회수를 Redis의 Sorted Set 데이터 타입으로 저장
- [x] 랭킹 조회 시 상위 조회수를 기록한 5개의 공연을 반환
</details>

<details>
  <summary><strong>2️⃣ 선착순 쿠폰 발급 </strong></summary>

- 쿠폰 발급 요청 동시성 제어
- [x] AOP 단계에서 분산락을 활용해 요청 간 동시성 문제 해결

</details>
<details>
  <summary><strong>3️⃣ 대기열 </strong></summary>

- Redis Sorted Set 기반 대기열 구현
- [x] 놀이동산 방식의 대기열을 구현
- [x] 처음 1000명 이후부터 대기열을 기다림
- [x] 매 10초마다 3000명씩 대기열에서 빠져나옴
- [x] 10분이 지나면 다시 대기열을 기다림
- [x] 분산락을 사용하여 지정된 사용자보다 더 많은 사용자가 대기열에서 빠져나오는 것을 방지

</details>
<details>
  <summary><strong>4️⃣ 모니터링 </strong></summary>

[모니터링 결과 보러 가기](https://www.notion.so/teamsparta/89343f86b17f469bb94c05df29ed1eb9)

</details>
<details>
  <summary><strong>5️⃣ CI / CD </strong></summary>
추가 예정 (우진)

</details>
<details>
  <summary><strong>6️⃣ 요약 </strong></summary>

| 서비스 | 요구 사항 | 기술명 | 구현 내용 |
| --- | --- | --- | --- |
| 공연 서비스 | 공연 조회 대기열 | Redis, Feign Client | Redis를 활용하여 sessionId, Token을 기반으로 사용자 식별, 대기열 기능 구현 |
| 공연 서비스 | 공연 주간 랭킹 | Redis Sorted Set | Redis를 활용해 공연 랭킹과 같은 실시간으로 자주 업데이트, 조회 되는 데이터를 빠르게 처리, Sorted Set 구조를 활용해 정렬 후 Top5 공연 반환 |
| 공연 서비스 | 공연 데이터 캐시 | Redis Cache | 공연 정보와 같은 자주 조회되는 데이터는 매번 DB에서 직접 조회하는 것보다 Redis 캐시 시스템을 활용하여 응답 속도를 개선 |
| 공연 서비스 | 공연 조회 대기열 | Redis Sorted Set | 많은 사용자가 동시에 하나의 공연에 대해 조회하는 상황에 서버 과부하가 발생할 가능성 존재, 놀이동산 방식의 대기열을 구현하여 서버가 감당할 수 있는 사용자를 주기적으로 처리 |
| 쿠폰 서비스 | 선착순 쿠폰 발급 | Redis, AOP, Distributed Lock | Redis 를 활용해 분산락을 AOP 단계에서 적용.동시성 문제, 중복 발급 방지를 보장하고, 시스템의 안정성과 데이터 일관성을 유지 |
| 티켓 서비스 | 티켓 발급 | Kafka | 결제 완료 후, 티켓 발급을 비동기적으로 처리하고, 확장 가능한 방식으로 시스템을 운영하기 위해, 티켓 발급 시스템의 안정성을 확보하고 대규모 트래픽에 효율적 |
| 티켓 서비스 | 대기열 | Kafka | 많은 사용자가 동시에 티켓을 구매하려는 상황에 동시성 문제와 서버 과부하가 발생할 가능성 존재, 실시간으로 요청을 처리하는 대신 대기열에 요청을 저장하여 순차적으로 처리 |
| CI / CD | 추가 예정 | 추가 예정 | 추가 예정 |

</details>

## ⭐ CI-CD

추가 예정