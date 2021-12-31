INSERT INTO events (creatorid, duration, eventscope, startsat) VALUES ( 1, 120, 0,  '2021-12-12 18:44:32.000000');
INSERT INTO events ( creatorid, duration, eventscope, startsat) VALUES ( 1, 120, 0, '2021-12-04 17:45:55.000000');
INSERT INTO events ( creatorid, duration, eventscope, startsat) VALUES ( 1, 120, 1,  '2021-12-13 18:44:32.000000');
INSERT INTO events ( creatorid, duration, eventscope, startsat) VALUES ( 2, 120, 2,  '2021-12-18 18:44:32.000000');
INSERT INTO events ( creatorid, duration, eventscope, startsat) VALUES ( 1, 120, 1,  '2021-12-08 18:44:32.000000');

INSERT INTO invitee (userid, event_id) VALUES (1,1);
INSERT INTO invitee (userid, event_id) VALUES (2,1);
INSERT INTO invitee (userid, event_id) VALUES (3,1);
INSERT INTO invitee (userid, event_id) VALUES (4,1);

INSERT INTO invitee (userid, event_id) VALUES (1,2);
INSERT INTO invitee (userid, event_id) VALUES (2,2);
INSERT INTO invitee (userid, event_id) VALUES (3,2);

INSERT INTO invitee (userid, event_id) VALUES (5,3);
INSERT INTO invitee (userid, event_id) VALUES (4,3);
INSERT INTO invitee (userid, event_id) VALUES (2,3);
INSERT INTO invitee (userid, event_id) VALUES (3,3);
