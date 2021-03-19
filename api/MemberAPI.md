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
        - managerId : Long
        - managerName : String
        - grade : int

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
