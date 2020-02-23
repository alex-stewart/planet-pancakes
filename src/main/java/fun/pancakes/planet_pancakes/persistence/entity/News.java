package fun.pancakes.planet_pancakes.persistence.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Builder
@Document(collection = "news")
public class News {

    @Id
    private Long id;

    private String newspaperTitle;
    private String newspaperColour;
    private Date publishDay;
    private List<String> headlines;
}
