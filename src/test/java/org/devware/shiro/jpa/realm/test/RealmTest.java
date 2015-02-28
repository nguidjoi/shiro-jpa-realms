/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.devware.shiro.jpa.realm.test;


import java.util.ArrayList;
import java.util.List;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.devware.shiro.dao.AccountDao;
import org.devware.shiro.model.UserAccount;
import org.devware.shiro.model.Permissions;
import org.devware.shiro.model.Roles;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author NGUIDJOI BELL Alain R. nguibellar@yahoo.fr
 * @since 10 02, 2015
 */
public class RealmTest {
    
    // logger
    private static final transient Logger log = LoggerFactory.getLogger(RealmTest.class);
    
    // security manager factory
    static Factory<org.apache.shiro.mgt.SecurityManager> factor;
    
    // security manager
    static org.apache.shiro.mgt.SecurityManager securityManager;
    
    // username and password token 
    UsernamePasswordToken token;
    
    // subject
    Subject currentUser;
    
    // session
    Session session;
    
    public RealmTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
        // Initialisation de la fabrique du gestionnaire des securité
       factor = new IniSecurityManagerFactory("classpath:shiro.ini");
       
       // creation du gestionnaire de securité
       securityManager = factor.getInstance();
       
       // initialisation du gestionnaire de securité
       SecurityUtils.setSecurityManager(securityManager);
       
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
           
        
       // get the currently executing user:
       currentUser = SecurityUtils.getSubject();
       
       // initialisation des informations de securité
       UserAccount account = new UserAccount();
        account.setUsername("BELL");
        account.setPassword("ALAIN ROGER");
       List<Roles> role = new ArrayList<Roles>();
        
       // initialisation des permissions et des roles
       List<Permissions> permission = new ArrayList<Permissions>();
       permission.add(new Permissions("create:soft"));
       Roles adminRole = new Roles("Admin", "Role des adminnistrateurs");
        
       adminRole.setPermissions(permission);
       role.add(adminRole);
       
       // initialisation des informations de securité de l utilisateur
       account.setRoles(role);
               
         try {  
                 
                // sauvegarde des informations de securité en base de donnée                
                (new AccountDao()).save(account,false,false,false);
                 
                                               
        } catch (Exception ex) {
                System.out.print(" impossible de creer l utilisateur " + ex);           
        }
         // initialisation des information d'authentification
         token = new UsernamePasswordToken("BELL","ALAIN ROGER");
         
         token.setRememberMe(true);
    }
    
    @After
    public void tearDown() {
        
        // deconnexion de l utilisateur
        currentUser.logout();
    }
    
   @Test
   public void Main() { 
       
        // session retrieve
        session = currentUser.getSession();
               
        // session initialize
        session.setAttribute("key", "keyValue");
                    
            try {
                    // Authentification de l'utilisateur
                    currentUser.login(token);
                    
            } 
            // gestion de l exception en cas de username inconnu
            catch (UnknownAccountException uae) {
                       
                        log.info("There is no user with username of " + token.getPrincipal()+" "+uae);
            }
            // gestion de l'exception en cas de credential (mot de passse) inconnu
            catch (IncorrectCredentialsException ice) {
                       
                        log.info("Password for account " + token.getPrincipal() + " was incorrect! "+ice);
            } 
            // gestion de l exception en  cas de compte verrouillé
            catch (LockedAccountException lae) {
                        log.info("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it. "+lae);
            }
            // gestion de lexception en cas d'impossibilité  d 'authentification
            catch (AuthenticationException ae) {
                log.info("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it. "+ " "+ae);
            }

            // test de l'authentification
            assertTrue (currentUser.isAuthenticated());
       
            // test du role:
            assertTrue (currentUser.hasRole("Admin"));
       
            // test des permissions
            assertTrue(currentUser.isPermitted("create:soft"));
              
   }
}
