/*		ShortCuts
ctrl + Enter : 세미콜론으로 분리된 해당 블럭의 쿼리문 실행
ctrl + shift + Enter : 세미콜론으로 분리된 블럭 친 다중 쿼리문 실행
ctrl + t : 새로운 쿼리 에디터 열기
ctrl + shift + o : 저장된 스크립트(*.sql)파일 열기
ctrl + b : 쿼리문 정리해줌.(개행이나 인덴팅 등.)			*/
/*	테이블 삭제	*/
DROP TABLE products;
/*	테이블 모든 컬럼 조회	*/
SELECT * FROM PRODUCTS;
/*	테이블 생성	*/
CREATE TABLE 04_productmanage.products (
  ID INT(11) NOT NULL AUTO_INCREMENT,
  NAME VARCHAR(45) NOT NULL,
  PRICE INT(11) NOT NULL,
  MANUF VARCHAR(20) NOT NULL,
  PRIMARY KEY (ID))
ENGINE = InnoDB
auto_increment = 15
DEFAULT CHARACTER SET = UTF8MB4;