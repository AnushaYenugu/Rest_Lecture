package lexicon.spring.rest_lecture.controller;


import lexicon.spring.rest_lecture.exception.ResourceNotFoundException;
import lexicon.spring.rest_lecture.model.entity.Role;
import lexicon.spring.rest_lecture.model.dto.RoleForm;
import lexicon.spring.rest_lecture.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RoleController {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public RoleController(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/api/v1/role")
    public ResponseEntity<List<RoleForm>> findAll(){

        List<Role> roleList=roleRepository.findAll();
       // return ResponseEntity.status(HttpStatus.OK).body(roleList);
       // List<RoleForm> roleFormList=new ArrayList<>();
        List<RoleForm> roleFormList=modelMapper.map(roleList
                ,new TypeToken<List<RoleForm>>(){}.getType());
     /*   for(Role role: roleList){
            roleFormList.add(new RoleForm(role.getId(),role.getName()));
        }
*/        return ResponseEntity.ok(roleFormList);
    }
    @GetMapping("api/v1/role/{id}")
    public ResponseEntity<RoleForm> findByRoleId(@PathVariable("id") Integer id){
        System.out.println(id);
        if(id==null) throw new IllegalArgumentException("Id is null");
        Role foundById=roleRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Role Data Not Found"));

       // RoleForm roleForm=new RoleForm(foundById.get().getId(),foundById.get().getName());

     //   RoleForm roleForm=modelMapper.map(foundById,RoleForm.class);
       /* if(foundById.isPresent()){
            return ResponseEntity.ok(foundById.get());
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }*/
       // return ResponseEntity.ok(roleForm);
        //instead we can write i simpler way
        return ResponseEntity.ok(modelMapper.map(foundById,RoleForm.class));
    }

    @GetMapping("api/v1/role/name")
    public ResponseEntity<RoleForm> findByRoleName(@RequestParam("name") String name){
        System.out.println(name);
        if(name==null) throw new IllegalArgumentException("Name was null");
        Role foundByName=roleRepository.findByName(name).orElseThrow(
                ()-> new ResourceNotFoundException("Role Data is not found"));

      //  RoleForm roleFormByName=new RoleForm(foundByName.get().getId(),foundByName.get().getName());
       RoleForm roleFormByName=modelMapper.map(foundByName,RoleForm.class);

        /* if(foundByName.isPresent()){
            return ResponseEntity.ok(foundByName.get());
        }
        else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }*/

        return ResponseEntity.ok(roleFormByName);
    }

    @PostMapping("api/v1/role")
    public ResponseEntity<RoleForm> create(@RequestBody RoleForm form ){
        System.out.println("Create ######Method");
        System.out.println("Role "+form);
       // Role role=new Role(form.getId(), form.getName());
        Role role=modelMapper.map(form,Role.class);
        Role savedRole=roleRepository.save(role);
        RoleForm roleForm=modelMapper.map(savedRole,RoleForm.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(roleForm);
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
          //  Role role=new Role(roleForm.getId(), roleForm.getName());
            Role role=modelMapper.map(roleForm,Role.class);
            roleRepository.save(role);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }else {
            return ResponseEntity.status(418).build();
        }

    }
}
