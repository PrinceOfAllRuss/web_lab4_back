package com.webapp.lab4.login;

import com.webapp.lab4.utils.MainController;
import static com.webapp.lab4.utils.TimeBasedOneTimePasswordUtil.generateCurrentNumberString;
import com.webapp.lab4.utils.Tokens;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.webapp.lab4.utils.DatabaseWorker;
import com.webapp.lab4.utils.Hasher;
import com.webapp.lab4.utils.Response;

@CrossOrigin
@RestController
public class LoginController extends MainController {

    DatabaseWorker dbw = new DatabaseWorker();
//    @Autowired
//    DatabaseWorker dbw;
    Hasher hasher = new Hasher();
    @Autowired
    Tokens tokens;

    @PostMapping("/login_user")
    public Response loginUser(@RequestBody HashMap<String, String> data) throws GeneralSecurityException {
        String msg = "";
        boolean condition = false;
        String token = "";
        String browser = data.get("browser");
        String secret = data.get("secret");
        User user = new User(data.get("name"), hasher.hashData(data.get("password")));
        String user_db_secret = dbw.getUserSecret(user.getName());
        if (dbw.getUserForLogin(user)) {
            if (tokens.checkUser(user.getName())) {
                if (tokens.checkBrowser(user.getName(), browser)) {
                    token = this.tokens.getTokenByName(user.getName());
                    msg = "This user already login";
                } else {
                    if (secret.equals(generateCurrentNumberString(user_db_secret))) {
                        this.tokens.addBrowser(user.getName(), browser);
                        token = this.tokens.getTokenByName(user.getName());
                    } else {
                        msg = "Secret isn't valid";
                    }
                }
            } else {
                if (secret.equals(generateCurrentNumberString(user_db_secret))) {
                    token = tokens.createToken();
                    this.tokens.addUser(user.getName(), browser);
                    this.tokens.addToken(user.getName(), token);
                    condition = true;
                    msg = "You're login";
                } else {
                    msg = "Secret isn't valid";
                }
            }
        } else {
            msg = "This user dosen't exist";
        }
        Response response = new Response(msg, condition, token);
        return response;
    }

    @PostMapping("/logout_user")
    public Response exitUser(@RequestBody HashMap<String, String> data) {
        String msg = "";
        boolean condition = false;
        String token = "";
        this.tokens.logoutUser(data.get("token"), data.get("browser"));
        msg = "You're logout";
        condition = true;
        Response response = new Response(msg, condition, token);
        return response;
    }
}
