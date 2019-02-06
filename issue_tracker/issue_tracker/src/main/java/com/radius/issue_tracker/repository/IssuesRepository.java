

package com.radius.issue_tracker.repository;

import com.radius.issue_tracker.model.Issues;
import java.time.LocalDate;
import java.util.stream.Stream;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

/**
 * @author nitish on 2019-02-06.
 */
public interface IssuesRepository extends CassandraRepository<Issues> {
  @Query("select * From issue_tracker.issues where issue_date = ?0")
  Stream<Issues> getByIssueDate(LocalDate issueDate);

  @Query("select * From issue_tracker.issues")
  Stream<Issues> getAll();
}
