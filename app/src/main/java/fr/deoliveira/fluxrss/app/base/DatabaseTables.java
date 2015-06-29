package fr.deoliveira.fluxrss.app.base;

import fr.deoliveira.fluxrss.app.fluxrss.FluxRssDao;

/**
 * Created by Romain on 21/06/2015.
 */
public enum DatabaseTables {

    fluxrss(FluxRssDao.TABLE_CREATE, FluxRssDao.TABLE_DROP);

    private String create;
    private String drop;

    DatabaseTables(String create, String drop) {
        this.create = create;
        this.drop = drop;
    }

    public String getCreate() {
        return create;
    }

    public String getDrop() {
        return drop;
    }
}
