/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.devware.shiro.realm;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.devware.shiro.dao.AccountDao;
import org.devware.shiro.dao.Iface.AccountDaoIfaces;
import org.devware.shiro.model.Permissions;
import org.devware.shiro.model.Roles;
import org.devware.shiro.model.UserAccount;

/**
 * @author NGUIDJOI BELL Alain R. nguibellar@yahoo.fr
 * @since 10 02, 2015
 */
public class JpaRealm extends AuthorizingRealm {

    public JpaRealm() {
    }
    
     @Override
    protected AuthenticationInfo  doGetAuthenticationInfo(AuthenticationToken token) 
                throws AuthenticationException {
        
       /*
        * on reupere les information d'authentification du Subject
        */
       UsernamePasswordToken upToken = (UsernamePasswordToken) token;         
        
       /*
        * on recupere le username du Subject
        */
       String username = upToken.getUsername();
       
       /*
        * on recuper le mot de passe du Subject
        */             
       String passwd = upToken.getPassword().toString();
       
       /*
        * si le nom d'utilisateur dun Subject est null ou vide
        */
        if (username == null || username.isEmpty()) {
            throw new AccountException("Null username are not allowed"
                    + " by this realm.");
        }
        
       /*
        * si le mot de passe du Subject est null ou vide
        */
        if (passwd == null || passwd.isEmpty()) {
            throw new AccountException("Null password are not allowed "
                    + "by this realm.");
        }
        
        /*
         * on initialise l' utilisateur;
         */
        UserAccount compte = null;
        
        /*
         * on recupere les information du Subject du datastore
         */       
        try{  
            AccountDaoIfaces users = new AccountDao() ;
            
            compte = users.findByPrimaryKey("username", username, null);
                                                          
        }catch ( Exception exc){
                throw new AuthenticationException("Authentication errors cannot"
                        + " retrieve specified user cause of: "+exc);
        }
        
        // si les information du subject n 'existe pas dans le datastore
        if (compte == null) {
            throw new AccountException("Null usernames are not allowed here \n");
        }
        
        // on recupere le mot de passe
        String password = compte.getPassword();
         
        /*
         * si le mot de passe est null ou vide
         */
        if (password == null || password.isEmpty()) {
                throw new UnknownAccountException("No account found for user "
                        + "[" + username + "]");
        }
       
        /*
         * on initialise les information d'authentification du le datastore
         */
        AuthenticationInfo  simpleInfo = 
                new SimpleAuthenticationInfo(username, password, getName());
        
        /*
         * on retourne les information d authentification
         */ 
        return simpleInfo;
    }
    
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals. fromRealm(getName()).
                iterator().next();
        
         // declaration des utilisateurs;        
         UserAccount account  = null;
         
         //  on recupere les information du compte utilisation
         try{                
                AccountDaoIfaces users = new AccountDao() ;
             
                account = users.findByUniqueProperty("username", username, null);;
                                                          
        }catch ( Exception exc){
                throw new AuthorizationException("Authorization error cannot"
                        + " retrieve specified user Authorised ",exc);
        }
        
        if(account != null) {
            
            // declaration 
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            
            // recuperation des roles
            for(Roles role : account.getRoles()) {
                  info.addRole(role.getName());
                 
                // recuperation des permissions  
                for (Permissions permission : role.getPermissions()){
                        info.addStringPermission(permission.getAction());
                }
            }
            
            // on retourne les info d authorisation
            return info;
        
          // si pas d utilisateur on retourne null
        } else return null;       
    }
    
}
