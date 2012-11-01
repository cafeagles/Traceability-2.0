INSERT INTO Patients
(MID, 
firstName,
lastName, 
email,
address1,
address2,
city,
state,
zip1,
zip2,
phone1,
phone2,
phone3,
eName,
ePhone1,
ePhone2,
ePhone3,
iCName,
iCAddress1,
iCAddress2,
iCCity, 
ICState,
iCZip1,
iCZip2,
iCPhone1,
iCPhone2,
iCPhone3,
iCID,
DateOfBirth,
MotherMID,
FatherMID,
BloodType,
Ethnicity,
Gender,
TopicalNotes
)
VALUES (
26,
'Philip',
'Fry',
'fryp@fakencsu.edu',
'2870 Gorgon Drive',
'',
'Raleigh',
'NC',
'27603',
'',
'525',
'455',
'5654',
'Jumbo the Clown',
'515',
'551',
'5551',
'IC2',
'Street3',
'Street4',
'Downtown',
'GA',
'19023',
'2735',
'559',
'595',
'5995',
'4',
'1986-2-15',
0,
0,
'O-',
'Caucasian',
'Male',
"Crispy"
)  ON DUPLICATE KEY UPDATE MID = MID;

INSERT INTO Users(MID, password, role, sQuestion, sAnswer) 
			VALUES (26, '1a91d62f7ca67399625a4368a6ab5d4a3baa6073', 'patient', 'how you doin?', 'good')
 ON DUPLICATE KEY UPDATE MID = MID;
 /*password: pw*/

INSERT INTO OfficeVisits(id,visitDate,HCPID,notes,HospitalID,PatientID)
VALUES (3956,'2011-11-19',9000000007,'Why am I here?','1',26),
	   (3957,'2011-12-10',9000000000,'Wakka-Wakka-dis anna Wakka-Wakka-dat','1',26)
		 ON DUPLICATE KEY UPDATE id = id;

INSERT INTO DeclaredHCP(PatientID,HCPID) VALUE(26, 9000000010)
 ON DUPLICATE KEY UPDATE PatientID = PatientID;

INSERT INTO ovdiagnosis(ID, VisitID, ICDCode)
VALUES	(2721, 3956, 84.50),
		(2722, 3957, 487.00)
			ON DUPLICATE KEY UPDATE ID = ID;

INSERT INTO TransactionLog(loggedInMID, secondaryMID, transactionCode, timeLogged, addedInfo) 
                    VALUES (9000000000, 24, 410, '2007-06-23 06:54:59','ASDF'),
                           (9000000000, 24, 410, '2007-06-23 06:54:59','ASDF'),
                           (9000000000, 24, 1900,'2007-06-23 06:55:59','Viewed patient records'),
                           (9000000007, 24, 1900,'2007-06-23 06:54:59','Viewed patient records'),
                           (9000000007, 24, 1900,'2007-06-25 06:54:59','Viewed patient records'),
                           (9000000009, 24, 1900,'2007-06-24 06:54:59','Viewed patient records'),
                           (9000000000, 24, 3400,'2011-06-24 06:54:59','Patient added to monitoring list'),
                           (9000000009, 24, 1900,'2007-06-22 06:54:59','Viewed patient records');
