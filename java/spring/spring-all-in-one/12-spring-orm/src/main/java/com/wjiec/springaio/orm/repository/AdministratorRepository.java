package com.wjiec.springaio.orm.repository;

import com.wjiec.springaio.orm.model.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long>, JpaChecker {

    Administrator findByUsernameOrPassword(String username, String password);

    List<Administrator> findByUsernameIn(List<String> username);

    @Query("select a from Administrator as a where username = password and username = ?1")
    List<Administrator> findAllEqualsAccounts(String username);

}
