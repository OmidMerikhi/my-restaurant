CREATE TABLE food(
        id VARCHAR(200) PRIMARY KEY NOT NULL ,
        title VARCHAR(200) DEFAULT NULL,
        material VARCHAR(200) DEFAULT NULL,
        price DOUBLE DEFAULT NULL,
        size VARCHAR(200) DEFAULT NULL,
        active BOOLEAN DEFAULT FALSE,
        category ENUM('PIZZA','WARM_SANDWICH','COLD_SANDWICH','SPECIAL','CHICKEN','DONER_KABAB','DRINK','STARTER')
)