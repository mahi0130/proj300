package in.co.rays.project_3.model;

import java.util.List;
import in.co.rays.project_3.dto.ShopDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface ShopModelInt {
	
	public long add(ShopDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(ShopDTO dto)throws ApplicationException;
	public void update(ShopDTO dto)throws ApplicationException,DuplicateRecordException;
	public ShopDTO findByPK(long pk)throws ApplicationException;
	public ShopDTO findByLogin(String login)throws ApplicationException;
	public List list()throws ApplicationException;
	public List list(int pageNo,int pageSize)throws ApplicationException;
	public List search(ShopDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public List search(ShopDTO dto)throws ApplicationException;
	public List getRoles(ShopDTO dto)throws ApplicationException;
	

}
