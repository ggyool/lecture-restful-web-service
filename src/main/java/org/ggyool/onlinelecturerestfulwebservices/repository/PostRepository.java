package org.ggyool.onlinelecturerestfulwebservices.repository;

import org.ggyool.onlinelecturerestfulwebservices.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
