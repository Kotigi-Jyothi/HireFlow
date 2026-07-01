# HireFlow Pipeline State Machine

## Overview

The HireFlow recruitment pipeline follows a strict state machine where every candidate progresses through predefined stages. Candidates cannot skip stages or move backwards unless explicitly handled by an administrator. Every successful transition creates a new record in the `stage_history` table, enabling complete audit tracking and hiring analytics.

---

# Pipeline Flow

```
Applied
   │
   ▼
Screening
   │
   ▼
Technical
   │
   ▼
HR Round
   │
   ▼
Offer
   │
   ▼
Hired
```

---

# State Transition Table

| Current Stage | Next Stage | Trigger                                    | Guard Condition            |
| ------------- | ---------- | ------------------------------------------ | -------------------------- |
| Applied       | Screening  | Recruiter shortlists application           | Application exists         |
| Screening     | Technical  | Recruiter schedules technical interview    | Candidate passed screening |
| Technical     | HR Round   | Technical interview completed successfully | Candidate recommended      |
| HR Round      | Offer      | HR approves candidate                      | HR round completed         |
| Offer         | Hired      | Candidate accepts offer letter             | Offer status = ACCEPTED    |

---

# Invalid Transitions

The following transitions are not allowed:

* Applied → Technical
* Applied → HR Round
* Applied → Offer
* Applied → Hired
* Screening → HR Round
* Screening → Offer
* Screening → Hired
* Technical → Offer
* Technical → Hired
* HR Round → Hired

Backward transitions are also not permitted.

---

# State Machine Rules

1. Candidates must move sequentially through every stage.
2. Stage skipping is not allowed.
3. Every transition updates the application's current stage.
4. Every transition inserts a new record into the `stage_history` table.
5. Analytics calculations use `stage_history` instead of the current stage to preserve historical accuracy.
6. SLA monitoring begins immediately after entering a stage.
7. Offer generation is allowed only after completing the HR Round.
8. Candidate hiring is allowed only after offer acceptance.

---

# Backend Components

## Controller

* PipelineController

## Service

* PipelineService
* PipelineServiceImpl

## Repository

* PipelineStageRepository
* StageHistoryRepository
* ApplicationRepository

## Entities

* Application
* PipelineStage
* StageHistory

---

# Transition Process

When a stage transition occurs:

1. Validate the current stage.
2. Validate the requested next stage.
3. Ensure the transition follows the predefined pipeline order.
4. Update the application's `currentStage`.
5. Update `lastStageChangedAt`.
6. Insert a new record into `stage_history`.
7. Save the updated application.
8. Trigger SLA monitoring for the new stage.

---

# Stage History Example

| Application | From Stage | To Stage  |
| ----------- | ---------- | --------- |
| 1           | Applied    | Screening |
| 1           | Screening  | Technical |
| 1           | Technical  | HR Round  |
| 1           | HR Round   | Offer     |
| 1           | Offer      | Hired     |

---

# Advantages of Using a State Machine

* Prevents invalid stage transitions.
* Maintains a complete audit trail.
* Simplifies analytics calculations.
* Enables SLA monitoring.
* Ensures consistent recruitment workflow.
* Makes the hiring process predictable and maintainable.

---

# Conclusion

The HireFlow recruitment pipeline is implemented as a controlled state machine that guarantees sequential candidate progression, preserves transition history, supports SLA tracking, and provides reliable analytics while preventing invalid workflow transitions.
