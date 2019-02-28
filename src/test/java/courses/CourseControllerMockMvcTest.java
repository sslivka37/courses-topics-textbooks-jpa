package courses;

import java.util.Collection;
import java.util.Optional;
import java.util.Arrays;

import javax.annotation.Resource;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.*;



@RunWith(SpringRunner.class)
@WebMvcTest(CourseController.class)
public class CourseControllerMockMvcTest {
	
	@Resource
	private MockMvc mvc;
	
	@MockBean
	private CourseRepository courseRepo;
	
	@MockBean
	private TopicRepository topicRepo;
	
	@Mock
	private Course course;
	
	@Mock
	private Course anotherCourse;
	
	@Mock
	private Topic topic;
	
	@Mock
	private Topic anotherTopic;
	
	
	
	@Test
	public void should_Route_To_Single_Course_View()throws Exception {
		long arbitraryCourseId =1;
		when(courseRepo.findById(arbitraryCourseId)).thenReturn(Optional.of(course));
		mvc.perform(get("/course?id=1")).andExpect(view().name(is("course")));
	}
	
	@Test
	public void should_Be_Ok_For_Single_Course() throws Exception {
		long arbitraryCourseId =1;
		when(courseRepo.findById(arbitraryCourseId)).thenReturn(Optional.of(course));
		mvc.perform(get("/course?id=1")).andExpect(status().isOk());
	}
	
	@Test
	public void should_Put_Single_Course_Into_Model() throws Exception {
		when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
		
		mvc.perform(get("/course?=id=1")).andExpect(model().attribute("courses", is(course)));
	}
	
	@Test
	public void should_Route_To_All_Courses_View() throws Exception {
		mvc.perform(get("/show-courses")).andExpect(view().name(is("courses")));
	}
	
	@Test
	public void should_Be_Ok_For_All_Courses() throws Exception {
		mvc.perform(get("/show-courses")).andExpect(status().isOk());
	}
	
	@Test
	public void should_Put_All_Courses_Into_Model() throws Exception {
		Collection <Course> allCourses = Arrays.asList(course, anotherCourse);
		when(courseRepo.findAll()).thenReturn(allCourses);
		mvc.perform(get("/show-courses")).andExpect(model().attribute("courses", is(allCourses)));
	}
	
	@Test
	public void should_Route_To_Single_Topic_View()throws Exception {
		long arbitraryTopicId =42;
		when(topicRepo.findById(arbitraryTopicId)).thenReturn(Optional.of(topic));
		mvc.perform(get("/topic?id=42")).andExpect(view().name(is("topic")));
	}
	
	@Test
	public void should_Be_Ok_For_Single_Topic() throws Exception {
		long arbitraryTopicId =42;
		when(topicRepo.findById(arbitraryTopicId)).thenReturn(Optional.of(topic));
		mvc.perform(get("/topic?id=42")).andExpect(status().isOk());

}
	@Test
	public void should_Put_Single_Topic_Into_Model() throws Exception {
		when(topicRepo.findById(42L)).thenReturn(Optional.of(topic));
		
		mvc.perform(get("/topic?id=42")).andExpect(model().attribute("topics", is(topic)));
	}
	
	@Test
	public void should_Be_Ok_For_All_Topics() throws Exception {
		mvc.perform(get("/show-topics")).andExpect(status().isOk());
	}
	
	
}
