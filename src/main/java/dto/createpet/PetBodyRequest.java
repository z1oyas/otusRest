
package dto.createpet;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import com.fasterxml.jackson.annotation.JsonInclude;
import dto.ABodyRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@SuppressFBWarnings(value = {
    "EI_EXPOSE_REP", "EI_EXPOSE_REP2"
}, justification = "Это DTO, и его поля намеренно публично доступны для сериализации")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PetBodyRequest extends ABodyRequest {

  private Category category;
  private Long id;
  private String name;
  private List<String> photoUrls;
  private String status;
  private List<Tag> tags;

}
