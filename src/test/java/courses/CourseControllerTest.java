package courses;



import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class CourseControllerTest {
	
	@InjectMocks
	private CourseController underTest;
	
	@Mock
	private Course course;
	
	@Mock
	private Course anotherCourse;
	
	@Mock
	private Topic topic;
	
	@Mock
	private Topic anotherTopic;
	
	@Mock
	private CourseRepository courseRepo;
	
	@Mock
	private TopicRepository topicRepo;
	
	
	@Mock
	private Model model;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void should_Add_Single_Course_To_Model() throws CourseNotFoundException {
		long arbitraryCourseId = 1;
		when(courseRepo.findById(arbitraryCourseId)).thenReturn(Optional.of(course));
		
		underTest.findOneCourse(arbitraryCourseId, model);		
		verify(model).addAttribute("courses", course);
	}
	
	@Test
	public void should_Add_All_Courses_To_Model() {
		Collection<Course> allCourses = Arrays.asList(course, anotherCourse);
		when (courseRepo.findAll()).thenReturn(allCourses);
		
		underTest.findAllCourses(model);
		verify(model).addAttribute("courses", allCourses);
	}
	
	
	@Test
	public void should_Add_Single_Topic_To_Model() throws TopicNotFoundException {
		long arbitraryTopicId = 1;
		when(topicRepo.findById(arbitraryTopicId)).thenReturn(Optional.of(topic));
		
		underTest.findOneTopic(arbitraryTopicId, model);
		
		verify(model).addAttribute("topics", topic);
	}
	
	
	
	@Test
	public void should_Add_All_Topics_To_Model() {
		Collection<Topic> allTopics = Arrays.asList(topic, anotherTopic);
		when(topicRepo.findAll()).thenReturn(allTopics);
		
		underTest.findAllTopics(model);
		verify(model).addAttribute("topics", allTopics);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
