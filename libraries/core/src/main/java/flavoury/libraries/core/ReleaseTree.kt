package flavoury.libraries.core

import timber.log.Timber

/**
 * Timber [timber.log.Timber.Tree] used for logging in production.
 */
internal class ReleaseTree : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        // TODO: set up FirebaseAnalytics
    }
}