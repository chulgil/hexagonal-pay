CREATE DATABASE IF NOT EXISTS hexagonal_pay;
USE hexagonal_pay;

-- for Axon Framework (for Mismatching of Axon and Mysql) 4.6.0
#  mismatching을 막으려고 했던 수동 스크립트로 `index` 사용시 mysql8.0은 예약어로 alter테이블 관련 에러 발생
# create table IF NOT EXISTS dead_letter_entry (
#    dead_letter_id varchar(255) not null,
#    cause_message varchar(255),
#    cause_type varchar(255),
#    diagnostics longblob,
#    enqueued_at datetime not null,
#    `index` bigint not null,
#    last_touched datetime,
#    aggregate_identifier varchar(255),
#    event_identifier varchar(255) not null,
#    message_type varchar(255) not null,
#    meta_data longblob, payload longblob not null,
#    payload_revision varchar(255),
#    payload_type varchar(255) not null,
#    sequence_number bigint,
#    time_stamp varchar(255) not null,
#    token longblob, token_type varchar(255),
#    type varchar(255),
#    processing_group varchar(255) not null,
#    processing_started datetime,
#    sequence_identifier varchar(255) not null,
#    primary key (dead_letter_id)
# ) engine=InnoDB
