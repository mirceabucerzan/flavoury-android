package app.flavoury.logging

import timber.log.Timber

/**
 * Release version of the initializer class for the Timber logging library.
 */
class TimberInitializerImpl : TimberInitializer {

    /**
     * Plants a tree for logging in production.
     */
    override fun plantTree() {
        Timber.plant(ReleaseTree())
    }
}