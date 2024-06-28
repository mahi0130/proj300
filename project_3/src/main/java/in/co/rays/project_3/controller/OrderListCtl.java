
package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.OrderDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.OrderModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.RoleModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = " OrderListCtl", urlPatterns = { "/ctl/OrderListCtl" })
public class OrderListCtl extends BaseCtl {

	protected void preload(HttpServletRequest request) {
		OrderModelInt cmodel = ModelFactory.getInstance().getOrderModel();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("Hp", "Hp");
		map.put("Dell","Dell");
		map.put("Lenevo", "Lenevo");
		map.put("acer", "acer");
		

		request.setAttribute("map", map);
			
	}
	
	
	
	
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		OrderDTO dto = new OrderDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setCustomerName(DataUtility.getString(request.getParameter("CustomerName")));
		dto.setAddress(DataUtility.getString(request.getParameter("address")));
		dto.setPhoneNo(DataUtility.getLong(request.getParameter("phoneNo")));
		dto.setProduct(DataUtility.getString(request.getParameter("product")));
		/*
		 * dto.setVersion(DataUtility.getDouble(request.getParameter("double")));
		 */	
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
			throws ServletException, IOException {
		
		System.out.println("chal gai reeee.......");
		List list;
		List next;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		OrderDTO dto = new OrderDTO();

		OrderModelInt model = ModelFactory.getInstance().getOrderModel();
		try {
			list = model.search(dto, pageNo, pageSize);

			ArrayList<OrderDTO> a = (ArrayList<OrderDTO>) list;

			next = model.search(dto, pageNo + 1, pageSize);
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			if (next == null || next.size() == 0) {
				request.setAttribute("nextListSize", 0);

			} else {
				request.setAttribute("nextListSize", next.size());
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			ServletUtility.handleException(e, request, response);
			return;
		} catch (Exception e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		List list = null;
		List next = null;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		OrderDTO dto = (OrderDTO) populateDTO(request);

		String[] ids = request.getParameterValues("ids");
		OrderModelInt model = ModelFactory.getInstance().getOrderModel();
		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
					String CustomerName = request.getParameter("CustomerName");
					String product = request.getParameter("product");
					String address = request.getParameter("address");
					String phoneNo = request.getParameter("phoneNo");
					String amount = request.getParameter("amount");
					String version = request.getParameter("version");

					if (CustomerName.equals("") && product.equals("") && address.equals("") && version.equals("") && phoneNo.equals("") && amount.equals("")) {

						ServletUtility.setErrorMessage("Pleace fill at least on search feald", request);
					}

				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.ORDER_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.ORDER_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					OrderDTO deletedto = new OrderDTO();
					for (String id : ids) {
						deletedto.setId(DataUtility.getLong(id));
						model.delete(deletedto);
						ServletUtility.setSuccessMessage("Data Deleted Successfully", request);
					}
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			}
			if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.ORDER_LIST_CTL, request, response);
				return;
			}
			dto = (OrderDTO) populateDTO(request);
			list = model.search(dto, pageNo, pageSize);

			ServletUtility.setDto(dto, request);
			next = model.search(dto, pageNo + 1, pageSize);

			ServletUtility.setList(list, request);
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				if (!OP_DELETE.equalsIgnoreCase(op)) {
					ServletUtility.setErrorMessage("No record found ", request);
				}
			}
			if (next == null || next.size() == 0) {
				request.setAttribute("nextListSize", 0);

			} else {
				request.setAttribute("nextListSize", next.size());
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			ServletUtility.handleException(e, request, response);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected String getView() {
		return ORSView.ORDER_LIST_VIEW;
	}

}
