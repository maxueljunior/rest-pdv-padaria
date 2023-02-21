CREATE TABLE IF NOT EXISTS `tb_venda_estoque` (
  `preco` double DEFAULT NULL,
  `quantidade` double DEFAULT NULL,
  `venda_id` bigint NOT NULL,
  `produto_id` bigint NOT NULL,
  PRIMARY KEY (`produto_id`,`venda_id`),
  KEY `FKqnaf50wtybyfidvnhjp1gjuc5` (`venda_id`),
  CONSTRAINT `FKlbtrmfx4vx62eksl6uorqr1b6` FOREIGN KEY (`produto_id`) REFERENCES `estoque` (`id`),
  CONSTRAINT `FKqnaf50wtybyfidvnhjp1gjuc5` FOREIGN KEY (`venda_id`) REFERENCES `vendas` (`id`)
);

