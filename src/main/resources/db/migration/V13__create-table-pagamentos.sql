create table pagamentos (
	id bigint not null auto_increment,
	consulta_id bigint not null,
	data_pagamento datetime not null,
	forma_pagamento varchar(30) not null,
	valor_pago decimal(10,2) not null,
	quem_pagou varchar(80),
	observacao VARCHAR(255),

	primary key(id),
	constraint fk_pagamentos_consulta_id foreign key (consulta_id) references consultas(id)
);
