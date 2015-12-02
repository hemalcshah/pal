package pal.post;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface PostRepository extends CrudRepository<Post, Long> {

	public Post findByTitle(String title);

}
