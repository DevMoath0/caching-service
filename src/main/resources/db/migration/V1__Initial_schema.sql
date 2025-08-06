CREATE TABLE users (
                             id BIGINT AUTO_INCREMENT NOT NULL,
                             name VARCHAR(255) NOT NULL,
                             age INTEGER NOT NULL,
                             mobile_number VARCHAR(255) NOT NULL,
                             email VARCHAR(255) NOT NULL,
                             created_by VARCHAR(255),
                             created_date TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3),
                             modified_by VARCHAR(255),
                             modified_date TIMESTAMP(3) NULL,
                             PRIMARY KEY (id)
);
CREATE INDEX users_mobile_number_idx ON users (mobile_number);
