ALTER Table restaurante ADD aberto tinyint(1) not NULL;
UPDATE restaurante set aberto = false;