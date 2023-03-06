INSERT IGNORE INTO loja (nome, endereco, telefone) 
VALUES 
('Show Multimarcas', 'Rua José', '11945855458'),
('Imports Showroom ', 'SMPW', '6135856485'),
('Nacional Car', 'Rua dos alagados', '31938465498'),
('Recife multimarcas', 'Em frente a Prais', '89963548523');

INSERT IGNORE INTO carro (marca, modelo, ano, cor, placa, loja_id) 
VALUES 
('Honda', 'Civic', '2015', 'Prata', 'SDF5894', 1),
('Chevrolet', 'Astra', '2005', 'Cinza', 'JJJ7812', 1),
('Fiat', 'Uno', '2011', 'Amarelo', 'GSM1245', 1),
('Wolkswagen', 'Volyage', '2020', 'Praeto', 'KTR6873', 2),
('Wolkswagen', 'Gol', '2021', 'Azul', 'JTW3625', 2),
('Hyundai', 'Creta', '2023', 'Branco', 'MTS7812', 2),
('Ford', 'Ranger', '2015', 'Prata', 'PAI9116', 3),
('Hyundai', 'HB20', '2015', 'Grafite', 'SPS4556', 3),
('Fiat', 'Argos', '2015', 'Preto', 'GGT1515', 3);

INSERT IGNORE INTO pessoa (nome, endereco, telefone) 
VALUES 
('Amadeu Moura', 'Rua 1000', '684765465'),
('Roberto Santos', 'Bairo das Garças', '6198745899'),
('Julio Amancio', 'Avenida Teófilo Otoni', '698463513549'),
('Cris dos Santos', 'SMPW 25 casa 152', '921461465'),
('Anna Catarina', 'Avenida das Palmeiras', '2165664499'),
('Bia de Souza', 'SQRI Asa Sul', '11998469784')
