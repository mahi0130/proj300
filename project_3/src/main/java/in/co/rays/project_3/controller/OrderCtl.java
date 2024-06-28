package in.co.rays.project_3.controller;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.OrderDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.OrderModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.RoleModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * user functionality controller.to perform add,delete and update operation
 * 
 * @author Mahi singh
 *
 */
@WebServlet(urlPatterns = { "/ctl/OrderCtl" })
public class OrderCtl extends BaseCtl {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/** The log. */
	/*
	 * private static Logger log = Logger.getLogger(OrderCtl.class);
	 */
	protected void preload(HttpServletRequest request) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("Hp", "Hp");
		map.put("Dell","Dell");
		map.put("Lenevo", "Lenevo");
		map.put("acer", "acer");
		request.setAttribute("map", map);
		}

	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("CustomerName"))) {
			request.setAttribute("CustomerName", PropertyReader.getValue("error.require", "CustomerName"));
			System.out.println(pass);
			pass = false;
		} else if(request.getParameter("CustomerName").length()>20) {
			request.setAttribute("CustomerName", "Name must contains only 20 alphabets");
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("address"))) {
			request.setAttribute("address", PropertyReader.getValue("error.require", "Address"));
			System.out.println(pass);
			pass = false;
		} 
			if (DataValidator.isNull(request.getParameter("phoneNo"))) {
				request.setAttribute("phoneNo", PropertyReader.getValue("error.require", "phoneNo"));
				pass = false;
		} 
		
			if (DataValidator.isNull(request.getParameter("version"))) {
				request.setAttribute("version", PropertyReader.getValue("error.require", "version"));
				System.out.println(pass);
				pass = false;
			}
			
		if (DataValidator.isNull(request.getParameter("product"))) {
			request.setAttribute("product", PropertyReader.getValue("error.require", "product"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("amount"))) {
			request.setAttribute("amount", PropertyReader.getValue("error.require", "amount"));
			pass = false;
		}
		
		
		return pass;
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		OrderDTO dto = new OrderDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setCustomerName(DataUtility.getString(request.getParameter("CustomerName")));
		dto.setAddress(DataUtility.getString(request.getParameter("address")));
		dto.setPhoneNo(DataUtility.getLong(request.getParameter("phoneNo")));
		dto.setProduct(DataUtility.getString(request.getParameter("product")));
		dto.setAmount(DataUtility.getLong(request.getParameter("amount")));
		try {
		dto.setVersion(Double.parseDouble((request.getParameter("version"))));
		}catch(NumberFormatException e){
			System.out.println("invalid vaersion");
		}
		populateBean(dto, request);

	return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		/*
		 * log.debug("OrderCtl Method doGet Started");
		 */		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		OrderModelInt model = ModelFactory.getInstance().getOrderModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			System.out.println("in id > 0  condition");
			OrderDTO dto;
			try {
				dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (Exception e) {
				e.printStackTrace();
				/*
				 * log.error(e);
				 */				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		
		OrderModelInt model = ModelFactory.getInstance().getOrderModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			OrderDTO dto = (OrderDTO) populateDTO(request);
			System.out.println(" in do post method jkjjkjk++++++++" + dto.getId());
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
						/*
						 * log.error(e);
						 */						ServletUtility.handleException(e, request, response);
						return;
					} catch (DuplicateRecordException e) {
						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage("Title already exists", request);
					}

				}
				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				/*
				 * log.error(e);
				 */				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Title already exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			OrderDTO dto = (OrderDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.ORDER_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				/*
				 * log.error(e);
				 */				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.ORDER_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.ORDER_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

		/*
		 * log.debug("JobCtl Method doPostEnded");
		 */	}

	@Override
	protected String getView() {
		return ORSView.ORDER_VIEW;
	}

}