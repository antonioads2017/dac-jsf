CREATE TABLE pessoa(
	id SERIAL PRIMARY KEY,
	nome VARCHAR(200),
	cpf VARCHAR(15),
	dependente_uuid VARCHAR(50),
	CONSTRAINT pessoa_dependente_fk FOREIGN KEY (dependente_uuid)
			REFERENCES dependente(uuid)
			ON UPDATE ON DELETE CASCADE
);

CREATE TABLE dependente(
	uuid VARCHAR(50) PRIMARY KEY,
	nome VARCHAR(200),
	data DATE
);