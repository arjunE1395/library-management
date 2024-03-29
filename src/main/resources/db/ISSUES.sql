CREATE TABLE ISSUES(
  ID INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  BOOK_ID INT(11),
  FOREIGN KEY (BOOK_ID) REFERENCES BOOKS(ID),
  USER_ID INT(11),
  FOREIGN KEY (USER_ID) REFERENCES USERS(ID),

  STATUS TINYINT DEFAULT 1,
  CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  MODIFIED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);