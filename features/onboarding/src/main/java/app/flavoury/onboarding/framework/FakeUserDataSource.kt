package app.flavoury.onboarding.framework

import app.flavoury.onboarding.datasource.UserDataSource
import flavoury.libraries.core.domain.User

internal class FakeUserDataSource : UserDataSource {

    override suspend fun getUser(uid: String): User? = null

}