package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.ShopDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class ShopModelHibImp implements ShopModelInt{
	
	@Override
	public long add(ShopDTO dto) throws ApplicationException, DuplicateRecordException {
		
		 ShopDTO existDto = null;
			
			Session session = HibDataSource.getSession();
			Transaction tx = null;
			try {

				tx = session.beginTransaction();

				session.save(dto);

				dto.getId();
				tx.commit();
			} catch (HibernateException e) {
				e.printStackTrace();
				if (tx != null) {
					tx.rollback();

				}
				throw new ApplicationException("Exception in Order Add " + e.getMessage());
			} finally {
				session.close();
			}

		
		return dto.getId();
	}

	@Override
	public void delete(ShopDTO dto) throws ApplicationException {
		
		
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Order Delete" + e.getMessage());
		} finally {
			session.close();
		}

	}

	@Override
	public void update(ShopDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		/*
		 * Transaction tx = null; ShopDTO exesistDto = findByLogin(dto.getLogin());
		 * 
		 * if (exesistDto != null && exesistDto.getId() != dto.getId()) { throw new
		 * DuplicateRecordException("Login id already exist"); }
		 */
		Transaction tx = null;

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(dto);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Order update" + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public ShopDTO findByPK(long pk) throws ApplicationException {
		
		Session session = null;
		ShopDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (ShopDTO) session.get(ShopDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting Product by pk");
		} finally {
			session.close();
		}


		return dto;
	}

	@Override
	public ShopDTO findByLogin(String login) throws ApplicationException {
		
		
		Session session = null;
		ShopDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ShopDTO.class);
			criteria.add(Restrictions.eq("login", login));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (ShopDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting Order by Login " + e.getMessage());

		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {
		

		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ShopDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in  Order list");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(ShopDTO dto, int pageNo, int pageSize) throws ApplicationException {
		
		Session session = null;
		ArrayList<ShopDTO> list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ShopDTO.class);
			if (dto != null) {
				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getCustomerName() != null && dto.getCustomerName().length() > 0) {
					criteria.add(Restrictions.like("status", dto.getCustomerName() + "%"));
				}
				
				  if (dto.getAddress() != null && dto.getAddress().length() > 0) {
					  criteria.add(Restrictions.like("title", dto.getAddress() + "%"));
					}
				  
				  if ( dto.getAmount() > 0) {
					  criteria.add(Restrictions.like("experience", dto.getAmount() + "%"));
					  }

			  
				  if ( dto.getPhone() > 0) {
					  criteria.add(Restrictions.like("experience", dto.getPhone() + "%"));
					  }
				  
				  if ( dto.getVersion() > 0) {
					  criteria.add(Restrictions.like("experience", dto.getVersion() + "%"));
					  }
				  
				  if (dto.getOrder() != null && dto.getOrder().length() > 0) {
					  criteria.add(Restrictions.like("title", dto.getOrder() + "%"));
					}
				  

				  if (dto.getRegisterDate()!= null && dto.getRegisterDate().getDate() > 0) {
					  criteria.add(Restrictions.like("title", dto.getRegisterDate() + "%"));
					}
			  
			}
			
			System.out.println("searchcalll");
			// if pageSize is greater than 0
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = (ArrayList<ShopDTO>) criteria.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Order search");
		} finally {
			session.close();
		}


		
		return list;
	}

	@Override
	public List search(ShopDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getRoles(ShopDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	



}
