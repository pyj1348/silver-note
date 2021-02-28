### Learning - Category 학습 카테고리

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
