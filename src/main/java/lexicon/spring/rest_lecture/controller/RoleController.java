package lexicon.spring.rest_lecture.controller;


import lexicon.spring.rest_lecture.model.Role;
import lexicon.spring.rest_lecture.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class RoleController {
    private RoleRepository roleRepository;

    @Autowired
    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    @GetMapping("/api/v1/role")
    public ResponseEntity<List<Role>> findAll(){

        List<Role> roleList=roleRepository.findAll();
        return ResponseEntity.ok(roleList);
    }
    @GetMapping("api/v1/role/{id}")
    public ResponseEntity<Role> findByRoleId(@PathVariable("id") Integer id){
        System.out.println(id);
        Optional<Role> foundById=roleRepository.findById(id);
        if(foundById.isPresent()){
            return ResponseEntity.ok(foundById.get());
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("api/v1/role/name")
    public ResponseEntity<Role> findByRoleName(@RequestParam("name") String name){
        System.out.println(name);
        Optional<Role> foundByName=roleRepository.findByName(name);
        if(foundByName.isPresent()){
            return ResponseEntity.ok(foundByName.get());
        }
        else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }
}
