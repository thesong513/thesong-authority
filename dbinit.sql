create table t_power
(
    id int auto_increment comment 'id' primary key ,
    power_id   varchar(50) not null comment '权限id',
    power_name varchar(50) not null comment '权限类型',
    operation  varchar(50) not null comment '操作'
);

INSERT INTO shiro.t_power (power_id, power_name, operation) VALUES ('1', '注册', '1');
INSERT INTO shiro.t_power (power_id, power_name, operation) VALUES ('2', '登录', '1');
INSERT INTO shiro.t_power (power_id, power_name, operation) VALUES ('3', '登出', '1');
INSERT INTO shiro.t_power (power_id, power_name, operation) VALUES ('4', '查找用户', '1');
INSERT INTO shiro.t_power (power_id, power_name, operation) VALUES ('5', '新增用户', '1');
INSERT INTO shiro.t_power (power_id, power_name, operation) VALUES ('6', '删除用户', '1');
INSERT INTO shiro.t_power (power_id, power_name, operation) VALUES ('7', '编辑用户', '1');
INSERT INTO shiro.t_power (power_id, power_name, operation) VALUES ('8', '新增角色', '1');
INSERT INTO shiro.t_power (power_id, power_name, operation) VALUES ('9', '删除角色', '1');
INSERT INTO shiro.t_power (power_id, power_name, operation) VALUES ('10', '编辑角色', '1');
INSERT INTO shiro.t_power (power_id, power_name, operation) VALUES ('11', '查找角色', '1');
INSERT INTO shiro.t_power (power_id, power_name, operation) VALUES ('12', '查找权限', '1');

create table t_role
(
    id int auto_increment comment 'id' primary key ,
    role_id   varchar(50) not null comment '角色id',
    role_name varchar(100)  not null comment '角色名字',
    ban       int default 1 not null comment '0-删除，1-未删除'
);

INSERT INTO shiro.t_role (role_id, role_name, ban) VALUES ('1', 'sysadmin', 1);
INSERT INTO shiro.t_role (role_id, role_name, ban) VALUES ('2', 'admin', 1);
INSERT INTO shiro.t_role (role_id, role_name, ban) VALUES ('3', 'guest', 1);

create table t_role_power
(
    id       int auto_increment primary key,
    role_id  varchar(50) not null,
    power_id varchar(50) not null
);

INSERT INTO shiro.t_role_power (id, role_id, power_id) VALUES (1, '1', '1');
INSERT INTO shiro.t_role_power (id, role_id, power_id) VALUES (2, '2', '2');
INSERT INTO shiro.t_role_power (id, role_id, power_id) VALUES (3, '3', '3');
INSERT INTO shiro.t_role_power (id, role_id, power_id) VALUES (4, '1', '4');
INSERT INTO shiro.t_role_power (id, role_id, power_id) VALUES (5, '1', '5');
INSERT INTO shiro.t_role_power (id, role_id, power_id) VALUES (6, '1', '6');
INSERT INTO shiro.t_role_power (id, role_id, power_id) VALUES (7, '1', '7');
INSERT INTO shiro.t_role_power (id, role_id, power_id) VALUES (8, '1', '8');
INSERT INTO shiro.t_role_power (id, role_id, power_id) VALUES (9, '1', '9');
INSERT INTO shiro.t_role_power (id, role_id, power_id) VALUES (10, '1', '10');
INSERT INTO shiro.t_role_power (id, role_id, power_id) VALUES (11, '1', '12');
INSERT INTO shiro.t_role_power (id, role_id, power_id) VALUES (12, '1', '12');

create table t_user
(
    id int auto_increment comment 'id' primary key ,
    user_id   varchar(50) not null comment 'user id',
    user_name varchar(100)  not null comment '用户名',
    password  varchar(100)  not null comment '密码',
    ban       int default 1 not null comment '用户状态(0-删除，1-正常，2-被禁)'
);

