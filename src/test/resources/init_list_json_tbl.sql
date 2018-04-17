drop table if exists list_json_tbl;
create table list_json_tbl (
  id     int unsigned primary key unique  auto_increment,
  listJson   varchar(255) not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_general_ci;
insert into list_json_tbl (listJson) values (''),('[{"name":"测试","age":13},{"name":"测试2","age":14}]')