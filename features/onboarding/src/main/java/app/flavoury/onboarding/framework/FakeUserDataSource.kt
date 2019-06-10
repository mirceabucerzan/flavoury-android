package app.flavoury.onboarding.framework

import app.flavoury.onboarding.datasource.UserDataSource
import com.mirceabucerzan.core.domain.User

class FakeUserDataSource : UserDataSource {

    override suspend fun getUser(uid: String): User? = null

}