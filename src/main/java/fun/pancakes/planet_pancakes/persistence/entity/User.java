package fun.pancakes.planet_pancakes.persistence.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Builder
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String name;
    private String location;

    private Long coins;
    private Map<String, Integer> resources;
}
