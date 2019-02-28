package courses;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.containsInAnyOrder;





@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class JPAMappingsTest {
	
	@Resource
	private TestEntityManager entityManager;
	
	
	@Resource
	private TopicRepository topicRepo;
	
	@Resource
	private CourseRepository courseRepo;
	
	@Resource
	private TextbookRepository textbookRepo;
	
	
	
	
	@Test
	public void should_Save_And_Load_Topic() {
		Topic topic = topicRepo.save(new Topic("topic"));
		long topicId = topic.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		Optional <Topic> result = topicRepo.findById(topicId);
		topic = result.get();
		assertThat(topic.getName(), is ("topic"));
	
	}
	
	@Test
	public void should_Generate_Topic_Id() {
		Topic topic = topicRepo.save(new Topic("topic"));
		long topicId = topic.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		assertThat(topicId, is(greaterThan(0L)));
	}
	
	
	@Test
	public void should_Save_And_Load_Course() {		
		Course course = new Course("course name", "description");
		course = courseRepo.save(course);
		long courseId = course.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		Optional <Course> result = courseRepo.findById(courseId);
		course = result.get();
		assertThat(course.getName(), is ("course name"));
		
	}
	
	@Test
	public void should_Establish_Course_To_Topics_Relationships() {
		//topic is not the owner so we create these first
		Topic java = topicRepo.save(new Topic("Java"));
		Topic ruby = topicRepo.save(new Topic("Ruby"));
		
		Course course = new Course("OO Languages", "descrption", java, ruby);
		course = courseRepo.save(course);
		long courseId = course.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		Optional <Course> result = courseRepo.findById(courseId);
		course = result.get();
		
		assertThat(course.getTopics(), containsInAnyOrder(java, ruby));
		
		
	}
	
	@Test
	public void should_Find_Courses_For_Topic() {
		Topic java = topicRepo.save(new Topic("java"));
		Course ooLanguages = courseRepo.save(new Course("OO Languges", "Description", java));
		Course advancedJava = courseRepo.save(new Course("Adv Java", "Description", java));
		
		
		entityManager.flush();
		entityManager.clear();
		
		Collection<Course> coursesForTopic = courseRepo.findByTopicsContains(java);
		
		assertThat(coursesForTopic, containsInAnyOrder(ooLanguages, advancedJava));
	}
	
	@Test
	public void should_Find_Courses_For_Topic_Id() {
		Topic ruby = topicRepo.save(new Topic("Ruby"));
		long topicId = ruby.getId();
		
		Course ooLanguages = courseRepo.save(new Course("OO Languges", "Description", ruby));
		Course advancedRuby = courseRepo.save(new Course("Adv Java", "Description", ruby));
		
		
		entityManager.flush();
		entityManager.clear();
		
		
		Collection<Course> coursesForTopic = courseRepo.findByTopicsId(topicId);
		
		assertThat(coursesForTopic, containsInAnyOrder(ooLanguages, advancedRuby));
		
	}
	
	
	@Test
	public void should_Establish_Textbook_To_Course_Relationship() {
		Course course = new Course("name", "description");
		courseRepo.save(course);
		long courseId = course.getId();
		
		Textbook book = new Textbook("title", course);
		textbookRepo.save(book);
		
		Textbook book2 = new Textbook("title2", course);
		textbookRepo.save(book2);
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<Course> result = courseRepo.findById(courseId);
		course = result.get();
		assertThat(course.getTextbooks(), containsInAnyOrder(book, book2));
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
