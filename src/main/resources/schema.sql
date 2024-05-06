CREATE TABLE `product_listing` (
   `id` int NOT NULL AUTO_INCREMENT,
   `quantity` int DEFAULT NULL,
   `price` double DEFAULT NULL,
   `user_id` int DEFAULT NULL,
   `name` varchar(50) DEFAULT NULL,
   PRIMARY KEY (`id`),
   KEY `user_id` (`user_id`),
   CONSTRAINT `product_listing_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

 CREATE TABLE `transactions` (
   `id` int NOT NULL AUTO_INCREMENT,
   `user_id` int DEFAULT NULL,
   `product_id` int DEFAULT NULL,
   `action` varchar(50) DEFAULT NULL,
   `created_at` date DEFAULT NULL,
   `metadata` json DEFAULT NULL,
   PRIMARY KEY (`id`),
   KEY `user_id` (`user_id`),
   KEY `product_id` (`product_id`),
   CONSTRAINT `transactions_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
   CONSTRAINT `transactions_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product_listing` (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

 CREATE TABLE `user` (
   `user_id` int NOT NULL,
   `wallet_balance` double DEFAULT NULL,
   `user_name` varchar(50) DEFAULT NULL,
   `metadata` json DEFAULT NULL,
   PRIMARY KEY (`user_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci