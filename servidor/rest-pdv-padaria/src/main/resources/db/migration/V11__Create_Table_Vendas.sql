CREATE TABLE IF NOT EXISTS `vendas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `condicao_pagamento` int,
  `data_venda` datetime(6),
  `valor_total` double,
  `cliente_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbvhsxopghjjhynkwca4resq7l` (`cliente_id`),
  CONSTRAINT `FKbvhsxopghjjhynkwca4resq7l` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`)
);

