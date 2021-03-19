### Exercise 운동

* 조회

    - 방식 : GET 
    - URL : "/exercises"
    - Return :
        - id : Long
        - name : String
        - briefDescription : String
        - fulldescription : String
        - url : String

* 등록

    - 방식 : POST 
    - URL : "/exercises/new"
    - Body : 
        - name : String
        - briefDescription : String
        - fulldescription : String
        - url : String
    - Return :
        - id : Long 
        - date : Date // 처리시각 

* 수정

    - 방식 : PUT 
    - URL : "/exercises/{id}"
    - Body : 
        - name : String
        - briefDescription : String
        - fulldescription : String
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
