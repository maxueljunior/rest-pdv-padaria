CREATE TABLE IF NOT EXISTS `estoque` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `data_compra` datetime(6),
  `data_validade` datetime(6),
  `descricao` varchar(80),
  `quantidade` double,
  `unidade` varchar(2),
  PRIMARY KEY (`id`)
);
