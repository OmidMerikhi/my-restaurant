CREATE TABLE chef(
        id VARCHAR(100) NOT NULL PRIMARY KEY,
        first_name VARCHAR(200) DEFAULT NULL,
        last_name VARCHAR(200) DEFAULT NULL,
        national_code VARCHAR(200) NOT NULL ,
        experience_year INT(30) DEFAULT NULL,
        about VARCHAR(1000) DEFAULT NULL,
        service_date DATE,
        active BOOLEAN
)