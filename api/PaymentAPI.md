### Payment 결제

* 조회

    - 방식 : GET 
    - URL : "/payments"
    - Return :
        - id : Long
        - price : Long
        - paymentDate : Date (YYYY-MM-DD)
        - expiredDate : Date (YYYY-MM-DD) 
        - centerId : Long
        - centerName : String
        - memberId : Long
        - memberName : String

* 등록

    - 방식 : POST 
    - URL : "/payments/new"
    - Body : 
        - paymentDate : Date (YYYY-MM-DD) 
        - itemId : Long
        - memberId : Long
        - centerId : Long
    - Return :
        - id : Long 
        - date : Date // 처리시각 
