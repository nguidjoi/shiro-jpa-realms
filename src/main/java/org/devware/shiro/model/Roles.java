/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.devware.shiro.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author NGUIDJOI BELL Alain R. nguibellar@yahoo.fr
 * @since 10 02, 2015
 */
@Entity
@Table(name = "SEC_ROLES")
public class Roles implements Serializable {
    
	private static final long serialVersionUID = 6142315693769197546L;

        public Roles() {
        }

        public Roles(String name, String description) {
            this.name = name;
            this.description = description;
        }
      
        @Id
	@Column(name = "NAME", nullable = false, length = 350)
	private String name;

	@Column(name = "DESCRIPTION", nullable = false, length = 50)
	private String description;        
       
        @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	@JoinTable(name = "ROLE_PERMISSION", joinColumns = @JoinColumn(name = "ROLE_ID"), inverseJoinColumns = @JoinColumn(name = "PERMISSION_ID"))
	private List<Permissions> permissions = new ArrayList<Permissions>();
        
        @ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "USER_ROLES", joinColumns = @JoinColumn(name = "ROLE_ID"), inverseJoinColumns = @JoinColumn(name = "USER_ID"))	
    	private List<UserAccount> users = new ArrayList<UserAccount>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
        
        public List<UserAccount> getUsers() {
                return users;
        }

        public void setUsers(List<UserAccount> users) {
                this.users = users;
        }
	
        public List<Permissions> getPermissions() {
                return permissions;
        }

        public void setPermissions(List<Permissions> permissions) {
                this.permissions = permissions;
        }
                
        
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this == obj)
			return true;

		if (getClass() != obj.getClass())
			return false;
		Roles other = (Roles) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}