### User 사용자

* 로그인

    - 방식 : POST 
    - URL : "/users/login"
    - Body : 
        - emailId : String
        - password : String
    - Return :
        - memeberId : Long
        - centerId : Long

* 센터등록여부 조회

    - 방식 : POST
    - URL : "/users/verification"
    - Body :
        - name : String
        - rrn : String
    - Return :
        - date : Date // 처리시각

* 센터회원 유저 등록

    - 방식 : POST 
    - URL : "/users/members/new"
    - Body : 
        - memberId : Long
        - emailId : String
        - password : String
     - Return :
        - id : Long 
        - date : Date // 처리시각
        - 
* 회원가족 유저 등록

    - 방식 : POST 
    - URL : "/users/family/new"
    - Body : 
        - patientId : Long
        - emailId : String
        - password : String
        - name : String
        - rrn : String
        - sex : String
        - address :
            - city : String
            - street : String
            - zipcode : String 
     - Return :
        - id : Long 
        - date : Date // 처리시각
