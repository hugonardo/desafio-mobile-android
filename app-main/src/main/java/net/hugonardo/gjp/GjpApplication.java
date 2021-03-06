package net.hugonardo.gjp;

import com.squareup.leakcanary.LeakCanary;

import net.hugonardo.gjp.di.DaggerApplicationInjector;
import net.hugonardo.gjp.tools.CrashlyticsTree;

import dagger.android.AndroidInjector;
import timber.log.Timber;

public class GjpApplication extends GjpFabricApplication {

    @Override protected AndroidInjector<? extends GjpFabricApplication> applicationInjector() {
        return DaggerApplicationInjector.builder().create(this);
    }

    @Override public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashlyticsTree());
        }
    }
}
