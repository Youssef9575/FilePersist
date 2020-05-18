package training.start.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import training.start.model.FileModel;


@Transactional
public interface FileRepository extends JpaRepository<FileModel, Long>{	
}