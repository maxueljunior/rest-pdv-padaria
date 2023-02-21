CREATE TABLE IF NOT EXISTS `compras` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `valor_total` double,
  `fornecedor_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK39ugknqdrfpthmjqxqk8tsxac` (`fornecedor_id`),
  CONSTRAINT `FK39ugknqdrfpthmjqxqk8tsxac` FOREIGN KEY (`fornecedor_id`) REFERENCES `fornecedor` (`id`)
);

