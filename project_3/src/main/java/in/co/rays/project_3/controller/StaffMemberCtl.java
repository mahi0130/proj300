package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.StaffMemberDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.StaffMemberModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "StaffMemberCtl", urlPatterns = { "/ctl/StaffMemberCtl" })

public class StaffMemberCtl extends BaseCtl{
	
	
	@Override
	protected void preload(HttpServletRequest request) {
		
		Map<Integer, String> map = new HashMap();
		map.put(1, "First");
		map.put(2, "second");
		map.put(3, "third");
		map.put(4, "four");
		map.put(5, "five");
		request.setAttribute("divisionn", map);	
		
			
		 	}
	

	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("identifier"))) {
			request.setAttribute("identifier", PropertyReader.getValue("error.require", "identifier"));
			pass = false;
		}

		
		
		if (DataValidator.isNull(request.getParameter("fullName"))) {
			request.setAttribute("fullName", PropertyReader.getValue("error.require", "fullName"));
			pass = false;
		}

		

		if (DataValidator.isNull(request.getParameter("joiningDate"))) {
			request.setAttribute("joiningDate", PropertyReader.getValue("error.require", " joiningDate"));

			pass = false;
		

		}
		if (DataValidator.isNull(request.getParameter("division"))) {
			request.setAttribute("division", PropertyReader.getValue("error.require", "division"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("previousEmployer"))) {
			request.setAttribute("previousEmployer", PropertyReader.getValue("error.require", "previousEmployer"));
			pass = false;
		}

			  
		 

		return pass;

	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		StaffMemberDTO dto = new StaffMemberDTO();


		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setIdentifier(DataUtility.getLong(request.getParameter("identifier")));

		dto.setFullName(DataUtility.getString(request.getParameter("fullName")));
		dto.setDivision(DataUtility.getString(request.getParameter("division")));
        dto.setJoiningDate(DataUtility.getDate(request.getParameter("joiningDate")));
		dto.setPreviousEmployer(DataUtility.getString(request.getParameter("previousEmployer")));




		populateBean(dto, request);

		return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		StaffMemberModelInt model = ModelFactory.getInstance().getStaffMemberModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			StaffMemberDTO dto;
			try {
				dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (Exception e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		StaffMemberModelInt model = ModelFactory.getInstance().getStaffMemberModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			StaffMemberDTO dto = (StaffMemberDTO) populateDTO(request);
			try {
				if (id > 0) {
					model.update(dto);

					ServletUtility.setSuccessMessage("Data is successfully Update", request);
				} else {

					try {
						model.add(dto);
						ServletUtility.setDto(dto, request);
						ServletUtility.setSuccessMessage("Data is successfully saved", request);
					} catch (ApplicationException e) {
						ServletUtility.handleException(e, request, response);
						return;
					} catch (DuplicateRecordException e) {
						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage("Login id already exists", request);
					}

				}
				
				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			StaffMemberDTO dto = (StaffMemberDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.STAFFMEMBER_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.STAFFMEMBER_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.STAFFMEMBER_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.STAFFMEMBER_VIEW;
	}



}
