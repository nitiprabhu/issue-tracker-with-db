

package com.radius.issue_tracker.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

/**
 * @author nitish on 2019-02-06.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(value = "issues")
public class Issues {
  @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, name = "issue_id")
  private String issueId;

  @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, name = "issue_time")
  private LocalDateTime issueTime;

  private String description;

  private String status;

}
