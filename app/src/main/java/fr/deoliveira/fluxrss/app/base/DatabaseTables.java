package fr.deoliveira.fluxrss.app.base;

import fr.deoliveira.fluxrss.app.fluxrss.FluxRssDao;

public enum DatabaseTables {

    //fluxrss(FluxRssDao.TABLE_CREATE, FluxRssDao.TABLE_DROP);
    //fluxrss(T
    TEST("test");
    private String  dao;

    DatabaseTables(String dao) {
        this.dao = dao;
    }

    public String getDao() {
        return dao;
    }
}
