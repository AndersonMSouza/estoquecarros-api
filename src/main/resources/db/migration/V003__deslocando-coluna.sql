ALTER TABLE carro 
DROP COLUMN status_carro;

ALTER TABLE carro 
ADD COLUMN status_carro VARCHAR(30) NOT NULL AFTER placa;