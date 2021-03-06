## Silver Note Back-end

## Description

이 프로젝트는 **요양원 센터 근로자** / **요양원 어르신** / **보호자**를 타겟으로 한 일종의 커뮤니티 및 학습관리 어플리케이션의 **Java Spring Framework와 JPA 기반**의 Back-end Server입니다. 지원하는 서비스는 **공지사항** / **식단** / **앨범** / **학습** / **운동** / **쇼핑** 등이 있고 이들은 JSON 형식의 데이터를 REST API를 통해 제공됩니다.  
앱의 통합관리자와 센터 근로자들은 해당 서비스들을 포함하여 **센터 멤버**와 **센터 정보**를 CRUD 할 수 있고 요양원 어르신과 보호자는 센터에서 작성한 센터 정보, 앨범, 일지 등을 열람할 수 있습니다. 더불어 각 센터는 자신들의 일정에 맞게 데일리 학습을 구성할 수 있고 어르신들을 센터에서 제공하는 일정을 따라 학습과 운동을 수행할 수 있습니다.

## Services

### 1. Member 센터멤버
[Member API로 이동](./api/MemberAPI.md)

센터에 소속된 멤버를 나타내는 엔티티

Manger, Employee, Patient, Family는 Memeber를 상속한다

Manger, Employee, Patient, Family extends Member


### 2. Center 센터
[Center API로 이동](./api/CenterAPI.md)

요양원 센터를 나타내는 엔티티


### 3. Notification 공지
[Notification API로 이동](./api/NotificationAPI.md)

각 센터에서 게시하는 공지 엔티티


### 4. Album 앨범
[Album API로 이동](./api/AlbumAPI.md)

각 센터에서 게시하는 앨범 엔티티
    

### 5. Menu 식단
[Menu API로 이동](./api/MenuAPI.md)

각 센터에서 게시하는 식단 엔티티


### 6. Schedule 일정
[Schedule API로 이동](./api/ScheduleAPI.md)

각 센터에서 게시하는 일정 엔티티


### 7. Item 상품
[Item API로 이동](./api/ItemAPI.md)

통합관리자가 등록하고 센터관리자가 주문할 수 있는 상품 엔티티


### 8. Order 구매
[Order API로 이동](./api/OrderAPI.md)

센터관리자가 상품을 주문한 내역을 나타내는 엔티티


### 9. Exercise 운동
[Exercise API로 이동](./api/ExerciseAPI.md)

통합관리자가 등록하는 운동 엔티티


### 10. Member - Exercise 운동내역
[MemberExercise API로 이동](./api/MemberExerciseAPI.md)

센터멤버가 운동을 수행한 내역을 나타내는 엔티티


### 11. Learning 학습
[Learning API로 이동](./api/LearningAPI.md)

통합관리자가 등록하는 학습 엔티티


### 12. Learning - Category 학습 카테고리
[LearningCategory API로 이동](./api/LearningCategoryAPI.md)

학습을 분류하는 카테고리 엔티티


### 13. Member - Learning 멤버별 학습일정
[MemberLearning API로 이동](./api/MemberLearningAPI.md)

센터관리자가 멤버별로 등록된 학습을 일정으로 관리하는 엔티티


### 14. Payment 결제
[Payment API로 이동](./api/PaymentAPI.md)

센터의 구독 결제 정보를 저장하는 엔티티



