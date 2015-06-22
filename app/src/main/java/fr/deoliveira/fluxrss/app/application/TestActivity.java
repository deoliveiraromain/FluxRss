package fr.deoliveira.fluxrss.app.application;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import fr.deoliveira.fluxrss.app.R;
import fr.deoliveira.fluxrss.app.fluxrss.FluxRss;
import fr.deoliveira.fluxrss.app.fluxrss.FluxRssDao;
import fr.deoliveira.fluxrss.app.fluxrss.TypeInfo;

import java.util.List;

/**
 * Created by Romain on 21/06/2015.
 */
public class TestActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);

        Button test = (Button) findViewById(R.id.buttonDb);
        Button update = (Button) findViewById(R.id.buttonDbUpdate);
        Button getAll = (Button) findViewById(R.id.buttonDbgetAll);


        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FluxRssDao dao = new FluxRssDao(v.getContext());
                dao.open();
                FluxRss newFlux = new FluxRss("google", "http://www.google.fr", TypeInfo.INTERNATIONAL);
                long id = dao.ajouter(newFlux);
                dao.close();
                Log.d("testInsert", id + "");
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FluxRssDao dao = new FluxRssDao(v.getContext());
                dao.open();
                FluxRss flux = dao.selectionnerWithAuteur("google");
                Log.d("testUpdateAvant", "" + flux.getAuteur() + "");
                flux.setUrl("http://wwww.yahoo.fr");
                flux.setAuteur("yahoo");
                long id = flux.getId();

                dao.modifier(flux);
                flux = dao.selectionner(id);
                Log.d("testUpdateApres", "" + flux.getAuteur() + "");
            }
        });

        getAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FluxRssDao dao = new FluxRssDao(v.getContext());
                dao.open();
                List<FluxRss> listeflux = dao.getAllFlux();
                for (FluxRss flux : listeflux) {
                    Log.d("testGetAll", "" + flux.getAuteur() + "");
                }
            }
        });
    }
}
