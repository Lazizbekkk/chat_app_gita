package com.example.mamadiyorov_lazizbek.chatappgita.ui.screens.enter

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mamadiyorov_lazizbek.chatappgita.R
import com.example.mamadiyorov_lazizbek.chatappgita.data.sourse.data.UserData
import com.example.mamadiyorov_lazizbek.chatappgita.databinding.ScreenEnterBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

class EnterScreen : Fragment(R.layout.screen_enter) {

    private val binding: ScreenEnterBinding by viewBinding(ScreenEnterBinding::bind)
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var userData: UserData

    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    account?.idToken?.let { idToken ->
                        firebaseAuthWithGoogle(idToken)
                    }
                } catch (e: ApiException) {
                    Log.d("EnterScreen", "Google sign-in xatosi: ${e.message}")
                }
            } else {
                Log.d(
                    "EnterScreen",
                    "Google Sign-In muvaffaqiyatsiz bo'ldi, resultCode: ${result.resultCode}"
                )
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Google Sign-In sozlamalarini yaratish
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Google Sign-In tugmasiga bosilganda
        binding.btRegister.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }
    }

    // Google bilan autentifikatsiya qilish
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    user?.let {
                        userData = UserData(
                            userId = user.uid,
                            userName = user.displayName ?: "",
                            userEmail = user.email ?: "",
                            userProfileImage = user.photoUrl?.toString() ?: ""
                        )
                        saveUserDataToFirestore(it)
                    }
                    // Navigatsiya
                    findNavController().navigate(
                        EnterScreenDirections.actionEnterScreenToScreenUserList(
                            userId = userData.userId,
                            userName = userData.userName,
                            userProfileImage = userData.userProfileImage,
                            userEmail = user?.email.toString()
                        )
                    )
                } else {
                    Log.d("EnterScreen", "Autentifikatsiya xatosi: ${task.exception?.message}")
                }
            }
    }

    // Firebase bilan autentifikatsiya qilgandan keyin chaqiriladi
    private fun saveUserDataToFirestore(user: FirebaseUser) {
        val userData = UserData(
            userId = user.uid,
            userName = user.displayName ?: "",
            userEmail = user.email ?: "",
            userProfileImage = user.photoUrl?.toString()
                ?: "" // Profil rasmi URL sifatida saqlanmoqda
        )

        // Firestore instance
        val firestore = FirebaseFirestore.getInstance()

        // Foydalanuvchi ma'lumotlarini Firestore'ga saqlash
        firestore.collection("users").document(user.uid).set(userData)
            .addOnSuccessListener {
                Log.d(
                    "EnterScreen",
                    "Foydalanuvchi ma'lumotlari Firestore'ga muvaffaqiyatli saqlandi."
                )
            }
            .addOnFailureListener { e ->
                Log.d("EnterScreen", "Foydalanuvchi ma'lumotlarini saqlashda xato: ${e.message}")
            }
    }
    // Profil rasmni Glide bilan yuklash

}
