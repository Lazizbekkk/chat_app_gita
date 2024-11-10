package com.example.mamadiyorov_lazizbek.chatappgita.ui.screens.enter

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mamadiyorov_lazizbek.chatappgita.R
import com.example.mamadiyorov_lazizbek.chatappgita.databinding.ScreenEnterBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class EnterScreen : Fragment(R.layout.screen_enter) {

    private val binding: ScreenEnterBinding by viewBinding(ScreenEnterBinding::bind)

    private lateinit var googleSignInClient: GoogleSignInClient
    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.d(
                "EnterScreen", "Google Sign-In natijasi olindi, kod: ${result.resultCode}"
            )  // Google Sign-In natijasi haqida log
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    if (account != null) {
                        Log.d(
                            "com.example.mamadiyorov_lazizbek.chatappgita.ui.screens.enter.EnterScreen",
                            "Google hisobiga kirildi: ${account.email}"
                        ) // Foydalanuvchi hisobining emaili
                        account.idToken?.let { idToken ->
                            // Firebase bilan Google autentifikatsiyasi
                            firebaseAuthWithGoogle(idToken)
                        } ?: Log.d("com.example.mamadiyorov_lazizbek.chatappgita.ui.screens.enter.EnterScreen", "Google sign-in xato, idToken null")
                    }
                } catch (e: ApiException) {
                    Log.d("com.example.mamadiyorov_lazizbek.chatappgita.ui.screens.enter.EnterScreen", "Google sign-in xatosi: ${e.message}")
                    Log.d(
                        "com.example.mamadiyorov_lazizbek.chatappgita.ui.screens.enter.EnterScreen",
                        "Google sign-in xatosi: ${e.message}"
                    ) // Google sign-in xatosi haqida batafsil log
// Xatolik haqida log
                }
            } else {
                Log.d(
                    "com.example.mamadiyorov_lazizbek.chatappgita.ui.screens.enter.EnterScreen",
                    "Google Sign-In muvaffaqiyatsiz bo'ldi, resultCode: ${result.resultCode}"
                ) // Google Sign-In muvaffaqiyatsiz bo'lganda
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("com.example.mamadiyorov_lazizbek.chatappgita.ui.screens.enter.EnterScreen", "com.example.mamadiyorov_lazizbek.chatappgita.ui.screens.enter.EnterScreen fragmenti yaratildi")  // Fragment yaratish haqida log
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("com.example.mamadiyorov_lazizbek.chatappgita.ui.screens.enter.EnterScreen", "com.example.mamadiyorov_lazizbek.chatappgita.ui.screens.enter.EnterScreen view yaratildi")  // View yaratish haqida log

        // Google Sign-In uchun sozlamalarni yaratish
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // Firebase'dan olingan client ID
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)

        binding.inputUserNickName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Log.d(
                    "com.example.mamadiyorov_lazizbek.chatappgita.ui.screens.enter.EnterScreen",
                    "NickName o'zgarganidan keyin: ${s.toString()}"
                )  // Nickname o'zgarganidan keyin
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d(
                    "com.example.mamadiyorov_lazizbek.chatappgita.ui.screens.enter.EnterScreen",
                    "NickName o'zgarmasdan oldin: ${s.toString()}"
                )  // Nickname o'zgarmasdan oldin
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d(
                    "com.example.mamadiyorov_lazizbek.chatappgita.ui.screens.enter.EnterScreen",
                    "NickName o'zgarayotganda: ${s.toString()}"
                )  // Nickname o'zgarayotganda
            }
        })

        // Google Sign-In tugmasiga bosilganda
        binding.btRegister.setOnClickListener {
            Log.d("com.example.mamadiyorov_lazizbek.chatappgita.ui.screens.enter.EnterScreen", "Register tugmasi bosildi") // Register tugmasi bosilganda log
            val signInIntent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }
    }

    // Google bilan autentifikatsiya qilish
    private fun firebaseAuthWithGoogle(idToken: String) {
        Log.d(
            "com.example.mamadiyorov_lazizbek.chatappgita.ui.screens.enter.EnterScreen",
            "Firebase bilan autentifikatsiya boshlanmoqda, Google ID tokeni ishlatilmoqda"
        ) // Firebase bilan autentifikatsiya boshlanishi haqida log
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(
                        "com.example.mamadiyorov_lazizbek.chatappgita.ui.screens.enter.EnterScreen",
                        "Autentifikatsiya muvaffaqiyatli bo'ldi, foydalanuvchi tizimga kirdi"
                    ) // Muvaffaqiyatli autentifikatsiya haqida log
                    // Autentifikatsiya muvaffaqiyatli bo'lsa
                    findNavController().navigate(EnterScreenDirections.actionEnterScreenToScreenUserList())
                } else {
                    // Xatolik yuzaga kelsa
                    Log.d(
                        "com.example.mamadiyorov_lazizbek.chatappgita.ui.screens.enter.EnterScreen",
                        "Autentifikatsiya xatosi: ${task.exception?.message}"
                    ) // Xato haqida log
                }
            }
    }
}
