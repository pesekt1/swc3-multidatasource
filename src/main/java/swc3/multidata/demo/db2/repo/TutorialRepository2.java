package swc3.multidata.demo.db2.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import swc3.multidata.demo.db2.data.Tutorial2;

import java.util.List;

public interface TutorialRepository2 extends JpaRepository<Tutorial2, Long> {

	//The implementation is plugged in by Spring Data JPA automatically.
	List<Tutorial2> findByPublished(boolean published);
	List<Tutorial2> findByTitleContaining(String title);

	//native query
	@Query(value = "SELECT * FROM tutorials t WHERE t.id = ?1", nativeQuery = true)
	Tutorial2 findTutorialById(long id);

}
