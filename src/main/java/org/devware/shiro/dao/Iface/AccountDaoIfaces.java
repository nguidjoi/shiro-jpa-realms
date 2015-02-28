/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.devware.shiro.dao.Iface;

import net.leadware.persistence.tools.core.dao.JPAGenericDAO;
import org.devware.shiro.model.UserAccount;

/**
 * @author NGUIDJOI BELL Alain R. nguibellar@yahoo.fr
 * @since 10 02, 2015
 */
public interface AccountDaoIfaces  extends JPAGenericDAO<UserAccount> {
    
    /**
     * Nom du service DAO
     */
    public static final String SERVICE_NAME = "AccountDao";
}
