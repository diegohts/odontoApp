ALTER TABLE consultas
ADD COLUMN procedimento_id BIGINT NOT NULL AFTER paciente_id,
ADD CONSTRAINT fk_consultas_procedimento_id FOREIGN KEY (procedimento_id) REFERENCES procedimentos (id);
