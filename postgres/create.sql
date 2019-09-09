
CREATE TABLE dependente(
	uuid VARCHAR(50) PRIMARY KEY,
	nome VARCHAR(200),
	dataN DATE
);
CREATE TABLE pessoa(
	id SERIAL PRIMARY KEY,
	nome VARCHAR(200),
	cpf VARCHAR(15) UNIQUE,
	dependente_uuid VARCHAR(50),
	CONSTRAINT pessoa_dependente_fk FOREIGN KEY (dependente_uuid)
			REFERENCES dependente(uuid)
);
