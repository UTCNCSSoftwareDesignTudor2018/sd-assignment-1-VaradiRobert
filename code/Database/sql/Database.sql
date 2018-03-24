DROP DATABASE `course_management`;
CREATE DATABASE `course_management`;

CREATE TABLE `course_management`.`group`
(
	`id` INT NOT NULL,
    `group_number` INT NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `course_management`.`student`
(
	`identity_card_number` INT NOT NULL,
    `personal_numerical_code` VARCHAR(20) NOT NULL,
    `address` VARCHAR(300) NOT NULL,
    `user_name` VARCHAR(30) NOT NULL,
    `password` VARCHAR(100) NOT NULL,
    `email` VARCHAR(60),
    `first_name` VARCHAR(30) NOT NULL,
    `last_name` VARCHAR(30) NOT NULL,
    `phone` VARCHAR(20),
    `group_id` INT,
    PRIMARY KEY(`identity_card_number`),
    FOREIGN KEY(`group_id`) REFERENCES `course_management`.`group`(`id`)
);

CREATE TABLE `course_management`.`enrollment_status`
(
	`id` INT NOT NULL,
    `status_name` VARCHAR(20) NOT NULL,
    PRIMARY KEY(`id`)
);

CREATE TABLE `course_management`.`course`
(
	`id` INT NOT NULL,
    `name` VARCHAR(50) NOT NULL,
    `credits` INT NOT NULL,
    PRIMARY KEY(`id`)
);

CREATE TABLE `course_management`.`teacher`
(
	`id` INT NOT NULL,
	`user_name` VARCHAR(30) NOT NULL,
    `password` VARCHAR(100) NOT NULL,
    `email` VARCHAR(30),
    `first_name` VARCHAR(30) NOT NULL,
    `last_name` VARCHAR(30) NOT NULL,
    `phone` VARCHAR(10),
    PRIMARY KEY(`id`)
);

CREATE TABLE `course_management`.`post`
(
	`id` INT NOT NULL,
    `course_id` INT NOT NULL,
    `teacher_id` INT NOT NULL, 
    `subject` VARCHAR(100),
    `content` VARCHAR(3000),
    `date` DATETIME,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`course_id`) REFERENCES `course_management`.`course`(`id`),
    FOREIGN KEY(`teacher_id`) REFERENCES `course_management`.`teacher`(`id`)
);

CREATE TABLE `course_management`.`grade_type`
(
	`id` INT NOT NULL,
    `type_name` VARCHAR(20),
    PRIMARY KEY(`id`)
);

CREATE TABLE `course_management`.`exam`
(
	`id` INT NOT NULL,
    `teacher_id` INT NOT NULL,
    `course_id` INT NOT NULL,
    date DATETIME NOT NULL,
	PRIMARY KEY(`id`),
    FOREIGN KEY(`teacher_id`) REFERENCES `course_management`.`teacher`(`id`),
    FOREIGN KEY(`course_id`) REFERENCES `course_management`.`course`(`id`)
);

CREATE TABLE `course_management`.`enrollment`
(
	`id` INT NOT NULL,
    `course_id` INT NOT NULL,
    `student_id` INT NOT NULL,
    `enrollment_status_id` INT NOT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`course_id`) REFERENCES `course_management`.`course`(`id`),
    FOREIGN KEY(`student_id`) REFERENCES `course_management`.`student`(`identity_card_number`),
    FOREIGN KEY(`enrollment_status_id`) REFERENCES `course_management`.`enrollment_status`(`id`)
);

CREATE TABLE `course_management`.`grade`
(
	`id` INT NOT NULL,
    `value` REAL NOT NULL,
    `weight` REAL NOT NULL,
    `course_id` INT NOT NULL,
    `teacher_id` INT NOT NULL,
    `date` DATETIME NOT NULL,
    `student_id` INT NOT NULL,
    `type_id` INT NOT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`course_id`) REFERENCES `course_management`.`course`(`id`),
    FOREIGN KEY(`student_id`) REFERENCES `course_management`.`student`(`identity_card_number`),
    FOREIGN KEY(`teacher_id`) REFERENCES `course_management`.`teacher`(`id`),
    FOREIGN KEY(`type_id`) REFERENCES `course_management`.`grade_type`(`id`)
);