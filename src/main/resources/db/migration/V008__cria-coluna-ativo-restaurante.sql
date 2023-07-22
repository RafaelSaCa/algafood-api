
alter table restaurante add ativo TINYINT(1) not null;
UPDATE restaurante SET ativo = true;