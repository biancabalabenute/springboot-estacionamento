insert into USUARIOS (id, username, password, role)
    values (100, 'ana@email.com', '$2a$12$oPX8PZK6QVS1BHOicKkJTeZzgn.b9QSQlwpQKM1Mn4GEMreAOgE/i', 'ROLE_ADMIN');
insert into USUARIOS (id, username, password, role)
    values (101, 'bob@email.com', '$2a$12$oPX8PZK6QVS1BHOicKkJTeZzgn.b9QSQlwpQKM1Mn4GEMreAOgE/i', 'ROLE_CLIENTE');
insert into USUARIOS (id, username, password, role)
    values (102, 'jose@email.com', '$2a$12$oPX8PZK6QVS1BHOicKkJTeZzgn.b9QSQlwpQKM1Mn4GEMreAOgE/i', 'ROLE_CLIENTE');
insert into USUARIOS (id, username, password, role)
    values (103, 'toby@email.com', '$2a$12$oPX8PZK6QVS1BHOicKkJTeZzgn.b9QSQlwpQKM1Mn4GEMreAOgE/i', 'ROLE_CLIENTE');

insert into CLIENTES (id, nome, cpf, id_usuario) values (10, 'Bob Santos', '22726057020', 101);
insert into CLIENTES (id, nome, cpf, id_usuario) values (20, 'Jose Silva', '39166418089', 102);

insert into vagas (id, codigo, status) values (100, 'A-01', 'OCUPADA');
insert into vagas (id, codigo, status) values (200, 'A-02', 'OCUPADA');
insert into vagas (id, codigo, status) values (300, 'A-03', 'OCUPADA');
insert into vagas (id, codigo, status) values (400, 'A-04', 'LIVRE');
insert into vagas (id, codigo, status) values (500, 'A-05', 'LIVRE');

insert into clientes_tem_vagas (numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga)
    values ('20230313-101300', 'FIT-1020', 'FIAT', 'PALIO', 'VERDE', '2023-03-13 10:15:00', 20, 100);
insert into clientes_tem_vagas (numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga)
    values ('20230314-101400', 'SIE-1020', 'FIAT', 'SIENA', 'BRANCO', '2023-03-14 10:15:00', 10, 200);
insert into clientes_tem_vagas (numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga)
    values ('20230315-101500', 'FIT-1020', 'FIAT', 'PALIO', 'VERDE', '2023-03-14 10:15:00', 20, 300);
