--liquibase formatted sql
				
--changeset auth:auth-data-1
insert into `liquibase-demo`(id, full_name) values (1, '张三');
insert into `liquibase-demo` (id,  full_name) values (2, '李四'); 
--rollback delete from  liquibase-demo where id in(1,2);

--changeset auth:auth-data-2 context:dev
insert into `liquibase-demo`(id, full_name) values (3, '王五');
delete from `liquibase-demo` where id =2;

--changeset auth:auth-data-3 context:prod
insert into `liquibase-demo`(id, full_name) values (4, '王五');

