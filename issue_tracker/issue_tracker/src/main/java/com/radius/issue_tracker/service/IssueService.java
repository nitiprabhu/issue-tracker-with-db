package com.radius.issue_tracker.service;

import com.radius.issue_tracker.model.Issues;
import com.radius.issue_tracker.repository.IssuesRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author nitish on 2019-02-06.
 */
@Service
public class IssueService {
  private static final String OPEN_STATUS = "OPENED"; //ideally, we should use Enums
  private static final int SECONDS = 86400;
  private static final int HOURS = 24;
  private static final int SECOND_ONE = 1;
  private final IssuesRepository issuesRepository;

  @Autowired
  public IssueService(IssuesRepository issuesRepository) {
    this.issuesRepository = issuesRepository;
  }

  public List<Issues> getAllOpenTickets() {
    return getAllOpenTicketsStream().collect(Collectors.toList());
  }

  /**
   * Method to get stream of open tickets.
   *
   * @return list of tickets with OPEN status
   */
  private Stream<Issues> getAllOpenTicketsStream() {
    return issuesRepository.getAll()
        .filter(status -> OPEN_STATUS.equalsIgnoreCase(status.getStatus()));
  }

  /**
   * Method to get a list of opened tickets from last N days
   *
   * @param numberOfDays number of previous days data
   * @return list of issues
   */
  public List<Issues> getOpenedTicketsFromLastNDays(int numberOfDays) {

    LocalDateTime pastNTime = LocalDateTime.now().minusHours(HOURS * numberOfDays);

    return getAllOpenTicketsStream()
        .filter(issueDate -> issueDate.getIssueTime().isAfter(pastNTime))
        .collect(Collectors.toList());
  }

  /**
   * Method to get a list of opened tickets from last N days
   *
   * @param numberOfDays number of previous days data
   * @return list of issues
   */
  public List<Issues> getOpenedTicketsAfterNDays(int numberOfDays) {

    LocalDateTime pastNTime = LocalDateTime.now().minusHours(HOURS * numberOfDays);

    return getAllOpenTicketsStream()
        .filter(issueDate -> issueDate.getIssueTime().isBefore(pastNTime.plusDays(SECOND_ONE)))
        .collect(Collectors.toList());
  }

  public List<Issues> getOpenedTicketsBetweenDays(int toDay, int fromDay) {

    LocalDateTime now = LocalDateTime.now();

    LocalDateTime fromPastDay = now.minusDays(fromDay);
    LocalDateTime toPastDay = now.minusDays(toDay);

    return getAllOpenTicketsStream()
        .filter(issueInstant -> issueInstant.getIssueTime().isBefore(toPastDay.plusDays(SECOND_ONE))
            &&
            issueInstant.getIssueTime().isAfter(fromPastDay.minusDays(SECOND_ONE)))
        .collect(Collectors.toList());

  }


}
