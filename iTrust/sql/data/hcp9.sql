INSERT INTO Personnel(
MID,
AMID,
role,
lastName, 
firstName, 
address1,
address2,
city,
state,
zip,
zip1,
zip2,
phone,
phone1,
phone2,
phone3,
specialty,
email)
VALUES (
9000000010,
null,
'hcp',
'Zoidberg',
'John',
'6789 S Street',
'PO BOX 4',
'CityName',
'NY',
'12345-1234',
'12345',
'1234',
'999-888-7777',
'999',
'888',
'7777',
NULL,
'jzoidberg@iTrust.org'
) ON DUPLICATE KEY UPDATE MID = MID;

INSERT INTO Users(MID, 
password, 
role, 
sQuestion, 
sAnswer) 
VALUES(9000000010, 
'1a91d62f7ca67399625a4368a6ab5d4a3baa6073', 
'hcp', 
'first letter?', 
'z')
ON DUPLICATE KEY UPDATE MID = MID;
/*password: pw*/
INSERT INTO HCPAssignedHos(HCPID, HosID) VALUES(9000000010,'9191919191')
ON DUPLICATE KEY UPDATE HCPID = HCPID;

INSERT INTO Appointment(doctor_id, patient_id, sched_date, appt_type)
VALUES(9000000010, 2, '2012-08-22 13:30:00', 'General Checkup');

INSERT INTO AppointmentRequests(doctor_id, patient_id, sched_date, appt_type,pending, accepted)
VALUES(9000000010, 26, '2012-02-24 09:00:00', 'General Checkup',1,0);
