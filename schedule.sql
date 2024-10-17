
-- 일정 테이블 생성
create table schedule (
      id bigint not null auto_increment,
      username varchar(255) not null,
      password varchar(255) not null,
      title varchar(255) not null,
      content varchar(255) not null,
      create_date datetime(6),
      update_date datetime(6),
      weather varchar(255) not null,
      primary key (id)
);


-- 댓글 테이블 생성
create table comment (
     id bigint not null auto_increment,
     username varchar(255) not null,
     password varchar(10) not null,
     comment varchar(255) not null,
     create_date datetime(6),
     update_date datetime(6),
     schedule_id bigint not null,
     primary key (id)
);


-- 일정에 배정된 유저 테이블(중간 테이블) 생성
create table schedule_to_user (
    schedule_id bigint not null,
    user_id bigint not null,
    primary key(schedule_id, user_id)
);


-- 유저 테이블 생성
create table user (
    id bigint not null auto_increment,
    username varchar(255) not null,
    password varchar(255) not null,
    email varchar(255) not null,
    create_date datetime(6),
    update_date datetime(6),
    role enum ('ADMIN','USER') not null,
    primary key (id)
);