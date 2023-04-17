create table convenios (
	id bigint not null auto_increment,
	nome varchar(100) not null,
	contato varchar(100) not null,
	cobertura text,
	observacao text,
	ativo tinyint not null,

	primary key(id)
);
