### Member 센터멤버

* 멤버 조회

    - 방식 : GET 
    - URL : "/members"
    - Return : 
        - id : Long
        - name : String
        - phone : String
        - sex : String
        - address :
            - city : String
            - street : String
            - zipcode : Stirng
        - joinStatus : Enum // 앱 회원가입 여부;
        - centerId : Long

* 환자(어르신) 조회

    - 방식 : GET
    - URL : "/members/patients
    - Return :
        - id : Long
        - name : String

* 멤버 등록

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
     - Return :
        - id : Long 
        - date : Date // 처리시각
* 수정

    - 방식 : PUT 
    - URL : "/members/{id}"
    - Body : 
        - phone : String
        - city : String
        - street : String
        - zipcode : String
    - Return :
        - id : Long 
        - date : Date // 처리시각
        
* 삭제

    - 방식 : DELETE 
    - URL : "/members/{id}"
    - Return :
        - id : Long 
        - date : Date // 처리시각
    
    
* 승인 대기 관리자 조회

    - 방식 : GET 
    - URL : "/members/manager/waiting"
    - Return :
        - id : Long
        - name : String

    
* 승인 대기 관리자 처리 요청

    - 방식 : PUT
    - URL : "/members/manager/{id}/approval?type=?"
    - Param : 
        - type : String ( must be "approve" | "decline" )
    - Return :
        - id : Long 
        - date : Date // 처리시각 


### Center 센터

* 조회

    - 방식 : GET 
    - URL : "/centers"
    - Return :
        - id : Long
        - name : String
        - phone : String
        - address :
            - city : String
            - street : String
            - zipcode : Stirng
        - description : String


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
    - Return :
        - id : Long 
        - date : Date // 처리시각 
        
* 삭제

    - 방식 : DELETE 
    - URL : "/centers/{id}"
    - Return :
        - id : Long 
        - date : Date // 처리시각 


### Notification 공지

* 조회

    - 방식 : GET 
    - URL : "/notifications"
    - Return :
        - id : Long
        - title : String
        - context : String
        - date : Date

* 등록

    - 방식 : POST 
    - URL : "/notifications/new"
    - Body : 
        - title : String
        - context : String
        - centerId : Long
        - date : Date
    - Return :
        - id : Long 
        - date : Date // 처리시각 

* 수정

    - 방식 : PUT 
    - URL : "/notifications/{id}"
    - Body : 
        - title : String
        - context : String
    - Return :
        - id : Long 
        - date : Date // 처리시각 
        
* 삭제

    - 방식 : DELETE 
    - URL : "/notifications/{id}"
    - Return :
        - id : Long 
        - date : Date // 처리시각 
    

### Album 앨범

* 조회

    - 방식 : GET 
    - URL : "/albums"
    - Return :
        - id : Long
        - title : String
        - context : String
        - date : Date

* 등록

    - 방식 : POST 
    - URL : "/albums/new"
    - Body : 
        - title : String
        - context : String
        - date : Date
        - memberId : Long
        - centerId : Long
    - Return :
        - id : Long 
        - date : Date // 처리시각 

* 수정

    - 방식 : PUT 
    - URL : "/albums/{id}"
    - Body : 
        - title : String
        - context : String
    - Return :
        - id : Long 
        - date : Date // 처리시각 
        
* 삭제

    - 방식 : DELETE 
    - URL : "/albums/{id}"
    - Return :
        - id : Long 
        - date : Date // 처리시각 
    

### Menu 식단

* 조회

    - 방식 : GET 
    - URL : "/menus"
    - Return :
        - id : Long
        - meal : String // 추후 embedded로 조/중/석 나눠야할듯
        - date : Date
        - centerId : Long

* 등록

    - 방식 : POST 
    - URL : "/menus/new"
    - Body : 
        - meal : String
        - date : Date
        - centerId : Long
    - Return :
        - id : Long 
        - date : Date // 처리시각 

* 수정

    - 방식 : PUT 
    - URL : "/menus/{id}"
    - Body : 
        - meal : String
    - Return :
        - id : Long 
        - date : Date // 처리시각 
* 삭제

    - 방식 : DELETE 
    - URL : "/menus/{id}"
    - Return :
        - id : Long 
        - date : Date // 처리시각 

### Schedule 일정

* 조회

    - 방식 : GET 
    - URL : "/schedules"
    - Return :
        - id : Long
        - context : String
        - date : Date

* 등록

    - 방식 : POST 
    - URL : "/schedules/new"
    - Body : 
        - context : String
        - date : Date
        - centerId : Long
    - Return :
        - id : Long 
        - date : Date // 처리시각 

* 수정

    - 방식 : PUT 
    - URL : "/schedules/{id}"
    - Body : 
        - context : String
    - Return :
        - id : Long 
        - date : Date // 처리시각 
        
* 삭제

    - 방식 : DELETE 
    - URL : "/schedules/{id}"
    - Return :
        - id : Long 
        - date : Date // 처리시각 

### Item 상품

* 조회

    - 방식 : GET 
    - URL : "/items"
    - Return :
        - id : Long
        - name : String
        - price : int
        - date : Date

* 등록

    - 방식 : POST 
    - URL : "/items/new"
    - Body : 
        - name : String
        - price : int
    - Return :
        - id : Long 
        - date : Date // 처리시각 

* 수정

    - 방식 : PUT 
    - URL : "/items/{id}"
    - Body : 
        - name : String
        - price : int
    - Return :
        - id : Long 
        - date : Date // 처리시각 
* 삭제

    - 방식 : DELETE 
    - URL : "/items/{id}"
    - Return :
        - id : Long 
        - date : Date // 처리시각 

### Order 구매

