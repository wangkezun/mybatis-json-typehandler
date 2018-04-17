drop table if exists object_json_tbl;
create table object_json_tbl (
  id     int unsigned primary key unique  auto_increment,
  objectJson varchar(255) not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_general_ci;
insert into object_json_tbl (objectJson) values (''),('{"name":"测试","age":13}');