### Center 센터

* 조회

    - 방식 : GET 
    - URL : "/centers"
    - Return :
        - id : Long
        - name : String
        - phone : String
        - address : String
        - zipcode : Stirng
        - description : String


* 등록

    - 방식 : POST 
    - URL : "/centers/new"
    - Body : 
        - name : String
        - phone : String
        - address : String
        - zipcode : Stirng
        - description : String
        
* 수정

    - 방식 : PUT 
    - URL : "/centers/{id}"
    - Body : 
        - phone : String
        - address : String
        - zipcode : Stirng
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
