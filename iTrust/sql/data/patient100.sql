DELETE FROM Users WHERE MID = 100;
/*For every insert, first clear the database of those entries.*/
INSERT INTO Users(MID, password, role, sQuestion, sAnswer) 
			VALUES (100, '1a91d62f7ca67399625a4368a6ab5d4a3baa6073', 'patient', 'Is it broken?', 'I can fix it.');
/*password: pw*/
DELETE FROM Patients WHERE MID = 100;
INSERT INTO Patients (MID, firstName, lastName, email, phone1, phone2, phone3) 
VALUES (100, 'Anakin', 'Skywalker', 'thechosenone@itrust.com', '919', '419', '5555');

DELETE FROM OfficeVisits WHERE PatientID = 100;
INSERT INTO OfficeVisits(id,visitDate,HCPID,notes,HospitalID,PatientID)
	VALUES (1093,'2012-1-01',9000000000,'Testing midi-chlorian count.','1',100);

DELETE FROM OVMedication WHERE VisitID = 1093;
INSERT INTO OVMedication(NDCode, VisitID, StartDate, EndDate, Dosage, Instructions)
	VALUES ('483012382', 1093, '2012-3-01', ADDDATE(CURDATE(),9000), 50, 'Take twice daily');
	/*In that OV, Kelly Doctor documents a future rx for Midichlominene from Mar. 1 to a long time from now in a galaxy far far away*/

DELETE FROM OVDiagnosis WHERE VisitID = 1093;
INSERT INTO OVDiagnosis(ICDCode, VisitID) VALUES 
			(493.00, 1093);
			
INSERT INTO Allergies(PatientID, Description, FirstFound, Code)
	VALUES (100,'Lantus','2012-2-03', '00882219'); /* Allergy for Lantus */
			
DELETE FROM DeclaredHCP WHERE PatientID = 100;
INSERT INTO DeclaredHCP(PatientID, HCPID) VALUES (100, 9000000000);