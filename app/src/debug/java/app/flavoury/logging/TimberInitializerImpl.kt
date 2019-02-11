package app.flavoury.logging

import timber.log.Timber


/**
 * Debug version of the initializer class for the Timber logging library.
 */
class TimberInitializerImpl : TimberInitializer {

    /**
     * Plants a tree for logging during debugging.
     */
    override fun plantTree() {
        Timber.plant(Timber.DebugTree())
    }
}