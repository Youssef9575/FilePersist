package training.start.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import training.start.model.DatabaseFile;


@Repository
public interface DatabaseFileRepository extends JpaRepository<DatabaseFile, String> {

}