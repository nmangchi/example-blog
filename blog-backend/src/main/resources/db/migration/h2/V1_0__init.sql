create table user(
	seq int primary key,
	login_id varchar(20) not null,
	login_password varchar(100) not null,
	name varchar(30) not null,
	nickname varchar(60) null
);
create sequence SEQ_USER;
insert into user(seq, login_id, login_password, name, nickname) 
values (NEXTVAL('SEQ_USER'), 'user1','$2a$10$Dx76JC2fF1qhumYw2Br32uPCMTSDNva0oBcyc/v1uWFNTTGuv6nzi','유저1','닉네임1');

create table oauth_client_details (
  client_id VARCHAR(256) PRIMARY KEY,
  resource_ids VARCHAR(256),
  client_secret VARCHAR(256),
  scope VARCHAR(256),
  authorized_grant_types VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities VARCHAR(256),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(256)
);
insert into oauth_client_details (client_id, client_secret, resource_ids, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove)
values ('post-client', '$2a$10$CkDVSZmpR9evHxGJNttu6Og.4/X59rSa4GiXFMPCjVsuPmz9EQ6yq', null, 'read,write', 'authorization_code,password,client_credentials,implicit,refresh_token', null, 'ROLE_CLIENT', 36000, 2592000, null, null);
