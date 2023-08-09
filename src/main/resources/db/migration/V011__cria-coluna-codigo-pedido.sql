alter table pedido add codigo varchar(36) not null after id;
UPDATE pedido SET codigo = uuid();
alter Table pedido add constraint uk_pedido_codigo UNIQUE (codigo);