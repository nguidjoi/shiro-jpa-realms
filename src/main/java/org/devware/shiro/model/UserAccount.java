package org.devware.shiro.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.util.ByteSource;



/**
 * @author NGUIDJOI BELL Alain R. nguibellar@yahoo.fr
 * @since 10 02, 2015
 */
@Entity
@Table(name = "SEC_ACCOUNT")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserAccount  extends BaseEntity {
    
	private static final long serialVersionUID = 5274477345750145639L;       
	@Id       
	@Column(name = "USERNAME", length = 250)
	private String username;

	@Column(name = "PASSWORD", length = 250)
	private String password;

	@Column(name = "SALT")
	private String salt;
        
        @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
        @JoinTable(name = "USER_ROLES", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID")) 
	private List<Roles> roles = new ArrayList<Roles>();
        
        public UserAccount() {
        
        }
        
        public UserAccount(String username, String password){
                      
            this.password = password ;
            
            //             
            this.username = username;
                     
        }
        
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

        public String getSalt() {
                return salt;
        }

        public void setSalt(String salt) {
                this.salt = salt;
        }
                
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
        
        public List<Roles> getRoles() {
                return roles;
        }

        public void setRoles(List<Roles> roles) {
                this.roles = roles;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 17 * hash + (this.username != null ? this.username.hashCode() : 0);
            hash = 17 * hash + (this.password != null ? this.password.hashCode() : 0);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserAccount other = (UserAccount) obj;
        if ((this.username == null) ? (other.username != null) : !this.username.equals(other.username)) {
            return false;
        }
        if ((this.password == null) ? (other.password != null) : !this.password.equals(other.password)) {
            return false;
        }
        return true;
    }
        
        
}
