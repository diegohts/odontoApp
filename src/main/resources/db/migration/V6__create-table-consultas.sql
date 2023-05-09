create table consultas(

    id bigint not null auto_increment,
    dentista_id bigint not null,
    paciente_id bigint not null,
    data datetime not null,

    primary key(id),
    constraint fk_consultas_dentista_id foreign key(dentista_id) references dentistas(id),
    constraint fk_consultas_paciente_id foreign key(paciente_id) references pacientes(id)
);
