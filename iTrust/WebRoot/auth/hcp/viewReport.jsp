<%@page
	import="edu.ncsu.csc.itrust.report.PersonnelReportFilter.PersonnelReportFilterType"%>
<%@page
	import="edu.ncsu.csc.itrust.report.MedicalReportFilter.MedicalReportFilterType"%>
<%@page
	import="edu.ncsu.csc.itrust.report.MedicalReportFilter.MedicalReportFilterType"%>
<%@page
	import="edu.ncsu.csc.itrust.report.MedicalReportFilter.MedicalReportFilterType"%>
<%@page
	import="edu.ncsu.csc.itrust.report.DemographicReportFilter.DemographicReportFilterType"%>
<%@page
	import="edu.ncsu.csc.itrust.report.DemographicReportFilter.DemographicReportFilterType"%>
<%@page
	import="edu.ncsu.csc.itrust.report.MedicalReportFilter.MedicalReportFilterType"%>
<%@page
	import="edu.ncsu.csc.itrust.report.MedicalReportFilter.MedicalReportFilterType"%>
<%@page
	import="edu.ncsu.csc.itrust.report.MedicalReportFilter.MedicalReportFilterType"%>
<%@page
	import="edu.ncsu.csc.itrust.report.MedicalReportFilter.MedicalReportFilterType"%>
<%@page
	import="edu.ncsu.csc.itrust.report.MedicalReportFilter.MedicalReportFilterType"%>
<%@page
	import="edu.ncsu.csc.itrust.report.MedicalReportFilter.MedicalReportFilterType"%>
<%@page import="edu.ncsu.csc.itrust.beans.PatientBean"%>
<%@page
	import="edu.ncsu.csc.itrust.report.DemographicReportFilter.DemographicReportFilterType"%>
<%@page import="edu.ncsu.csc.itrust.beans.GroupReportBean"%>
<%@page import="edu.ncsu.csc.itrust.report.ReportFilter"%>
<%@page import="edu.ncsu.csc.itrust.action.GroupReportAction"%>
<%@page import="edu.ncsu.csc.itrust.report.MedicalReportFilter"%>
<%@page
	import="edu.ncsu.csc.itrust.report.PersonnelReportFilter.PersonnelReportFilterType"%>
<%@page
	import="edu.ncsu.csc.itrust.report.MedicalReportFilter.MedicalReportFilterType"%>
<%@page import="edu.ncsu.csc.itrust.report.DemographicReportFilter"%>
<%@page import="edu.ncsu.csc.itrust.report.PersonnelReportFilter"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - View Group Report";
%>

<%@include file="/header.jsp"%>

<style type="text/css">
.content{
	height:40px;
	overflow-y:auto;
}
</style>

