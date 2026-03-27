package jm.task.core.jdbc.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Util {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("preproject113PU");

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
}
