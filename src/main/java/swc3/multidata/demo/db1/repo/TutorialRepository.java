package swc3.multidata.demo.db1.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import swc3.multidata.demo.db1.data.Tutorial;

import java.util.List;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {

	//The implementation is plugged in by Spring Data JPA automatically.
	List<Tutorial> findByPublished(boolean published);
	List<Tutorial> findByTitleContaining(String title);

	//native query
	@Query(value = "SELECT * FROM tutorials t WHERE t.id = ?1", nativeQuery = true)
	Tutorial findTutorialById(long id);

}
