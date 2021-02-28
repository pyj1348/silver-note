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
