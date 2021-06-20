CREATE SCHEMA notification;
GRANT ALL ON SCHEMA notification TO notifications;

create table notification.notifications
(
    notificationid  uuid not null
        constraint notificationid
            primary key,
    userid uuid,
    created_date_time      date,
    payload  varchar(255),
    read_date_time date
);

INSERT INTO notification.notifications (notificationid, userid, created_date_time, payload, read_date_time) VALUES ('6042edfd-a908-4364-a5ef-69f060bdf3da', '9d2a50a0-3648-41f2-b344-08a4459a7f27', '2021-06-16 21:59:46.873000', 'notification 1', null);
INSERT INTO notification.notifications (notificationid, userid, created_date_time, payload, read_date_time) VALUES ('d1657bab-3509-417f-ae8a-956b1b2cad38', '9d2a50a0-3648-41f2-b344-08a4459a7f27', '2021-06-16 21:59:46.873000', 'notification 2', null);
INSERT INTO notification.notifications (notificationid, userid, created_date_time, payload, read_date_time) VALUES ('79b608e7-d303-4ef4-a5ea-b88cce924619', '9d2a50a0-3648-41f2-b344-08a4459a7f27', '2021-06-16 21:59:46.873000', 'notification 3', null);