<%
	DAOFactory factory = DAOFactory.getProductionInstance();
	GroupReportAction action = new GroupReportAction(factory);
	List<ReportFilter> filters = new ArrayList<ReportFilter>();
	if (request.getParameter("generate") != null) {
		if (request.getParameter("demoparams") != null
				&& !request.getParameter("demoparams").isEmpty()) {
			String demoparams = request.getParameter("demoparams");
			String demoFilters[] = demoparams.split(" ");
			for (String filter : demoFilters) {
				if (request.getParameter(filter) != null
						&& !request.getParameter(filter).isEmpty()) {
					DemographicReportFilterType filterType = DemographicReportFilter
							.filterTypeFromString(filter);
					DemographicReportFilter fil = new DemographicReportFilter(
							filterType, request.getParameter(filter),
							factory);
					filters.add(fil);
				}
			}
		}
		if (request.getParameter("medparams") != null
				&& !request.getParameter("medparams").isEmpty()) {
			String medparams = request.getParameter("medparams");
			String medFilters[] = medparams.split(" ");
			for (String filter : medFilters) {
				if (request.getParameter(filter) != null
						&& !request.getParameter(filter).isEmpty()) {
					MedicalReportFilterType filterType = MedicalReportFilter
							.filterTypeFromString(filter);
					if (filterType == MedicalReportFilterType.DIAGNOSIS_ICD_CODE
							|| filterType == MedicalReportFilterType.MISSING_DIAGNOSIS_ICD_CODE
							|| filterType == MedicalReportFilterType.ALLERGY
							|| filterType == MedicalReportFilterType.CURRENT_PRESCRIPTIONS
							|| filterType == MedicalReportFilterType.PASTCURRENT_PRESCRIPTIONS
							|| filterType == MedicalReportFilterType.PROCEDURE) {
						String[] vals = request
								.getParameterValues(filter);
						for (String val : vals) {
							MedicalReportFilter fil = new MedicalReportFilter(
									filterType, val, factory);
							filters.add(fil);
						}
					} else {
						MedicalReportFilter fil = new MedicalReportFilter(
								filterType,
								request.getParameter(filter), factory);
						filters.add(fil);
					}

				}
			}
		}
		if (request.getParameter("persparams") != null
				&& !request.getParameter("persparams").isEmpty()) {
			String persparams = request.getParameter("persparams");
			String personnelFilters[] = persparams.split(" ");
			for (String filter : personnelFilters) {
				if (request.getParameter(filter) != null
						&& !request.getParameter(filter).isEmpty()) {
					PersonnelReportFilterType filterType = PersonnelReportFilter
							.filterTypeFromString(filter);
					if (filterType == PersonnelReportFilterType.DLHCP) {
						String[] vals = request
								.getParameterValues(filter);
						for (String val : vals) {
							PersonnelReportFilter fil = new PersonnelReportFilter(
									filterType, val, factory);
							filters.add(fil);
						}
					} else {
						PersonnelReportFilter fil = new PersonnelReportFilter(
								filterType,
								request.getParameter(filter), factory);
						filters.add(fil);
					}
				}
			}
		}
%>
<h1>Group Report</h1>
<%
	GroupReportBean report = action.generateReport(filters);
		if (report != null) {
			loggingAction.logEvent(TransactionType.GROUP_REPORT_VIEW,
					loggedInMID, 0L, "");
%>
<p>
	Using filters:<br />
	<%
		for (ReportFilter filter : filters) {
					out.println(filter.toString() + "<br />");
				}
	%>
</p>
<p>Patients matching these filters:</p>
<table border="1" cellpadding="5" cellspacing="0" class="fTable" id="report">
	<tr>
		<%
			for (DemographicReportFilterType type : DemographicReportFilterType
							.values()) {
						if (type != DemographicReportFilterType.LOWER_AGE_LIMIT
								&& type != DemographicReportFilterType.UPPER_AGE_LIMIT)
							out.println("<th>" + type.toString() + "</th>");
					}
					for (MedicalReportFilterType type : MedicalReportFilterType
							.values()) {
						if (type != MedicalReportFilterType.LOWER_OFFICE_VISIT_DATE
								&& type != MedicalReportFilterType.UPPER_OFFICE_VISIT_DATE)
							out.println("<th>" + type.toString() + "</th>");
						if (type == MedicalReportFilterType.LOWER_OFFICE_VISIT_DATE)
							out.println("<th>OFFICE VISIT DATE</th>");
					}
					for (PersonnelReportFilterType type : PersonnelReportFilterType
							.values()) {
						out.println("<th>" + type.toString() + "</th>");
					}
		%>
	</tr>
	<%
		for (PatientBean patient : report.getPatients()) {
					out.println("<tr>");
					for (DemographicReportFilterType type : DemographicReportFilterType
							.values()) {
						if (type != DemographicReportFilterType.LOWER_AGE_LIMIT
								&& type != DemographicReportFilterType.UPPER_AGE_LIMIT) {
							String val = action
									.getComprehensiveDemographicInfo(
											patient, type);
							if (val != null) {
								out.println("<td><div class='content'>"
										+ val.replace("\n", "<br />")
										+ "</div></td>");
							} else {
								out.println("<td></td>");
							}
						}
					}
					for (MedicalReportFilterType type : MedicalReportFilterType
							.values()) {
						if (type != MedicalReportFilterType.UPPER_OFFICE_VISIT_DATE) {
							String val = action
									.getComprehensiveMedicalInfo(patient,
											type);
							if (val != null) {
								out.println("<td><div class='content'>"
										+ val.replace("\n", "<br />")
										+ "</div></td>");
							} else {
								out.println("<td></td>");
							}
						}
					}
					for (PersonnelReportFilterType type : PersonnelReportFilterType
							.values()) {
						String val = action.getComprehensivePersonnelInfo(
								patient, type);
						if (val != null) {
							out.println("<td><div class='content'>"
									+ val.replace("\n", "<br />") + "</div></td>");
						} else {
							out.println("<td></td>");
						}
					}
					out.println("</tr>");
				}
	%>
</table>
<%
	}
%>
<%
	} else {
%>
<p>
	You have not selected any filters. Please <a href="groupReport.jsp">generate
		a group report</a> first.
</p>
<%
	}
%>

<%@include file="/footer.jsp"%>