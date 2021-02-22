## Silver Note Back-end

## Description

이 프로젝트는 **요양원 센터 근로자** / **요양원 어르신** / **보호자**를 타겟으로 한 일종의 커뮤니티 및 학습관리 어플리케이션의 **Java Spring Framework 기반**의 Back-end Server입니다. 지원하는 서비스는 **공지사항** / **식단** / **앨범** / **학습** / **운동** / **쇼핑** 등이 있고 이들은 JSON 형식의 REST API를 통해 제공됩니다.  
앱의 통합관리자와 센터 근로자들은 해당 서비스들을 포함하여 **센터 멤버**와 **센터 정보**를 CRUD 할 수 있고 요양원 어르신과 보호자는 센터에서 작성한 센터 정보, 앨범, 일지 등을 열람할 수 있습니다. 더불어 각 센터는 자신들의 일정에 맞게 데일리 학습을 구성할 수 있고 어르신들을 센터에서 제공하는 일정을 따라 학습과 운동을 수행할 수 있습니다.

## Services

### 1. Member

* 조회

    - 방식 : GET 
    - URL : "/members"

* 등록

    - 방식 : POST 
    - URL : "/members/new"
    - Body : 
        - name : String
        - sex : String
        - rrn : String
        - phone : String
        - city : String
        - street : String
        - zipcode : String
        - centerId : Long
        
* 수정

    - 방식 : PUT 
    - URL : "/members/{id}"
    - Body : 
        - phone : String
        - city : String
        - street : String
        - zipcode : String
        
* 삭제

    - 방식 : DELETE 
    - URL : "/members/{id}"
    
    
* 승인 대기 관리자 조회

    - 방식 : GET 
    - URL : "/members/manager/waiting"

    
* 승인 대기 관리자 처리 요청

    - 방식 : PUT
    - URL : "/members/manager/{id}/approval?type=?"
    - Param : 
        - type : "approve" | "decline" 


### 2. Center

* 조회

    - 방식 : GET 
    - URL : "/centers"

* 등록

    - 방식 : POST 
    - URL : "/centers/new"
    - Body : 
        - name : String
        - phone : String
        - city : String
        - street : String
        - zipcode : String
        - description : String
        
* 수정

    - 방식 : PUT 
    - URL : "/centers/{id}"
    - Body : 
        - phone : String
        - city : String
        - street : String
        - zipcode : String
        - description : String
        
* 삭제

    - 방식 : DELETE 
    - URL : "/centers/{id}"


### 3. Notification

* 조회

    - 방식 : GET 
    - URL : "/notifications"

* 등록

    - 방식 : POST 
    - URL : "/notifications/new"
    - Body : 
        - title : String
        - context : String
        - centerId : Long
        - date : Date

* 수정

    - 방식 : PUT 
    - URL : "/notifications/{id}"
    - Body : 
        - title : String
        - context : String
        
* 삭제

    - 방식 : DELETE 
    - URL : "/notifications/{id}"
    

### 4. Album

* 조회

    - 방식 : GET 
    - URL : "/albums"

* 등록

    - 방식 : POST 
    - URL : "/albums/new"
    - Body : 
        - title : String
        - context : String
        - date : Date
        - memberId : Long
        - centerId : Long

* 수정

    - 방식 : PUT 
    - URL : "/albums/{id}"
    - Body : 
        - title : String
        - context : String
        
* 삭제

    - 방식 : DELETE 
    - URL : "/albums/{id}"
    

### 5. Menu

* 조회

    - 방식 : GET 
    - URL : "/menus"

* 등록

    - 방식 : POST 
    - URL : "/menus/new"
    - Body : 
        - meal : String
        - date : Date
        - centerId : Long

* 수정

    - 방식 : PUT 
    - URL : "/menus/{id}"
    - Body : 
        - meal : String
        
* 삭제

    - 방식 : DELETE 
    - URL : "/menus/{id}"

### 6. Schedule

* 조회

    - 방식 : GET 
    - URL : "/schedules"

* 등록

    - 방식 : POST 
    - URL : "/schedules/new"
    - Body : 
        - context : String
        - date : Date
        - centerId : Long

* 수정

    - 방식 : PUT 
    - URL : "/schedules/{id}"
    - Body : 
        - context : String
        
* 삭제

    - 방식 : DELETE 
    - URL : "/schedules/{id}"

### 7. Item

* 조회

    - 방식 : GET 
    - URL : "/items"

* 등록

    - 방식 : POST 
    - URL : "/items/new"
    - Body : 
        - name : String
        - price : int

* 수정

    - 방식 : PUT 
    - URL : "/items/{id}"
    - Body : 
        - name : String
        - price : int
* 삭제

    - 방식 : DELETE 
    - URL : "/items/{id}"

### 8. Order

* 조회

    - 방식 : GET 
    - URL : "/orders"

* 등록

    - 방식 : POST 
    - URL : "/orders/new"
    - Body : 
        - date : Date
        - quantity : int
        - memberId : Long
        - centerId : Long
        
* 삭제

    - 방식 : DELETE 
    - URL : "/orders/{id}"

### 9. Exercise

* 조회

    - 방식 : GET 
    - URL : "/exercises"

* 등록

    - 방식 : POST 
    - URL : "/exercises/new"
    - Body : 
        - name : String
        - decription : String
        - url : String

* 수정

    - 방식 : PUT 
    - URL : "/exercises/{id}"
    - Body : 
        - name : String
        - decription : String
        - url : String

* 삭제

    - 방식 : DELETE 
    - URL : "/exercises/{id}"

### 10. Member - Exercise

* 조회

    - 방식 : GET 
    - URL : "/member-exercises"

* 등록

    - 방식 : POST 
    - URL : "/member-exercises/new"
    - Body : 
        - progress : int
        - date : Date
        - memberId : Long
        - exerciseId : Long
* 수정

    - 방식 : PUT 
    - URL : "/member-exercises/{id}"
    - Body : 
        - progress : int
* 삭제

    - 방식 : DELETE 
    - URL : "/member-exercises/{id}"

### 11. Learning

* 조회

    - 방식 : GET 
    - URL : "/learnings"

* 등록

    - 방식 : POST 
    - URL : "/learnings/new"
    - Body : 
        - name : String
        - decription : String
        - url : String

* 수정

    - 방식 : PUT 
    - URL : "/learnings/{id}"
    - Body : 
        - name : String
        - decription : String
        - url : String

* 삭제

    - 방식 : DELETE 
    - URL : "/learnings/{id}"

### 12. Center - Learning

* 조회

    - 방식 : GET 
    - URL : "/center-learnings"

* 등록

    - 방식 : POST 
    - URL : "/center-learnings/new"
    - Body : 
        - centerId : Long
        - data : Map<Date, Array of Long> // date : 일자, Array : learningIDs

* 삭제

    - 방식 : DELETE 
    - URL : "/center-learnings/{id}"

### 13. Member - Center - Learning

* 조회

    - 방식 : GET 
    - URL : "/member-center-learnings"

* 등록

    - 방식 : POST 
    - URL : "/member-center-learnings/new"
    - Body : 
        - progress : int
        - date : Date
        - memberId : Long
        - centerLearningId : Long
* 수정

    - 방식 : PUT 
    - URL : "/member-center-learnings/{id}"
    - Body : 
        - progress : int
* 삭제

    - 방식 : DELETE 
    - URL : "/member-center-learnings/{id}"


```python

```
