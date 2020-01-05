package it.mytutor.business.impl;

import it.mytutor.business.services.CreatesInterface;
import it.mytutor.domain.Creates;
import it.mytutor.domain.dao.exception.DatabaseException;
import it.mytutor.domain.dao.implement.CreatesDao;
import it.mytutor.domain.dao.interfaces.CreatesDaoInterface;

public class CreatesBusiness implements CreatesInterface {

    @Override
    public void createCreates(Creates creates) {
        CreatesDaoInterface createsDao = new CreatesDao();
        try {
            createsDao.createCreates(creates);
        } catch (DatabaseException e) {
            e.printStackTrace();

        }

    }

    @Override
    public Creates getCreates(int id) {
        Creates creates = null;
        return creates;
    }
}
