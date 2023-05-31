CREATE TABLE ClimbingParty (
	id INT PRIMARY KEY AUTO_INCREMENT,
    boardId INT NOT NULL,
    userId VARCHAR(20) NOT NULL,
    memberId VARCHAR(20) NOT NULL,
    FOREIGN KEY (boardId) REFERENCES ClimbingBoard(id)
);

DROP TABLE ClimbingParty;

SELECT * FROM ClimbingParty;