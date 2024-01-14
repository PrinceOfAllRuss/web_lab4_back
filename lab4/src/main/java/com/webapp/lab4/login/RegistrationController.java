package com.webapp.lab4.login;

import com.webapp.lab4.utils.MainController;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.webapp.lab4.utils.DatabaseWorker;
import com.webapp.lab4.utils.Hasher;
import com.webapp.lab4.utils.Response;
import static com.webapp.lab4.utils.TimeBasedOneTimePasswordUtil.generateBase32Secret;

@CrossOrigin
@RestController
public class RegistrationController extends MainController {
    DatabaseWorker dbw = new DatabaseWorker();
    Hasher hasher = new Hasher();
    
    @PostMapping("/register_user")
    public Response registerUserByDriverManager(@RequestBody HashMap<String, String> data) throws NoSuchAlgorithmException {
        String msg = "";
        boolean condition = false;
        String token = "";
        User user = new User(data.get("name"), hasher.hashData(data.get("password")));
        if (!dbw.getUserForRegistration(user)) {
            String secret = generateBase32Secret();
            user.setSecret(secret);
            dbw.registerUser(user);
            msg = secret;
            condition = true;
        } else {
            msg = "This user already exist";
        }
        Response response = new Response(msg, condition, token);
        return response;
    }
}
