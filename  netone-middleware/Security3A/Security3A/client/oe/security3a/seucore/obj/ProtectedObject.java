/* Generated by Together */

package oe.security3a.seucore.obj;

import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


public class ProtectedObject {
	/** identifier field */
	private String id;

	/** nullable persistent field */
	private String objecttype;

	/** persistent field */
	private String name;

	/** nullable persistent field */
	private String naturalname;

	/** nullable persistent field */
	private String active;

	/** nullable persistent field */
	private String description;

	/** nullable persistent field */
	private Long appid;

	/** nullable persistent field */
	private String parentdir;

	/** nullable persistent field */
	private String actionurl;

	/** nullable persistent field */
	private String ou;

	/** full constructor */
	public ProtectedObject(String objecttype, String name, String naturalname,
			String active, String description, Long appid, String parentdir,
			String actionurl, String ou) {
		this.objecttype = objecttype;
		this.name = name;
		this.naturalname = naturalname;
		this.active = active;
		this.description = description;
		this.appid = appid;
		this.parentdir = parentdir;
		this.actionurl = actionurl;
		this.ou = ou;
	}
	
	public ProtectedObject(){
		
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getObjecttype() {
		return this.objecttype;
	}

	public void setObjecttype(String objecttype) {
		this.objecttype = objecttype;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNaturalname() {
		return this.naturalname;
	}

	public void setNaturalname(String naturalname) {
		this.naturalname = naturalname;
	}

	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getAppid() {
		return this.appid;
	}

	public void setAppid(Long appid) {
		this.appid = appid;
	}

	public String getParentdir() {
		return this.parentdir;
	}

	public void setParentdir(String parentdir) {
		this.parentdir = parentdir;
	}

	public String getActionurl() {
		return this.actionurl;
	}

	public void setActionurl(String actionurl) {
		this.actionurl = actionurl;
	}

	public String getOu() {
		return this.ou;
	}

	public void setOu(String ou) {
		this.ou = ou;
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}

}
