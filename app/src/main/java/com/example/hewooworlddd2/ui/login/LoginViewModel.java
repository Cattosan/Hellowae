package com.example.hewooworlddd2.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.example.hewooworlddd2.data.LoginRepository;
import com.example.hewooworlddd2.data.Result;
import com.example.hewooworlddd2.data.model.LoggedInUser;
import com.example.hewooworlddd2.R;

public class LoginViewModel extends ViewModel {

    String users;
    String passer;

    public void setUsers() {
        this.users = "admin";
        this.passer = "admin";
    }

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(username, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void loginDataChanged(String username, String password) {
        if (isUserNameValid(username) && isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(true));
        }
        else {

        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username != users){
            return false;
        }
        else{
            return true;
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        if (password != passer){
            return false;
        }
        else{
            return true;
        }
    }
}