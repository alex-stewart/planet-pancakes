package fun.pancakes.planet.pancakes.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
@DynamoDBTable(tableName = "island")
public class Island {

    @Id
    private Integer id;

    private String name;

    private Double size;

    private Integer ring;

    private Double radius;

    private Double bearing;

    private WikiSection wiki;
}
