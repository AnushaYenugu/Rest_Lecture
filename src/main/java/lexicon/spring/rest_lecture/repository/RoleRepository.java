package lexicon.spring.rest_lecture.repository;

import lexicon.spring.rest_lecture.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {


    Optional<Role> findByName(String name);
}
