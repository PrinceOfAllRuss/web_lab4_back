package com.webapp.lab4.home;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.webapp.lab4.utils.MainController;
import com.webapp.lab4.utils.Tokens;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import com.webapp.lab4.utils.Response;
import com.webapp.lab4.utils.Validator;

@CrossOrigin
@RestController
public class ResultController extends MainController {

    private ExecutorService executors = Executors.newFixedThreadPool(3);
    Validator validator = new Validator();
    @Autowired
    Tokens tokens;
    @Autowired
    ResultElementRepository resultElementRepository;

    @PostMapping("/check_result")
    public Response checkResult(@RequestBody HashMap<String, String> data) {
        String msg = "All ok";
        boolean condition = false;
        String token = "";
        String browser = data.get("browser");
        ClientPoint point = new ClientPoint(data.get("x"), data.get("y"), data.get("r"), data.get("method"), data.get("token"));
        if (!tokens.checkToken(point.getToken())) {
            msg = "You're not login";
        } else {
            if (tokens.checkTimeOfSession(point.getToken(), browser)) {
                msg = "Session time is out";
            } else {
                point.setY(point.getY().replace(',', '.'));
                token = point.getToken();
                if (point.getMethod().equals("form") && !validator.validateData(point)) {
                    msg = "Invalid data";
                } else {
                    ResultElement result = validator.checkPointInArea(point, System.nanoTime());
                    resultElementRepository.save(result);
                    condition = true;
                }
                this.tokens.updateTimeOfSession(token);
            }
        }

        Response response = new Response(msg, condition, token);
        return response;
    }

    @GetMapping("/get_results")
    public String getAllResults() {
        String msg = "";
        List<ResultElement> results = resultElementRepository.findAll();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            msg = ow.writeValueAsString(results);
        } catch (JsonProcessingException e) {
            System.out.println("Json parse error");
            msg = "Json parse error";
        }
        return msg;
    }

    @GetMapping("/changes")
    public DeferredResult<String> checkDatabaseChanges() {
        int length = resultElementRepository.findAll().size();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        long timeOutInMilliSec = 1800000000000L;
        String timeOutResp = "Time Out.";
        DeferredResult<String> output = new DeferredResult<>(timeOutInMilliSec, timeOutResp);
        this.executors.execute(() -> {
            try {
                while (true) {
                    if (length < resultElementRepository.findAll().size()) {
                        break;
                    }
                    Thread.sleep(10);
                }
                List<ResultElement> results = resultElementRepository.findAll();
                output.setResult(ow.writeValueAsString(results));
            } catch (Exception e) {
                System.out.println("Error with threads");
                output.setResult("Json parse error");
            }
        });
        return output;
    }
}
