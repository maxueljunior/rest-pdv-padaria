CREATE TABLE IF NOT EXISTS `cliente` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(80),
  `sobrenome` varchar(80),
  `data_nascimento` datetime(6),
  `endereco` varchar(80),
  `lucratividade` double,
  `sexo` varchar(1),
  `telefone` varchar(80),
  PRIMARY KEY (`id`)
);


