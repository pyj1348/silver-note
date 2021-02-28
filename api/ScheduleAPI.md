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
