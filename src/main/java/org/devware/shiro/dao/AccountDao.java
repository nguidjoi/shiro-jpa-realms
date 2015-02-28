/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.devware.shiro.dao;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import net.leadware.persistence.tools.api.dao.constants.DAOMode;
import net.leadware.persistence.tools.api.dao.constants.DAOValidatorEvaluationTime;
import net.leadware.persistence.tools.api.exceptions.JPersistenceToolsException;
import net.leadware.persistence.tools.core.dao.impl.JPAGenericDAORulesBasedImpl;
import org.devware.shiro.dao.Iface.AccountDaoIfaces;
import org.devware.shiro.model.UserAccount;

/**
 * @author NGUIDJOI BELL Alain R. nguibellar@yahoo.fr
 * @since 10 02, 2015
 */
public class AccountDao extends  JPAGenericDAORulesBasedImpl<UserAccount> implements AccountDaoIfaces {
    
    private EntityManager entityManager = Persistence.createEntityManagerFactory(PersistentUnit.Test).createEntityManager(); 
    
    @Override
    public EntityManager getEntityManager() {
		 return this.entityManager;
    }

    @Override
    public Class<UserAccount> getManagedEntityClass() {
                return UserAccount.class;
    }
    
    
    	@Override
	public UserAccount save(UserAccount entity) {
		
		// On retourne l'entite enregistree		
        return save(entity, validateIntegrityConstraintOnSave, preValidateReferentialConstraintOnSave, postValidateReferentialConstraintOnSave);
        }

	/*
	 * (non-Javadoc)
	 * @see net.leadware.persistence.tools.core.dao.JPAGenericDAO#save(java.lang.Object, boolean, boolean, boolean)
	 */
	@Override
	public UserAccount save(UserAccount entity, boolean validateIntegrityConstraint, boolean preValidateReferentialConstraint, boolean postValidateReferentialConstraint) {
		
		// Si l'entite est nulle
		if(entity == null) throw new JPersistenceToolsException("NullEntityException.message");
		
		// Si on doit valider les contraintes d'integrites
		if(validateIntegrityConstraint) validateEntityIntegrityConstraint(entity, DAOMode.SAVE, DAOValidatorEvaluationTime.PRE_CONDITION);
		
		// Si on doit pre-valider les contraintes referentielles
		if(preValidateReferentialConstraint) validateEntityReferentialConstraint(entity, DAOMode.SAVE, DAOValidatorEvaluationTime.PRE_CONDITION);
		
		try {   // Debut de la transaction
                        getEntityManager().getTransaction().begin();
                    
			// Enregistrement
			getEntityManager().persist(entity);
			
                        // Commit de l'enregistrement
                        getEntityManager().getTransaction().commit();
                        
		} catch (Exception e) {
			
			// On relance
			throw new JPersistenceToolsException("jpagenericdaorulesbased.save.error", e);
		}
		
		// Validation de l'entite
		if(postValidateReferentialConstraint) validateEntityReferentialConstraint(entity, DAOMode.SAVE, DAOValidatorEvaluationTime.POST_CONDITION);
		
		// On retourne l'entite enregistree
		return entity;
	}

}
