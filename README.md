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

- [x] 회원가입 화면
    - [x] 아이디 체크
    - [x] 비밀번호 체크
- [x] 회원가입 (User Save)
- [x] 로그인 화면
- [x] 로그인
- [x] 뉴스 화면
- [x] 뉴스 화면 view 에 키워드 뿌리기
- [x] 키워드 등록 화면 -뉴스화면에다가 같이할까 ?
- [x] 키워드 등록 (Keyword Save)
    - [x] 키워드 체크 - 같은거 체크 안되게
    - [x] 키워드 삭제
- [x] 키워드 선택시 그 키워드 뉴스만 보이게
- [x] 키워드 등록시 뉴스 수집 (News Save)
    -  [x] 한번 수집
        - [x] 날짜 맞추기
- [x] 뉴스 화면 view 에 뉴스 수집한거 뿌리기
    - [x] jpa 페이징 처리
- [x] 키워드 선택시 그 키워드만
- [x] 1시간마다 뉴스 수집
    - [x] 같은 뉴스 수집안되게
    - [x] 여러번 수집 [첫 수집은 100개, 한시간 간격은 1시간동안 올라온것]

## 프론트 css 수정해야할것
- [ ] 회원가입 표시
- [ ] 페이징 수정
- [ ] 뉴스 화면 선택한거 표시 

## ++

- [ ] 아이디 별로 키워드 가지게 해야겠다
    - 그러면 키워드 삭제해도 그 키워드는 계속 수집되는건가 ? -다음은 뉴스 API를 제공하지 않는데, 크롤링을 해야하나 ? -네이버 뉴스와 다음 뉴스의 같은 뉴스는 어떻게 하지 ?
- 키워드 처음 등록시 몇개를 수집해야하는가 ?
    - 전부 ?
    - 100개만 ?
- 스케줄러 100개의 키워드
- 키워드 삭제했을때 뉴스가 같이 삭제가 안된다..
  - ...( , cascade = CascadeType.REMOVE)
  - @OnDelete(action = OnDeleteAction.CASCADE)
  두 방법으로 했을때 처리가 되지 않음..
  - 그런데 아이디 별로 키워드를 가지게 할거면, 뉴스 삭제를 할 필요가 없긴한데, 기본 전제가 정확하지 않으니. 일단은 . 