INSERT INTO shiro.t_user (user_id, user_name, password, ban) VALUES ('1', 'admin', '$2a$10$K6eBgcqxRfEIwDZE529Yz.lCFfDOeXEmmhE2eWvMs0qO2JSf7oEca', 1);
INSERT INTO shiro.t_user (user_id, user_name, password, ban) VALUES ('2', 'test', '$2a$10$qr74R3VxEJvI9zEwvPuzwOy3WW7zUeMnz7rhF5FwBFKI1EaZ9HrQG', 1);
INSERT INTO shiro.t_user (user_id, user_name, password, ban) VALUES ('3', '123', '$2a$10$X6foVP9Z3aIDJC5onhc7De1xCm/det5ATMUbZjOi3Wd5JRXNYnfgO', 1);
INSERT INTO shiro.t_user (user_id, user_name, password, ban) VALUES ('4', '123www', '$2a$10$Y1JVfZEcAhdtO.PlFzQkFeQaCR3TQdgMOaqD.QHX5omDrA8eJjkR6', 1);
INSERT INTO shiro.t_user (user_id, user_name, password, ban) VALUES ('5', '123wwww', '$2a$10$HDGXEKARtp3n3wmTecEkXuzmqxDbJCQ3Kh58FgsBbeyN9OdwHjaYW', 1);
INSERT INTO shiro.t_user (user_id, user_name, password, ban) VALUES ('6', '123w123', '$2a$10$DWksViiajEwls/9ERnArnubvApOxqp02SawVVJ8yCR40aqmPxLAje', 1);
INSERT INTO shiro.t_user (user_id, user_name, password, ban) VALUES ('7', '123w1234', '$2a$10$kLj4ID2Pu.hYhiZo0c7bZuPz2ZVDVj2z6g7jxCjOzgAB07NIjvFxi', 1);
INSERT INTO shiro.t_user (user_id, user_name, password, ban) VALUES ('8', '123w12345', '$2a$10$a4p8Otlz.xxl3JT9Dgh8zuFlYCCuaw87MjASJTo/v20ajox5v9HRS', 1);
INSERT INTO shiro.t_user (user_id, user_name, password, ban) VALUES ('9', '123w123456', '$2a$10$Cy7fUQUCiOrJJdu6KCoFa.FrvT2qQKdqtMpSgsxSXMP672qNzMaSK', 1);
INSERT INTO shiro.t_user (user_id, user_name, password, ban) VALUES ('10', '123w1234567', '$2a$10$VhCotNmq796ZBXoF0qmc3OinjOAlaxuv5nSacV3ifLcOqFgFbMBpm', 1);
INSERT INTO shiro.t_user (user_id, user_name, password, ban) VALUES ('11', '123w12345678', '$2a$10$KA5f3EUFj.kjoJihuSluP.w0.GIVeFjkcu/yyNRIvnZ7dX0kuTVa2', 1);
INSERT INTO shiro.t_user (user_id, user_name, password, ban) VALUES ('12', '123w123456789', '$2a$10$FJuUV9AEce6ATNbs0GmpUOmyA4v3bthc82M1qlCE7tFToDhiz9LQK', 1);

create table t_user_role
(
    id      int auto_increment primary key,
    user_id varchar(50) not null,
    role_id varchar(50) not null
);

INSERT INTO shiro.t_user_role (id, user_id, role_id) VALUES (1, '1', '1');
INSERT INTO shiro.t_user_role (id, user_id, role_id) VALUES (2, '2', '3');
INSERT INTO shiro.t_user_role (id, user_id, role_id) VALUES (3, '3', '3');
INSERT INTO shiro.t_user_role (id, user_id, role_id) VALUES (4, '4', '3');
INSERT INTO shiro.t_user_role (id, user_id, role_id) VALUES (5, '5', '1');
INSERT INTO shiro.t_user_role (id, user_id, role_id) VALUES (6, '6', '3');
INSERT INTO shiro.t_user_role (id, user_id, role_id) VALUES (7, '7', '3');
INSERT INTO shiro.t_user_role (id, user_id, role_id) VALUES (8, '8', '3');
INSERT INTO shiro.t_user_role (id, user_id, role_id) VALUES (9, '9', '3');
INSERT INTO shiro.t_user_role (id, user_id, role_id) VALUES (10, '10', '3');
INSERT INTO shiro.t_user_role (id, user_id, role_id) VALUES (11, '11', '1');
INSERT INTO shiro.t_user_role (id, user_id, role_id) VALUES (12, '12', '3');