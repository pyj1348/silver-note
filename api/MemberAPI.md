### Member 센터멤버

* 관리자 조회

    - 방식 : GET 
    - URL : "/members/managers?centerId="
    - Param :
        - centerId : Long
    - Return : 
        - List :
            - id : Long
            - name : String
            - loginId : String
            - email : String
            - phone : String
            - sex : String
            - rrn : String // 주민등록번호
            - address : String
            - zipcode : Stirng
            - status : String // 앱 회원가입 여부;
            - centerId : Long
            - type : String // 멤버 구분

* 근로자 조회

    - 방식 : GET 
    - URL : "/members/employees?centerId="
    - Param :
        - centerId : Long
    - Return : 
        - List :
            - id : Long
            - name : String
            - loginId : String
            - email : String
            - phone : String
            - sex : String
            - rrn : String // 주민등록번호
            - address : String
            - zipcode : Stirng
            - status : String // 앱 회원가입 여부;
            - centerId : Long
            - type : String // 멤버 구분

* 회원 조회

    - 방식 : GET 
    - URL : "/members/patients?centerId="
    - Param :
        - centerId : Long
    - Return : 
        - List :
            - id : Long
            - name : String
            - loginId : String
            - email : String
            - phone : String
            - sex : String
            - rrn : String // 주민등록번호
            - address : String
            - zipcode : Stirng
            - status : String // 앱 회원가입 여부;
            - centerId : Long
            - type : String // 멤버 구분
            - managerId : Long
            - managerName : String
            - grade : int
            
* 가족 조회

    - 방식 : GET 
    - URL : "/members/family?centerId="
    - Param :
        - centerId : Long
    - Return : 
        - List :
            - id : Long
            - name : String
            - loginId : String
            - email : String
            - phone : String
            - sex : String
            - rrn : String // 주민등록번호
            - address : String
            - zipcode : Stirng
            - status : String // 앱 회원가입 여부;
            - centerId : Long
            - type : String // 멤버 구분
            - patientId : Long
            - patientName : String

* 승인 대기 중 관리자 조회

    - 방식 : GET 
    - URL : "/members/managers/waiting"
    - Param :
        - centerId : Long
    - Return : 
        - List :
            - id : Long
            - name : String
            - loginId : String
            - email : String
            - phone : String
            - sex : String
            - rrn : String // 주민등록번호
            - address : String
            - zipcode : Stirng
            - status : String // 앱 회원가입 여부;
            - centerId : Long
            - type : String // 멤버 구분

* 회원 등록

    - 방식 : POST 
    - URL : "/members/new"
    - Body : 
        - name : String
        - email : String
        - sex : String
        - rrn : String // 주민등록번호
        - phone : String
        - address : String
        - zipcode : Stirng
        - centerId : Long
        - type : String // 멤버 구분
     - Return :
        - id : Long 
        - date : Date // 처리시각
        
* 관리자 / 근로자 / 가족 정보 수정

    - 방식 : PUT 
    - URL : "/members/{id}"
    - Body : 
        - phone : String
        - address : String
        - zipcode : Stirng
        - email : String
    - Return :
        - id : Long 
        - date : Date // 처리시각

* 회원 정보 수정

    - 방식 : PUT 
    - URL : "/members/paitents/{id}"
    - Body : 
        - phone : String
        - address : String
        - zipcode : Stirng
        - email : String
        - managerId : Long
        - grade : int
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
    - URL : "/members/manager/join-request/{id}?type=?"
    - Param : 
        - type : String ( must be "approve" | "decline" )
    - Return :
        - id : Long 
        - date : Date // 처리시각


* 로그인 요청

    - 방식 : POST
    - URL : "/members/login"
    - Body :
        - loginId : String
        - password : String 
    - Return :
        - memberId : Long
        - centerId : Long
        - type : String // 멤버 구분

* 회원가입 가능 요청

    - 방식 : POST
    - URL : "/members/login/join-possible"
    - Body :
        - name : String
        - rrn : String 
    - Return :
        - memberId : Long
        - centerId : Long
        - type : String // 멤버 구분

* 등록된 회원 가입신청

    - 방식 : POST
    - URL : "/members/login/existing/new"
    - Body :
        - memberId : Long
        - loginId : String
        - password : String 
    - Return :
        - id : Long 
        - date : Date // 처리시각

* 관리자 가입신청

    - 방식 : POST
    - URL : "/members/login/manager/new"
    - Body :
        - name : String
        - loginId : String
        - password : String
        - email : String
        - phone : String
        - sex : String
        - rrn : String // 주민등록번호
        - address : String
        - zipcode : Stirng
        - centerId : Long
    - Return :
        - id : Long 
        - date : Date // 처리시각

* 가족 가입신청

    - 방식 : POST
    - URL : "/members/login/family/new"
    - Body :
        - name : String
        - loginId : String
        - password : String
        - email : String
        - phone : String
        - sex : String
        - rrn : String // 주민등록번호
        - address : String
        - zipcode : Stirng
        - patientId : Long
        - centerId : Long
    - Return :
        - id : Long 
        - date : Date // 처리시각 
