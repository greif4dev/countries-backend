insert into usuario (login, senha, nome, administrador) values
('convidado', '$2a$10$8dizDn981J62tVUVxl1ZuunYfBS5WVg.qp840hJTZPXkInSrHrfIO', 'Usuário convidado', false);

insert into usuario (login, senha, nome, administrador) values
('admin', '$2a$10$nEO8YlqIeQp0IG20ZCDcn.DDLldsnDYYU3dFXavLoQNpA6uoYnE.e', 'Gestor', true);

insert into pais (nome, sigla, gentilico) values ('Brasil', 'BR', 'Brasileiro');
insert into pais (nome, sigla, gentilico) values ('Argentina', 'AR', 'Argentino');
insert into pais (nome, sigla, gentilico) values ('Alemanha', 'AL', 'Alemão');
