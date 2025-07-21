CREATE TABLE IF NOT EXISTS appointments(
    id bigint not null auto_increment,
    physician_id bigint not null,
    patient_id  bigint not null,
    date datetime not null,

    primary key(id),

    constraint fk_appointments_patient_id foreign key (patient_id) references patients(id),
    constraint fk_appointments_physician_id foreign key (physician_id) references physicians(id)
);