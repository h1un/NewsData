# News Data

목표

- Springboot를 활용하여뉴스데이터수집서비스개발

진행 방향

- 데이터모델링
- 어플리케이션아키텍처구성
- API (로그인및기본CRUD)및프론트페이지개발
- 테스트케이스작성

개발 참고

- RESTful API 설계
- Embeded Database 구성
- freemarker, jstl, thymeleaf, vuejs, bootstrap 등을활용하여프론트구성

특정 키워드가 들어간 뉴스를 수집하여 보여주는 사이트를 만들려고 합니다.

이 사이트는 아래의 최소 기능을 포함하며, 수집된 데이터가 최신을 늘 유지하고 관리돼야 합니다.

최초 등록된 키워드는 1번에 한해 바로 수집 처리 되며, 이후에는 지정된 주기별로 수집을 실행하여, 해당 데이터를 볼 수 있습니다

1. User 가입
2. User 로그인
3. 로그인 후 수집 결과 view 페이지로 이동
4. 설정 페이지로 이동
5. 수집 키워드 등록(선거, 코로나 .. 등 복수 지정)
   6.수집 대상 뉴스 사이트 설정(네이버, 다음 .. 등 복수 지정)
7. 수집 결과 view 페이지에서 데이터 확인

---

화면 
1. [메인] 로그인 페이지
2. 가입 페이지
3. view 페이지 - 뉴스 수집 화면
4. 설정 페이지 - 수집 키워드 등록

---

