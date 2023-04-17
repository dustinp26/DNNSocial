// MainActivity.kt
package com.example.dnnsocial

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.dnnsocial.ui.theme.DNNSocialTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DNNSocialTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

                }
            }
        }
    }

    fun onLoginClick(view: View) {
        // get the username and password entered by the user
        val editTextUsername = findViewById<EditText>(R.id.editTextUsername)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val username = editTextUsername.text.toString()
        val password = editTextPassword.text.toString()

        // authenticate the user with their credentials
        authenticateUser(username, password)
    }

    private fun authenticateUser(username: String, password: String) {
        val baseUrl = "https://www.sunnysideconnect.com/"
        val apiService = RetrofitClient.getClient(baseUrl).create(DnnAuthoApiService::class.java)
        val call = apiService.authenticateUser(username, password)

        call.enqueue(object : Callback<DnnAuthoApiService.UserInfo> {
            override fun onResponse(call: Call<DnnAuthoApiService.UserInfo>, response: Response<DnnAuthoApiService.UserInfo>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    if (user != null && user.isAuthenticated) {
                        // authentication successful, do something
                        Toast.makeText(this@MainActivity, "Authentication successful!", Toast.LENGTH_SHORT).show()
                    } else {
                        // authentication failed, show an error message
                        Toast.makeText(this@MainActivity, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // handle the error
                    Toast.makeText(this@MainActivity, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DnnAuthoApiService.UserInfo>, t: Throwable) {
                // handle network error
                Toast.makeText(this@MainActivity, "Network error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}