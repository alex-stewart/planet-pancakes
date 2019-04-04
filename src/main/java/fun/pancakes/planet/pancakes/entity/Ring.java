package fun.pancakes.planet.pancakes.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
@DynamoDBTable(tableName = "ring")
public class Ring {

    @Id
    private Integer id;

    private boolean clockwise;

    private Double yearSeconds;

    private Double radius;
}
