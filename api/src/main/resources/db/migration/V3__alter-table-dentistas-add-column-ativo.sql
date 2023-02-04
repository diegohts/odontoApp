alter table dentistas add ativo tinyint not null;
update dentistas set ativo = 1;
