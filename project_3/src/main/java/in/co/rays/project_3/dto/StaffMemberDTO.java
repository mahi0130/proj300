
package in.co.rays.project_3.dto;

import java.util.Date;

public class StaffMemberDTO extends BaseDTO{
	
	private long identifier;
	private String fullName;
	private Date joiningDate;
	private String division;
	private String previousEmployer;
	
		public long getIdentifier() {
		return identifier;
	}
	public void setIdentifier(long identifier) {
		this.identifier = identifier;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Date getJoiningDate() {
		return joiningDate;
	}
	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getPreviousEmployer() {
		return previousEmployer;
	}
	public void setPreviousEmployer(String previousEmployer) {
		this.previousEmployer = previousEmployer;
	}
		@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
