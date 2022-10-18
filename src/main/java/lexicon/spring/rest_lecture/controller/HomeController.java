package lexicon.spring.rest_lecture.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

 //   @RequestMapping(method = RequestMethod.GET, path = {"/","index","helloworld"})
    @GetMapping(path = {"index","helloworld"})
    public String helloWorld(){

        return  " <h1>Hello World - Message from Home Controller</h1>";
    }

    @GetMapping("/message")
    public ResponseEntity<String>  responseString(@RequestParam(value="message",defaultValue = "Hello this is another message") String message){

        return ResponseEntity.status(200).body(message);
    }

@GetMapping
    public ResponseEntity<Void> foo(){
        return ResponseEntity.notFound().build();
    }
}
