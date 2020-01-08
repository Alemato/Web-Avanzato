package it.mytutor.business.services;

import it.mytutor.business.exceptions.ChatBusinessException;
import it.mytutor.domain.Creates;

import java.util.List;

public interface CreatesInterface {
    void createCreates(Integer idUser1, String chatNames, Integer idUser2) throws ChatBusinessException;

    List<Creates> getCreatesByIdUser(int idUser) throws ChatBusinessException;
}
