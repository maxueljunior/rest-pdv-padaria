CREATE TABLE IF NOT EXISTS `fornecedor` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cnpj` varchar(18),
  `nome_do_contato` varchar(45),
  `razao_social` varchar(45),
  `telefone` varchar(13),
  PRIMARY KEY (`id`)
);

