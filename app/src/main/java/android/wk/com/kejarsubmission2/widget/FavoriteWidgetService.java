package android.wk.com.kejarsubmission2.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

public class FavoriteWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d("widgetFav","InService!");
        return new StackRemoteViewsFactory(getApplicationContext());
    }
}
