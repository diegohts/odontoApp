create table procedimentos (
	id bigint not null auto_increment,
	nome varchar(100) not null,
	descricao VARCHAR(255) NOT NULL,
	preco DECIMAL(10,2) NOT NULL,
	ativo tinyint not null,

	primary key(id)
);
