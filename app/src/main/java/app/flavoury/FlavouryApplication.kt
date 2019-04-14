package app.flavoury

import android.app.Application
import app.flavoury.core.logging.TimberInitializerImpl

/**
 * Application class used to perform global initialization:
 * - logging
 */
class FlavouryApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        TimberInitializerImpl().plantTree()
    }

}