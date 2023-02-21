CREATE TABLE IF NOT EXISTS `tb_compra_estoque` (
  `preco` double DEFAULT NULL,
  `quantidade` double DEFAULT NULL,
  `compra_id` bigint NOT NULL,
  `produto_id` bigint NOT NULL,
  PRIMARY KEY (`compra_id`,`produto_id`),
  KEY `FKigxcvl1tcxiuex7ux5l3o0fok` (`produto_id`),
  CONSTRAINT `FKcdrab955bwabrod3apayvdbx3` FOREIGN KEY (`compra_id`) REFERENCES `compras` (`id`),
  CONSTRAINT `FKigxcvl1tcxiuex7ux5l3o0fok` FOREIGN KEY (`produto_id`) REFERENCES `estoque` (`id`)
);


