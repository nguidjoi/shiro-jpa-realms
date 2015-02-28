/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.devware.shiro.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author NGUIDJOI BELL Alain R. nguibellar@yahoo.fr
 * @since 10 02, 2015
 */
@Entity
@Table ( name = "SEC_PERMISSION")
public class Permissions extends BaseEntity {
            
	private static final long serialVersionUID = -2844386098501951453L;
        
        
        @Id
	@Column(name = "ACTIONS", nullable = false, length = 1500)
	private String action;
        
        
        public Permissions() {
        
        }

        public Permissions(String action) {
                this.action = action;
        }
	
       	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}
