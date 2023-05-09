ALTER TABLE pacientes
ADD COLUMN convenio_id bigint,
ADD COLUMN num_carteirinha varchar(20),
ADD CONSTRAINT fk_pacientes_convenio_id FOREIGN KEY (convenio_id) REFERENCES convenios(id);
