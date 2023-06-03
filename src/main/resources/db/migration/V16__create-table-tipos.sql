create table tipos (

	id bigint not null auto_increment,
	usuario_id bigint NOT NULL,
	perfis_id bigint NOT NULL,

	primary key(id),
	CONSTRAINT fk_tipos_usuario_id FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
	CONSTRAINT fk_tipos_perfis_id FOREIGN KEY (perfis_id) REFERENCES perfil(id)

);
