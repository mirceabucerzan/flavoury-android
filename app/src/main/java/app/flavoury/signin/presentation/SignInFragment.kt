package app.flavoury.signin.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import app.flavoury.R
import app.flavoury.core.AlertDialogFragment
import app.flavoury.core.UniqueEventObserver
import app.flavoury.core.getViewModel
import app.flavoury.databinding.FragmentSignInBinding
import app.flavoury.signin.provideSignInViewModelFactory

/**
 * View that displays the sign in UI, forwards user events to its associated
 * ViewModel and reacts to the navigation/error events held by it.
 */
class SignInFragment : Fragment() {

    companion object {
        private const val START_ACTIVITY_REQUEST_CODE = 7
    }

    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentSignInBinding = FragmentSignInBinding.inflate(inflater, container, false)
        viewModel = getViewModel(provideSignInViewModelFactory(requireContext()))
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigateForResultEvent.observe(this, UniqueEventObserver { intent ->
            intent?.let { startActivityForResult(it, START_ACTIVITY_REQUEST_CODE) }
        })

        viewModel.navigateEvent.observe(this, UniqueEventObserver { intent ->
            intent?.let { startActivity(it) }
        })

        viewModel.errorEvent.observe(this, UniqueEventObserver {
            val errorAlert: DialogFragment = AlertDialogFragment.newInstance(
                titleId = R.string.sign_in_error_title,
                messageId = R.string.sign_in_error_message,
                positiveBtnId = R.string.sign_in_error_button_text
            )
            errorAlert.show(requireFragmentManager(), null)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == START_ACTIVITY_REQUEST_CODE) {
            viewModel.navigationResultReceived(data)
        }
    }

}