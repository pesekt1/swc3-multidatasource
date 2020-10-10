package swc3.multidata.demo.db2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swc3.multidata.demo.db2.data.Tutorial2;
import swc3.multidata.demo.db2.repo.TutorialRepository2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


//REST Controller for client-side rendering

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api2")
public class TutorialController2 {

	@Autowired
	TutorialRepository2 tutorialRepository;

	@GetMapping("/tutorials")
	public ResponseEntity<List<Tutorial2>> getAllTutorials(@RequestParam(required = false) String title) {
		try {
			List<Tutorial2> tutorials = new ArrayList<>();

			if (title == null)
				tutorials.addAll(tutorialRepository.findAll());
			else
				tutorials.addAll(tutorialRepository.findByTitleContaining(title));

			if (tutorials.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(tutorials, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/tutorials/{id}")
	public ResponseEntity<Tutorial2> getTutorialById(@PathVariable("id") long id) {
		Optional<Tutorial2> tutorialData = tutorialRepository.findById(id);

		if (tutorialData.isPresent()) {
			return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/tutorials")
	public ResponseEntity<Tutorial2> createTutorial(@RequestBody Tutorial2 tutorial) {
		try {
			Tutorial2 _tutorial = tutorialRepository
					.save(new Tutorial2(tutorial.getTitle(), tutorial.getDescription(), false));
			return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/tutorials/{id}")
	public ResponseEntity<Tutorial2> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial2 tutorial) {
		Optional<Tutorial2> tutorialData = tutorialRepository.findById(id);

		if (tutorialData.isPresent()) {
			Tutorial2 _tutorial = tutorialData.get();
			_tutorial.setTitle(tutorial.getTitle());
			_tutorial.setDescription(tutorial.getDescription());
			_tutorial.setPublished(tutorial.isPublished());
			return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/tutorials/{id}")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
		try {
			tutorialRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/tutorials")
	public ResponseEntity<HttpStatus> deleteAllTutorials() {
		try {
			tutorialRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/tutorials/published")
	public ResponseEntity<List<Tutorial2>> findByPublished() {
		try {
			List<Tutorial2> tutorials = tutorialRepository.findByPublished(true);

			if (tutorials.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(tutorials, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}