CREATE DATABASE FavoriteSitesDB;

USE FavoriteSitesDB;

CREATE TABLE userinfo (
    userid INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL
);

CREATE TABLE visits (
    visitid INT AUTO_INCREMENT PRIMARY KEY,
    userid INT,
    country VARCHAR(50),
    city VARCHAR(50),
    yearvisited INT,
    season VARCHAR(20),
    feature VARCHAR(50),
    comment TEXT,
    rating numeric(2,1),
    FOREIGN KEY (userid) REFERENCES userinfo(userid)
);

CREATE TABLE sharedvisits (
    shareid INT AUTO_INCREMENT PRIMARY KEY,
    senderid INT,
    receiverid INT,
    visitid INT,
    FOREIGN KEY (senderid) REFERENCES userinfo(userid),
    FOREIGN KEY (receiverid) REFERENCES userinfo(userid),
    FOREIGN KEY (visitid) REFERENCES visits(visitid)
);
INSERT INTO userinfo(username,password) VALUES ("Ozan" , "1234");
INSERT INTO userinfo(username,password) VALUES ("Bora" , "1234");
INSERT INTO userinfo(username,password) VALUES ("Orhan" , "1234");
INSERT INTO userinfo(username,password) VALUES ("Cigdem" , "1234");


INSERT INTO visits(userid,country,city,yearvisited,season,feature,comment,rating) VALUES (1,"Turkey","Izmir",2021,"Spring","Food","Best",5);
INSERT INTO visits(userid,country,city,yearvisited,season,feature,comment,rating) VALUES (1,"Germany","Berlin",2020,"Summer","Cultural","Average",3);
INSERT INTO visits(userid,country,city,yearvisited,season,feature,comment,rating) VALUES (1,"England","London",2021,"Spring","Sport","Magnificent",5);
INSERT INTO visits(userid,country,city,yearvisited,season,feature,comment,rating) VALUES (1,"Spain","Barcelona",2022,"Winter","Cultural","Perfect",4);
INSERT INTO visits(userid,country,city,yearvisited,season,feature,comment,rating) VALUES (1,"Italy","Rome",2022,"Spring","Food","Good",4);
INSERT INTO visits(userid,country,city,yearvisited,season,feature,comment,rating) VALUES (1,"Turkey","Uşak",2020,"Autumn","Cultural","Not Bad",2);

