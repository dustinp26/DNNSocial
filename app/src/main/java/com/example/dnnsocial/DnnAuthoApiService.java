package com.example.dnnsocial;

// DnnAuthApiService.java
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DnnAuthoApiService {
    @GET("api/Auth/AuthenticateUser")
    Call<UserInfo> authenticateUser(@Query("username") String username, @Query("password") String password);

    // Replace the endpoint in the @GET annotation with the appropriate endpoint for your DNN authentication API.

    class UserInfo {
        private boolean isAuthenticated;
        private String username;

        public boolean isAuthenticated() {
            return isAuthenticated;
        }

        public void setAuthenticated(boolean authenticated) {
            isAuthenticated = authenticated;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}