* 조회

    - 방식 : GET 
    - URL : "/orders"
    - Return :
        - id : Long
        - date : Date
        - quantity : int
        - memberId : Long
        - itemId : Long

* 등록

    - 방식 : POST 
    - URL : "/orders/new"
    - Body : 
        - date : Date
        - quantity : int
        - memberId : Long
        - centerId : Long
    - Return :
        - id : Long 
        - date : Date // 처리시각 
        
* 삭제

    - 방식 : DELETE 
    - URL : "/orders/{id}"
    - Return :
        - id : Long 
        - date : Date // 처리시각 

### Exercise 운동

* 조회

    - 방식 : GET 
    - URL : "/exercises"
    - Return :
        - id : Long
        - name : String
        - description : String
        - url : String

* 등록

    - 방식 : POST 
    - URL : "/exercises/new"
    - Body : 
        - name : String
        - decription : String
        - url : String
    - Return :
        - id : Long 
        - date : Date // 처리시각 

* 수정

    - 방식 : PUT 
    - URL : "/exercises/{id}"
    - Body : 
        - name : String
        - decription : String
        - url : String
    - Return :
        - id : Long 
        - date : Date // 처리시각 

* 삭제

    - 방식 : DELETE 
    - URL : "/exercises/{id}"
    - Return :
        - id : Long 
        - date : Date // 처리시각 

### Member - Exercise 운동내역

* 조회

    - 방식 : GET 
    - URL : "/member-exercises"
    - Return :
        - id : Long
        - date : Date
        - memberId : Long
        - exerciseId : Long

* 등록

    - 방식 : POST 
    - URL : "/member-exercises/new"
    - Body : 
        - progress : int
        - date : Date
        - memberId : Long
        - exerciseId : Long
    - Return :
        - id : Long 
        - date : Date // 처리시각 
* 수정

    - 방식 : PUT 
    - URL : "/member-exercises/{id}"
    - Body : 
        - progress : int
    - Return :
        - id : Long 
        - date : Date // 처리시각 
* 삭제

    - 방식 : DELETE 
    - URL : "/member-exercises/{id}"
    - Return :
        - id : Long 
        - date : Date // 처리시각 

### Learning 학습

* 조회

    - 방식 : GET 
    - URL : "/learnings"
    - Return :
        - id : Long
        - name : String
        - description : String
        - url : String

* 등록

    - 방식 : POST 
    - URL : "/learnings/new"
    - Body : 
        - name : String
        - decription : String
        - url : String
    - Return :
        - id : Long 
        - date : Date // 처리시각 

* 수정

    - 방식 : PUT 
    - URL : "/learnings/{id}"
    - Body : 
        - name : String
        - decription : String
        - url : String
    - Return :
        - id : Long 
        - date : Date // 처리시각 

* 삭제

    - 방식 : DELETE 
    - URL : "/learnings/{id}"
    - Return :
        - id : Long 
        - date : Date // 처리시각 

### Learning-Schedule 센터별 학습일정

* 조회

    - 방식 : GET 
    - URL : "/learning-schedules?centerId=?&start=?&end=?"
    - Param : 
        - centerId : Long
        - start : Date (yyyy-mm-dd)
        - end : Date (yyyy-mm-dd)
    - Return :
        - List :
            - date : Date
            - learnings : List
                - learnigId : Long   
        
* 등록

    - 방식 : POST 
    - URL : "/learning-schedules/new"
    - Body : 
        - centerId : Long
        - date : Date
    - Return :
        - id : Long 
        - date : Date // 처리시각 

* 삭제

    - 방식 : DELETE 
    - URL : "/learning-schedules/{id}"
    - Return :
        - id : Long 
        - date : Date // 처리시각

### Daily - Learning 일자별 학습목록

* 조회

    - 방식 : GET 
    - URL : "/daily-learnings"
    - Return :
        - id : Long
        - scheduleId : Long
        - learning : Long 

* 등록

    - 방식 : POST 
    - URL : "/daily-learnings/new"
    - Body : 
        - scheduleId : Long
        - learning : Long 
    - Return :
        - id : Long 
        - date : Date // 처리시각 
        - 
* 삭제

    - 방식 : DELETE 
    - URL : "/daily-learnings/{id}"
    - Return :
        - id : Long 
        - date : Date // 처리시각 

### Member - Learning 학습 수강내역

* 조회

    - 방식 : GET 
    - URL : "/member-learnings"
    - Rutrn :
        - id : Long
        - memberId : Long
        - dailyLearning : Long

* 등록

    - 방식 : POST 
    - URL : "/member-learnings/new"
    - Body : 
        - progress : int
        - memberId : Long
        - dailyLearning : Long
    - Return :
        - id : Long 
        - date : Date // 처리시각 

* 삭제

    - 방식 : DELETE 
    - URL : "/member-learnings/{id}"
    - Return :
        - id : Long 
        - date : Date // 처리시각 

### Learning-Category 학습 카테고리

* 조회

    - 방식 : GET 
    - URL : "/learning-categories"
    - Return :
        - List
            - parent :
                - id : Long
                - name : String
            - children : List
                - id : Long
                - name : string
* 등록

    - 방식 : POST 
    - URL : "/learning-categories/new"
    - Body : 
        - name : String
        - parentId : Long // if 최상위 -> 0 , else -> parentId
    - Return :
        - id : Long 
        - date : Date // 처리시각 
* 수정

    - 방식 : PUT 
    - URL : "/learning-categories/{id}"
    - Body : 
        - name : String
    - Return :
        - id : Long 
        - date : Date // 처리시각 
* 삭제

    - 방식 : DELETE 
    - URL : "/learning-categories/{id}"
    - Return :
        - id : Long 
        - date : Date // 처리시각 

