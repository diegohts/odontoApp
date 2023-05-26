CREATE TABLE pessoas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    imagem_url VARCHAR(255),
    usuario_id BIGINT NOT NULL,
    genero VARCHAR(15),
    data_nascimento DATE,
    PRIMARY KEY (id),
    CONSTRAINT fk_pessoas_usuarios_id FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);
