create sequence id_sequence_crypt start 100 increment 10;
create table sensor_vals (id int8 not null, uuid varchar(255), primary key (id));
