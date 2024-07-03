insert into USUARIOS (id, username, password, role) values(100, 'ana@email.com', '$2a$12$oPX8PZK6QVS1BHOicKkJTeZzgn.b9QSQlwpQKM1Mn4GEMreAOgE/i', 'ROLE_ADMIN');
insert into USUARIOS (id, username, password, role) values(101, 'bob@email.com', '$2a$12$oPX8PZK6QVS1BHOicKkJTeZzgn.b9QSQlwpQKM1Mn4GEMreAOgE/i', 'ROLE_CLIENTE');
insert into USUARIOS (id, username, password, role) values(102, 'jose@email.com', '$2a$12$oPX8PZK6QVS1BHOicKkJTeZzgn.b9QSQlwpQKM1Mn4GEMreAOgE/i', 'ROLE_CLIENTE');
insert into USUARIOS (id, username, password, role) values(103, 'toby@email.com', '$2a$12$oPX8PZK6QVS1BHOicKkJTeZzgn.b9QSQlwpQKM1Mn4GEMreAOgE/i', 'ROLE_CLIENTE');

insert into CLIENTES (id, nome, cpf, id_usuario) values(10, 'Bob Santos', '22726057020', 101);
insert into CLIENTES (id, nome, cpf, id_usuario) values(20, 'Jose Silva', '39166418089', 102);
