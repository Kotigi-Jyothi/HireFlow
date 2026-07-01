# EDGE_CASES

## Overview

During the development of HireFlow, several edge cases were identified to ensure the recruitment workflow remains consistent, reliable, and secure. The following document explains the major edge cases and how they are handled.

---

# Edge Case 1: Invalid Pipeline Transition

### Problem

A recruiter attempts to move a candidate directly from **Applied** to **HR Round**, skipping intermediate stages.

### Handling

The Pipeline State Machine validates every transition before updating the application.

Only the immediate next stage is permitted.

Example:

```text
Applied → Screening ✅
Applied → Technical ❌
Applied → HR Round ❌
```

---

# Edge Case 2: Duplicate Scorecard Submission

### Problem

An interviewer accidentally submits multiple scorecards for the same interview.

### Handling

The system validates whether a scorecard already exists for the interviewer and interview combination.

If one exists, the existing scorecard must be updated instead of creating a duplicate.

This prevents inconsistent interview evaluations.

---

# Edge Case 3: SLA Alert Sent Multiple Times

### Problem

The scheduler runs every day.

Without validation, the same candidate would receive repeated SLA notifications.

### Handling

Each application contains an `slaBreached` flag.

When an SLA breach is detected:

* Email is sent once.
* WebSocket notification is broadcast once.
* `slaBreached` becomes `true`.

Subsequent scheduler executions ignore the application.

---

# Edge Case 4: Offer Acceptance Without Offer Letter

### Problem

A candidate attempts to accept an offer before an offer letter has been generated.

### Handling

The application can reach the **Hired** stage only after an offer letter exists and its status becomes:

```text
ACCEPTED
```

Otherwise, the transition is rejected.

---

# Edge Case 5: Analytics with No Data

### Problem

Analytics queries execute when the database contains little or no hiring data.

This may cause divide-by-zero errors or NULL values.

### Handling

Analytics queries use SQL functions such as:

* `NULLIF()`
* `COALESCE()`
* `LEFT JOIN`

These ensure:

* Division by zero is avoided.
* Empty datasets return safe default values.
* APIs always return valid JSON responses.

Example:

```json
{
  "offerAcceptanceRate": 0.0,
  "scorecardSubmissionRate": 0.0
}
```

---

# Conclusion

These edge cases improve the robustness of HireFlow by preventing invalid workflow transitions, duplicate operations, repeated notifications, inconsistent offer processing, and analytics failures caused by empty datasets.
