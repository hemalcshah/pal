package pal.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
	private PostRepository postRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Post> getAll() {

    	Iterable<Post> postList = postRepository.findAll();
    	
    	return postList;
    }
    
    @RequestMapping(method = RequestMethod.POST)
	Post add(@RequestBody Post input) {
    	return postRepository.findOne(null);
    }
    
	@RequestMapping("/create")
	@ResponseBody
	public String create(String title, String description) {
		Post post = null;
		try {
			post = new Post(title, description);
			postRepository.save(post);
		} catch (Exception ex) {
			ex.printStackTrace();
			return "Error creating the user: " + ex.toString();
		}
		return "User succesfully created! (id = " + post.getId() + ")";
	}
}
