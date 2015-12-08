package pal.post;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    	//validate input
    	return postRepository.save(input);

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
	
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody Post uploadMedia(
            @RequestParam(value="file", required=false) MultipartFile file ,
            @RequestParam(value="title", required=true) String title,
            @RequestParam(value="description", required=true) String description) {
        
        try {
            System.out.println("file name: " + file.getOriginalFilename());
            System.out.println("file size: " + file.getSize());
            
            //);Document document = new Document(file.getBytes(), file.getOriginalFilename(), date, person );
            
            //return document.getMetadata();
            Post post = new Post(title,description);
            post.setCreatedDateTime(new Date());
            post.setMedia(file.getBytes());
            postRepository.save(post);
            return post;
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }      
    }
	
}
