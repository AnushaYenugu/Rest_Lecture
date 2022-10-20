package lexicon.spring.rest_lecture.controller;


import lexicon.spring.rest_lecture.model.entity.Role;
import lexicon.spring.rest_lecture.model.dto.RoleForm;
import lexicon.spring.rest_lecture.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
       // return ResponseEntity.status(HttpStatus.OK).body(roleList);
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

    @PostMapping("api/v1/role")
    public ResponseEntity<Role> create(@RequestBody RoleForm form ){
        System.out.println("Create ######Method");
        System.out.println("Role "+form);
        Role role=new Role(form.getId(), form.getName());
        Role savedRole=roleRepository.save(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRole);
    }

    @DeleteMapping("api/v1/role/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        if(roleRepository.existsById(id)){
            System.out.println("id "+id);
            roleRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        }
    }

//@RequestMapping(method = RequestMethod.PUT)
    @PutMapping("api/v1/role/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Integer id,@RequestBody RoleForm roleForm){
        System.out.println("Id: "+id);
        System.out.println("RoleForm "+roleForm);
        if(id== roleForm.getId()){
            Role role=new Role(roleForm.getId(), roleForm.getName());
            roleRepository.save(role);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }else {
            return ResponseEntity.status(418).build();
        }

    }
}
