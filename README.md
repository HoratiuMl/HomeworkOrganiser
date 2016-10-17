# About
This application allows students to organize their assigned homeworks, in a manner very similar to a to-do list application.

# Planned features
- Registering new subjects and homeworks
- Homework deadline calendar
- Homework completion charts
- Reminder notifications (with email support)
- Various statistics

## Notifications
The student should be notified by its mobile app and/or by email whenever a deadline nears. The reminder period should be customizable (e.g. how many days before and at what time)

## Statistics and Charts
The statistics and charts will be targeted to a specific semester, year, and/or subject.

There will be statistics for:
- Average grade
- Punctuality (%)

There will be charts for:
- Grades
- Punctuality (%)

# Implementation details

## Entities
All entities will have an automatically generated identifier

- Subject
    - Title
    - Start date
    - Frequency (weekly, once every two weeks)
    - List of penalty values
    - Year
    - Semester

- Homework
    - Title
    - Description
    - Deadline
    - Grade
    
## Other details
Whenever the student misses a deadline, the maximum grade will be reduced by the current penalty’s value. That grade will be displayed to the user as well as information about the next deadline (if one is availabile)

There will also be a list of holidays.
