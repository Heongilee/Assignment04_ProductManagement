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
auto_increment = 0
DEFAULT CHARACTER SET = UTF8MB4;

/*	삽입 삭제 수정 세이프 모드 해제 (테이블 처음 생성하면 이거 해줘야함)	*/
SET SQL_SAFE_UPDATES = 0;

/*	테이블의 모든 데이터 삭제	*/
DELETE FROM PRODUCTS;

SELECT * FROM PRODUCTS WHERE ID = 1;
/*	튜플 삽입	*/
INSERT INTO 04_productmanage.products(PRODUCTS.NAME, PRODUCTS.PRICE, PRODUCTS.MANUF) VALUES('라면', 1500, '삼양');
INSERT INTO 04_productmanage.products(PRODUCTS.NAME, PRODUCTS.PRICE, PRODUCTS.MANUF) VALUES('사이다', 1000, '스프라이트');

/*	ID검색해서 해당 튜플 삭제	*/
DELETE FROM PRODUCTS WHERE ID = 1;

/*	삽입/삭제 후 id값 재정렬	*/
SET @CNT = 0;
UPDATE PRODUCTS SET PRODUCTS.ID = @CNT:=@CNT+1;


/*	UPDATE문	*/
UPDATE PRODUCTS SET NAME = '불닭볶음면', PRICE = 5000, MANUF = '농심' WHERE ID = 3;

/*	SELECT문	*/
SELECT ID FROM products;