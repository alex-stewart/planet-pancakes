package fun.pancakes.planet_pancakes.persistence.repository;

import fun.pancakes.planet_pancakes.persistence.entity.News;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NewsRepository extends PagingAndSortingRepository<News, Long> {

}
