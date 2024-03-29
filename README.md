## 프로젝트 명 : 여기 사람 in나요?
### 개발 개요 : 학교 주변의 실내 시설을 이용하고 있는 실시간 이용자 수와 혼잡도를 나타내는 앱
#
#### 구현 목적 : Covid 19로 인해 그동안 인식하지 못했던 일상 속의 불편함을 느끼고 있습니다.  
#### 그 중 실내시설을 이용을 망설이는 사람들을 위하여 시설의 혼잡도를 확인하고 예약할 수 있는 서비스를 기획하였습니다.
#
#### 개발 기간 : 2021.03 ~ 2021.11
#### 인원 구성 : 5명(팀장)
#### 개발 환경 : 
언어 | 서버 | DB | IDE
---|---|---|---|
Java, PHP |AWS ec2, Ubuntu, phpMyAdmin| Mysql 8.0.21 | Android Studio 4.0.2 (SDK 30.0.3)
#
## [기술 설명]
### 1. DB
정규화 및 데이터 베이스의 개념을 이해하였고, PK와 FK 및 칼럼 간의 관계를 고려하여 ER-diagram 작성 및 DB구축 가능, 이를 기반으로 CRUD 작성 가능

### 2. 웹서버
AWS 를 이용하여 Ubuntu 서버 생성 가능, 서버에 phpMyAdmin 설치 가능, File zilla 프로그램을 통해 서버에 파일 업로드 가능

### 3. 로그인 및 회원가입
Shared Preference을 이용하여 자동 로그인 구현 가능

### 4. 예약 기능
AsyncTask를 이용하여 비동기 처리 방식의 함수 작성 가능, 예약에 필요한 데이터를 phpMyAdmin에 저장하기 위하여
UrlConnection 방법으로 Http서버와 통신 가능

### 5. 혼잡도 확인 기능
Naver API를 이용해 지도 연결 가능, 기본값으로 표시되는 위치 지정할 수 있음, 원하는 위치, 크기, 아이콘을 지정하여 마커 지도 위에 표시 가능

#
#
## 화면 및 상세 설명
<img src="https://user-images.githubusercontent.com/73291755/205490367-8ea3c906-6a4c-49e4-b3ba-68715759836f.png" width="600" height="400"/>

* [메인 화면]
  * 혼잡도: 학교 주변 시설의 혼잡도를 확인할 수 있음  
  * 예약: 학교 주변의 카페, 식당을 예약할 수 있음
* [혼잡도 확인]
학교 주변 시설에 대해 ‘혼잡/보통/여유’ 3 단계로 구분하여 혼잡도 확인 가능
* [시설 등록]
일반인/운영자/개발자로 사용자가 구분되며, 그중 운영자가 예약 가능한 시설로 등록 시 필요한 시설 정보를 입력

<img src="https://user-images.githubusercontent.com/73291755/205490523-9ad4ef1f-6723-49aa-a811-4810f2c903fc.png" width="600" height="400"/>

* [관리자 페이지]
개발자는 예약, 회원, 시설의 전체 리스트를 확인할 수 있고, 예약에서 제외할 시설을 삭제할 수 있음
* [예약-(1)]
사용자가 원하는 ‘날짜, 시간, 인원수’ 입력
* [예약-(2)]
가게 명의 일부분만 입력하여 장소 검색이 가능하며, 카페, 식당 등 카테고리별 필터링 가능

<img src="https://user-images.githubusercontent.com/73291755/205490409-0c43033a-b2ea-48e7-8681-5bbe16164c06.png" width="600" height="400"/>

* [예약-(3)]
예약이 불가능한 2 가지 경우에 따라 다르게 메시지 창 띄움
  * ① 이미 예약이 마감된 경우 : ‘해당시간 예약 마감’ 메시지 창 띄움
  * ② 예약 하려는 인원 수가 총 예약 가능한 인원 수를초과할 경우 : ‘예약 가능한 인원 수 초과로 인해 예약 불가능’ 메시지창 띄움
* [예약-(4)]
예약 확정 전 입력한 정보 확인
* [예약-(5)]
예약한 내역을 확인할 수 있으며, 예약 내역을 선택해 예약 취소를 할 수 있음

#
#
## [발생한 오류 및 해결 과정]
프로젝트를 진행하며 마주한 오류 중 하나로 로컬 환경에서 Mysql로 DB를 사용해왔는데, 단말기 테스트 시 로컬 서버의 DB에 접근이 불가능한 것이었습니다.  
이를 해결하기 위해 외부에 서버를 생성해 웹 페이지에서 데이터를 받는 방법을 선택하였습니다.  
AWS를 이용해 생성한 서버에 phpMyAdmin을 설치하고 FTP 프로그램을 이용해 기능 구현을 위해 작성한 CRUD 쿼리가 포함된 PHP 파일을 서버에 업로드하였습니다.  
Android studio에서 업로드한 PHP 파일의 URI주소에 접속하면 작성한 쿼리의 결과 값을 얻을 수 있는데 이 값을 android studio로 반환하는 방식으로 DB를 사용하였습니다.

#
## [소감]
프로젝트를 정리하면서 AWS에 서버를 만드는 대신에 AWS사에서 지원하는 RDS를이용하면 위의 방법보다 간편하게 클라우드에서 DB를 사용할 수 있다는 것을 알게되었습니다.  
RDS를 이용해보지 못해 아쉬움이 남아 기회가 되면 프로젝트를 수정할 때 RDS를 이용하여 개발해보고 싶습니다.
