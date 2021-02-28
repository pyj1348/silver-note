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
